package model;

public class Tandem extends Chair {
    private int numberOfSeats;

    public Tandem(String reference, int price, double rating, int numberOfSeats) {
        super(reference, price, rating);
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public boolean shouldBeRemoved() {
        return false;
    }
}
