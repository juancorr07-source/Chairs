package model;

public class Massager extends Chair {
    private int usageModes;
    private String calefaction;

    public Massager(String reference, int price, double rating, int usageModes, String calefaction) {
        super(reference, price, rating);
        this.usageModes = usageModes;
        this.calefaction = calefaction;
    }

    @Override
    public boolean shouldBeRemoved() {
        return false;
    }
}
