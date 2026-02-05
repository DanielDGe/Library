package solidLibrary.presentation;

import solidLibrary.receipt.LoanReceipt;
import solidLibrary.receipt.MonthlyReport;
import solidLibrary.receipt.ReturnReceipt;

public interface ReportPresenter {
    void presentMonthlyReport(MonthlyReport report);

    void presentLoanReceipt(LoanReceipt receipt);

    void presentReturnReceipt(ReturnReceipt receipt);
}
