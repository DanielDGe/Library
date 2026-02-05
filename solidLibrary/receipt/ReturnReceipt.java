package solidLibrary.receipt;

public class ReturnReceipt {
    private final String loanId;
    private final double fine;

    public ReturnReceipt(String loanId, double fine) {
        this.loanId = loanId;
        this.fine = fine;
    }

    public String getLoanId() {
        return loanId;
    }

    public double getFine() {
        return fine;
    }
}
