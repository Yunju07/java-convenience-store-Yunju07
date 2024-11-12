package store.enums;

public enum Membership {
    STANDARD(0.3, 8000);

    private final double discountRate;
    private final int discountLimit;

    private Membership(double discountRate, int discountLimit){
        this.discountRate = discountRate;
        this.discountLimit = discountLimit;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public int getDiscountLimit() {
        return discountLimit;
    }
}
