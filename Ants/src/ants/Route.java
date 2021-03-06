/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Route {
    private ArrayList<City> route = new ArrayList();
    private double length = 0;
    private City lastCity = null;
    
    public ArrayList<City> getRoute() {
        return route;
    }
    
    
    
    public double getLength() {
        return length;
    }
    
    public void addCity(City city) {
        if (getLastCity() != null) {
            length += Main.data.getDistanceData(getLastCity().getNumber(), city.getNumber());
        }
        route.add(city);
        lastCity = city;
    }

    /**
     * @return the lastCity
     */
    public City getLastCity() {
        return lastCity;
    }
    
    
}
