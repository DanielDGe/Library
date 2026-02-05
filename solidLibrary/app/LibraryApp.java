package solidLibrary.app;

import java.time.Clock;

import solidLibrary.domain.Book;
import solidLibrary.domain.Member;
import solidLibrary.notification.ConsoleNotificationService;
import solidLibrary.notification.NotificationService;
import solidLibrary.policy.LoanPolicy;
import solidLibrary.presentation.ConsoleReportPresenter;
import solidLibrary.presentation.ReportPresenter;
import solidLibrary.receipt.LoanReceipt;
import solidLibrary.receipt.MonthlyReport;
import solidLibrary.repository.inmemory.InMemoryBookRepository;
import solidLibrary.repository.inmemory.InMemoryLoanRepository;
import solidLibrary.repository.inmemory.InMemoryMemberRepository;
import solidLibrary.service.LoanService;
import solidLibrary.service.ReportService;
import solidLibrary.strategy.FineStrategy;
import solidLibrary.strategy.FlatRateFineStrategy;

public class LibraryApp {
    public static void main(String[] args) {
        InMemoryBookRepository bookRepository = new InMemoryBookRepository();
        InMemoryMemberRepository memberRepository = new InMemoryMemberRepository();
        InMemoryLoanRepository loanRepository = new InMemoryLoanRepository();
        NotificationService notificationService = new ConsoleNotificationService();
        FineStrategy fineStrategy = new FlatRateFineStrategy(0.50);
        LoanPolicy loanPolicy = new LoanPolicy(5, 14);
        Clock clock = Clock.systemDefaultZone();

        LoanService loanService = new LoanService(
            bookRepository,
            memberRepository,
            loanRepository,
            notificationService,
            fineStrategy,
            loanPolicy,
            clock
        );
        ReportService reportService = new ReportService(loanRepository, fineStrategy, clock);
        ReportPresenter reportPresenter = new ConsoleReportPresenter();

        bookRepository.save(new Book("B1", "Clean Code", "Robert C. Martin"));
        bookRepository.save(new Book("B2", "Effective Java", "Joshua Bloch"));

        memberRepository.save(new Member("M1", "Daniel", "daniel@email.com"));
        memberRepository.save(new Member("M2", "Ana", "ana@email.com"));

        LoanReceipt receipt = loanService.createLoan("M1", "B1");
        reportPresenter.presentLoanReceipt(receipt);

        MonthlyReport report = reportService.generateMonthlyReport();
        reportPresenter.presentMonthlyReport(report);
    }
}
