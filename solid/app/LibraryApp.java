package solid.app;

import java.time.Clock;

import solid.domain.Book;
import solid.domain.Member;
import solid.notification.ConsoleNotificationService;
import solid.notification.NotificationService;
import solid.policy.LoanPolicy;
import solid.presentation.ConsoleReportPresenter;
import solid.presentation.ReportPresenter;
import solid.receipt.LoanReceipt;
import solid.receipt.MonthlyReport;
import solid.repository.inmemory.InMemoryBookRepository;
import solid.repository.inmemory.InMemoryLoanRepository;
import solid.repository.inmemory.InMemoryMemberRepository;
import solid.service.LoanService;
import solid.service.ReportService;
import solid.strategy.FineStrategy;
import solid.strategy.FlatRateFineStrategy;

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
