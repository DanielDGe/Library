package solid.receipt;

import java.time.LocalDate;

public class LoanReceipt {
    private final String loanId;
    private final String memberName;
    private final String bookTitle;
    private final LocalDate loanDate;
    private final LocalDate dueDate;

    public LoanReceipt(String loanId, String memberName, String bookTitle, LocalDate loanDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.memberName = memberName;
        this.bookTitle = bookTitle;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
