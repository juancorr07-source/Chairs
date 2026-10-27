package model;

public class Wheelchair extends Chair {
    private Traction traction;

    public Wheelchair(String reference, int price, double rating, Traction traction) {
        super(reference, price, rating);
        this.traction = traction;
    }

    @Override
    public boolean shouldBeRemoved() {
        return false;
    }
}