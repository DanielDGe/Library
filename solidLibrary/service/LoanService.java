package solidLibrary.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import solidLibrary.domain.Book;
import solidLibrary.domain.Loan;
import solidLibrary.domain.Member;
import solidLibrary.notification.NotificationService;
import solidLibrary.policy.LoanPolicy;
import solidLibrary.receipt.LoanReceipt;
import solidLibrary.receipt.ReturnReceipt;
import solidLibrary.repository.BookRepository;
import solidLibrary.repository.LoanRepository;
import solidLibrary.repository.MemberRepository;
import solidLibrary.strategy.FineStrategy;

public class LoanService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;
    private final NotificationService notificationService;
    private final FineStrategy fineStrategy;
    private final LoanPolicy loanPolicy;
    private final Clock clock;

    public LoanService(BookRepository bookRepository,
                       MemberRepository memberRepository,
                       LoanRepository loanRepository,
                       NotificationService notificationService,
                       FineStrategy fineStrategy,
                       LoanPolicy loanPolicy,
                       Clock clock) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
        this.notificationService = notificationService;
        this.fineStrategy = fineStrategy;
        this.loanPolicy = loanPolicy;
        this.clock = clock;
    }

    public LoanReceipt createLoan(String memberId, String bookId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("Miembro no encontrado"));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

        if (!book.isAvailable()) {
            throw new IllegalStateException("Libro no disponible");
        }

        long activeLoans = loanRepository.findByMemberId(memberId).stream()
            .filter(loan -> !loan.isReturned())
            .count();

        if (activeLoans >= loanPolicy.getMaxActiveLoans()) {
            throw new IllegalStateException("Límite de préstamos alcanzado");
        }

        LocalDate today = LocalDate.now(clock);
        LocalDate dueDate = today.plusDays(loanPolicy.getLoanDays());
        Loan loan = new Loan(UUID.randomUUID().toString(), memberId, bookId, today, dueDate);
        loanRepository.save(loan);

        book.setAvailable(false);
        bookRepository.save(book);

        notificationService.notifyLoanCreated(member, book);

        return new LoanReceipt(loan.getId(), member.getName(), book.getTitle(), today, dueDate);
    }

    public ReturnReceipt returnLoan(String loanId) {
        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        loan.setReturned(true);
        loanRepository.save(loan);

        bookRepository.findById(loan.getBookId()).ifPresent(book -> {
            book.setAvailable(true);
            bookRepository.save(book);
        });

        double fine = calculateFine(loan);

        memberRepository.findById(loan.getMemberId()).ifPresent(member ->
            bookRepository.findById(loan.getBookId()).ifPresent(book ->
                notificationService.notifyLoanReturned(member, book)
            )
        );

        return new ReturnReceipt(loan.getId(), fine);
    }

    public double calculateFine(Loan loan) {
        LocalDate today = LocalDate.now(clock);
        long daysOverdue = ChronoUnit.DAYS.between(loan.getDueDate(), today);
        return fineStrategy.calculateFine(daysOverdue);
    }
}
