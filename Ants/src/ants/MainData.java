package ants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

public class MainData {

    private  ArrayList<ArrayList<Double>> pheromonData;
    private  ArrayList<ArrayList<Double>> distanceData;
    private  ArrayList<City> cityList = new ArrayList();
    public  double pheromon;
    public  double localInformation;
    public  double evaporation;
    public  double initialPheromon;
    public  double pheromonUpdate;
    private  double maxX = Double.MIN_VALUE;
    private  double maxY = Double.MIN_VALUE;
    private  double minX = Double.MAX_VALUE;
    private  double minY = Double.MAX_VALUE;
    private  Route globalBest = null;
    private  Route localBest = null;

    public MainData() {
        
    }
    
    public  void loadFromFile(String path) {

        if (path != null) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                
                while (reader.ready()) {
                    String line = reader.readLine();
                    
                    switch (line) {
                        case "NODE_COORD_SECTION":
                            while (reader.ready()) {
                                String coordLine = reader.readLine();
                                if (!coordLine.equals("EOF")) {
                                    String lineParts[] = coordLine.split(" +");
                                    if (lineParts.length == 3) {
                                        Main.data.addCity(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]));
                                    }
                                }
                            } 
                    }
                    
                }
                
                
            } catch (Exception e) { //TODO mehr catchblöcke
                JOptionPane.showMessageDialog(null, "Die gewählte Datei wurde nicht gefunden"+e, path, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public  void saveToFile() {
    }

    public  void solveTSP(Integer antCount, Integer iterationCount) {
    }

    public  void getParameters() {
    }

    /**
     * @return the pheromonData
     */
    public  Double getPheromonData(int from, int to) {
        return pheromonData.get(from).get(to);
    }

    /**
     * @return the distanceData
     */
    public  Double getDistanceData(int from, int to) {

        return distanceData.get(from).get(to);
    }

    /**
     * @return the cityList
     */
    public  City getCity(int number) {
        return cityList.get(number);
    }

    public  int getCityListLength() {
        return cityList.size();
    }

    public  void addCity(double x, double y) {
        if (x > maxX) {
            maxX = x;
        }
        if (y > maxY) {
            maxY = y;
        }
        if (x < minX) {
            minX = x;
        }
        if (y < minY) {
            minY = y;
        }
        cityList.add(new City(x, y, cityList.size()));

        //TODO Länge
        //TODO PHEROMON
    }

    public  double getMaxX() {
        return maxX;
    }

    public  double getMaxY() {
        return maxY;
    }

    public  double getMinX() {
        return minX;
    }

    public  double getMinY() {
        return minY;
    }

    public  Route getGlobalBest() {
        return globalBest;
    }

    public  Route getLocalBest() {
        return localBest;
    }

    public  void setGlobalBest(Route route) {
        globalBest = route;
    }

    public  void setLocalBest(Route route) {
        localBest = route;
    }
}