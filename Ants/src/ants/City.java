package ants;

public class City {

    private double xPos;
    private double yPos;
    private int cityNumber;

    public City(double xPos, double yPos, int number) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.cityNumber = number;
    }

    public double getXPos() {
        return this.xPos;
    }

    public double getYPos() {
        return this.yPos;
    }

    public Integer getNumber() {
        return null;
    }
}