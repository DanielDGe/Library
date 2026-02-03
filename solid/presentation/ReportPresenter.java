package solid.presentation;

import solid.receipt.LoanReceipt;
import solid.receipt.MonthlyReport;
import solid.receipt.ReturnReceipt;

public interface ReportPresenter {
    void presentMonthlyReport(MonthlyReport report);

    void presentLoanReceipt(LoanReceipt receipt);

    void presentReturnReceipt(ReturnReceipt receipt);
}
