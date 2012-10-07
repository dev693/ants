package ants;

import java.awt.Color;

public class City {

    private double xPos;
    private double yPos;
    private int cityNumber;
    private Color color = Color.BLACK;
    
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

    public void moveCity(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    public Integer getNumber() {
        return this.cityNumber;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }
}