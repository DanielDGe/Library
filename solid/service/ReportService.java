package solid.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import solid.domain.Loan;
import solid.receipt.MonthlyReport;
import solid.repository.LoanRepository;
import solid.strategy.FineStrategy;

public class ReportService {
    private final LoanRepository loanRepository;
    private final FineStrategy fineStrategy;
    private final Clock clock;

    public ReportService(LoanRepository loanRepository, FineStrategy fineStrategy, Clock clock) {
        this.loanRepository = loanRepository;
        this.fineStrategy = fineStrategy;
        this.clock = clock;
    }

    public MonthlyReport generateMonthlyReport() {
        LocalDate today = LocalDate.now(clock);
        int totalLoans = 0;
        int totalReturns = 0;
        double totalFines = 0;

        for (Loan loan : loanRepository.findAll()) {
            if (loan.getLoanDate().getMonth() == today.getMonth()) {
                totalLoans++;
                if (loan.isReturned()) {
                    totalReturns++;
                }
                if (loan.getDueDate().isBefore(today) && !loan.isReturned()) {
                    long days = ChronoUnit.DAYS.between(loan.getDueDate(), today);
                    totalFines += fineStrategy.calculateFine(days);
                }
            }
        }

        return new MonthlyReport(totalLoans, totalReturns, totalFines);
    }
}
