package ants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

public class TSP {

    private String name;
    private String comment = "";
    private ArrayList<ArrayList<Double>> pheromonData;
    private ArrayList<ArrayList<Double>> distanceData;
    private ArrayList<City> cityList = new ArrayList();
    private double pheromon;
    private double localInformation;
    private double evaporation;
    private double initialPheromon;
    private double pheromonUpdate;
    private int iterations;
    private int ants;
    private double maxX = Double.MIN_VALUE;
    private double maxY = Double.MIN_VALUE;
    private double minX = Double.MAX_VALUE;
    private double minY = Double.MAX_VALUE;
    private Route globalBest = null;
    private Route localBest = null;

    public TSP() {
    }

    public static void loadFromFile(String path) {
        System.out.println(path);
        Main.data = new TSP();

        if (path != null) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                int localDimension = 0;
                while (reader.ready()) {
                    String line = reader.readLine().trim();


                    if (line.equals("NODE_COORD_SECTION")) {
                        while (reader.ready()) {
                            String coordLine = reader.readLine();
                            if (!coordLine.equals("EOF")) {
                                String lineParts[] = coordLine.trim().split(" +");
                                if (lineParts.length == 3) {
                                    Main.data.addCity(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]));
                                }
                            }

                        }
                        if (localDimension != Main.data.getCityListLength()) {
                            throw new Exception("Fehlerhafte Dimension in TSP-Datei.");
                        }
                    }

                    if (line.startsWith("COMMENT")) {
                        String lineParts[] = line.split(":");
                        Main.data.addComment(lineParts[1].trim());
                    }

                    if (line.startsWith("NAME")) {
                        String lineParts[] = line.split(":");
                        Main.data.setName(lineParts[1].trim());
                    }

                    if (line.startsWith("DIMENSION")) {
                        String lineParts[] = line.split(":");
                        localDimension = Integer.parseInt(lineParts[1].trim());
                    }

                    Main.window.repaint();

                }

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Die gewählte Datei wurde nicht gefunden \n" + e, path, JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Lesen der Datei \n" + e, path, JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) { //TODO mehr catchblöcke
                JOptionPane.showMessageDialog(null, e.getMessage(), path, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void saveToFile(File file) {
        if (this.comment == null) {
            this.comment = JOptionPane.showInputDialog(Main.window, "Bitte geben Sie einen Kommentar ein: ", "Kommentar", JOptionPane.QUESTION_MESSAGE);
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            writer.write("NAME: " + file.getName().subSequence(0, file.getName().lastIndexOf(".")));
            writer.newLine();
            writer.write("TYPE: TSP");
            writer.newLine();
            writer.write("COMMENT: " + this.comment);
            writer.newLine();
            writer.write("DIMENSION: " + this.getCityListLength());
            writer.newLine();
            writer.write("EDGE_WEIGHT_TYPE: EUC_2D");
            writer.newLine();
            writer.write("NODE_COORD_SECTION");
            writer.newLine();
            int number = 0;
            for (City city : cityList) {
                writer.write(++number + " " + city.getXPos() + " " + city.getYPos());
                writer.newLine();
            }
            writer.write("EOF");
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void solveTSP() {
    }

    public void getParameters() {
    }

    /**
     * @return the pheromonData
     */
    public Double getPheromonData(int from, int to) {
        return pheromonData.get(from).get(to);
    }

    /**
     * @return the distanceData
     */
    public Double getDistanceData(int from, int to) {

        return distanceData.get(from).get(to);
    }

    /**
     * @return the cityList
     */
    public City getCity(int number) {
        return cityList.get(number);
    }

    public int getCityListLength() {
        return cityList.size();
    }

    public void addCity(double x, double y) {
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
    
    public City getCityNearby(double x, double y, double rangeX, double rangeY) {
        for(City city : cityList) {
            if((x-rangeX) < city.getXPos() && city.getXPos() < (x+rangeX) 
                    && (y-rangeY) < city.getYPos() &&  city.getYPos() < (y+rangeY)) {
                return city;
            }
        }
        return null;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public Route getGlobalBest() {
        return globalBest;
    }

    public Route getLocalBest() {
        return localBest;
    }

    public void setGlobalBest(Route route) {
        globalBest = route;
    }

    public void setLocalBest(Route route) {
        localBest = route;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public void addComment(String comment) {
        this.comment += comment + " ";
    }

    /**
     * @param pheromon the pheromon to set
     */
    public void setPheromon(double pheromon) {
        this.pheromon = pheromon;
    }

    /**
     * @param localInformation the localInformation to set
     */
    public void setLocalInformation(double localInformation) {
        this.localInformation = localInformation;
    }

    /**
     * @param evaporation the evaporation to set
     */
    public void setEvaporation(double evaporation) {
        this.evaporation = evaporation;
    }

    /**
     * @param initialPheromon the initialPheromon to set
     */
    public void setInitialPheromon(double initialPheromon) {
        this.initialPheromon = initialPheromon;
    }

    /**
     * @param pheromonUpdate the pheromonUpdate to set
     */
    public void setPheromonUpdate(double pheromonUpdate) {
        this.pheromonUpdate = pheromonUpdate;
    }

    /**
     * @param iterations the iterations to set
     */
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    /**
     * @param ants the ants to set
     */
    public void setAnts(int ants) {
        this.ants = ants;
    }
}