package solidLibrary.policy;

public class LoanPolicy {
    private final int maxActiveLoans;
    private final int loanDays;

    public LoanPolicy(int maxActiveLoans, int loanDays) {
        this.maxActiveLoans = maxActiveLoans;
        this.loanDays = loanDays;
    }

    public int getMaxActiveLoans() {
        return maxActiveLoans;
    }

    public int getLoanDays() {
        return loanDays;
    }
}
