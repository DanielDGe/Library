package solid.domain;

import java.time.LocalDate;

public class Loan {
    private final String id;
    private final String memberId;
    private final String bookId;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private boolean returned;

    public Loan(String id, String memberId, String bookId, LocalDate loanDate, LocalDate dueDate) {
        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returned = false;
    }

    public String getId() {
        return id;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
