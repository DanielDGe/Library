package solidLibrary.strategy;

public class FlatRateFineStrategy implements FineStrategy {
    private final double ratePerDay;

    public FlatRateFineStrategy(double ratePerDay) {
        this.ratePerDay = ratePerDay;
    }

    @Override
    public double calculateFine(long daysOverdue) {
        if (daysOverdue <= 0) {
            return 0;
        }
        return daysOverdue * ratePerDay;
    }
}
