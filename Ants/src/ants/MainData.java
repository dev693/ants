package ants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

public class MainData {

    private static ArrayList<ArrayList<Double>> pheromonData;
    private static ArrayList<ArrayList<Double>> distanceData;
    private static ArrayList<City> cityList = new ArrayList();
    public static double pheromon;
    public static double localInformation;
    public static double evaporation;
    public static double initialPheromon;
    public static double pheromonUpdate;
    private static double maxX = Double.MIN_VALUE;
    private static double maxY = Double.MIN_VALUE;
    private static double minX = Double.MAX_VALUE;
    private static double minY = Double.MAX_VALUE;
    private static Route globalBest = null;
    private static Route localBest = null;

    public static void loadFromFile(String path) {

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
                                        MainData.addCity(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]));
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

    public static double getMaxX() {
        return maxX;
    }

    public static double getMaxY() {
        return maxY;
    }

    public static double getMinX() {
        return minX;
    }

    public static double getMinY() {
        return minY;
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