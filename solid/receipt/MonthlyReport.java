package solid.receipt;

public class MonthlyReport {
    private final int totalLoans;
    private final int totalReturns;
    private final double totalFines;

    public MonthlyReport(int totalLoans, int totalReturns, double totalFines) {
        this.totalLoans = totalLoans;
        this.totalReturns = totalReturns;
        this.totalFines = totalFines;
    }

    public int getTotalLoans() {
        return totalLoans;
    }

    public int getTotalReturns() {
        return totalReturns;
    }

    public double getTotalFines() {
        return totalFines;
    }
}
