package ants;

import java.util.ArrayList;
import java.util.Collections;

public class MainData {

  private static ArrayList<ArrayList<Double>> pheromonData;

  private static ArrayList<ArrayList<Double>> distanceData;

  private static ArrayList<City> cityList = new ArrayList();

  public static double pheromon;

  public static double localInformation;

  public static double evaporation;

  public static double initialPheromon;

  public static double pheromonUpdate;
  
  private static double maxX = 0;
  
  private static double maxY = 0;
  
  private static Route globalBest = null;
  
  private static Route localBest = null;

  public static void loadFromFile(String path) {
  }

  public static void saveToFile() {
  }

  public static void solveTSP(Integer antCount, Integer iterationCount) {
  }

  public static void getParameters() {
  }

    /**
     * @return the pheromonData
     */
    public static Double getPheromonData(int from, int to) {
        return pheromonData.get(from).get(to);
    }

    /**
     * @return the distanceData
     */
    public static Double getDistanceData(int from, int to) {
        
        return distanceData.get(from).get(to);
    }

    /**
     * @return the cityList
     */
    public static City getCity(int number) {
        return cityList.get(number);
    }
    
    public static int getCityListLength() {
        return cityList.size();
    }
    
    public static void addCity(double x, double y) {
        if (x > maxX) {
            maxX = x;
        }
        if (y > maxY) {
            maxY = y;
        }
        cityList.add(new City(x,y, cityList.size()));
        
        //TODO Länge
        //TODO PHEROMON
    }
    
    public static double getMaxX() {
        return maxX;
    }
    
    public static double getMaxY() {
        return maxY;
    }
    
    public static Route getGlobalBest() {
        return globalBest;
    }
    
    public static Route getLocalBest() {
        return localBest;
    }
   
    public static void setGlobalBest(Route route) {
        globalBest = route;
    }
  
    public static void setLocalBest(Route route) {
        localBest = route;
    }
  
}