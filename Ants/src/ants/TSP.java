package ants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import javax.swing.JOptionPane;

public class TSP {

    private String name;
    private String comment = "";
    private ArrayList<ArrayList<Double>> pheromonData;
    private ArrayList<ArrayList<Double>> distanceData;
    private TreeMap<Integer, City> cityMap = new TreeMap();
    private double pheromon;
    private double localInformation;
    private double evaporation;
    private double initialPheromon;
    private double pheromonUpdate;
    private int iterations;
    private int ants;
    private Route globalBest = null;
    private Route localBest = null;
    private Route optimalRoute = null;
    private int maxCityNumber = 1;
    
    public TSP() {
    }

    public static TSP loadFromFile(String path) {
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
                                    Main.data.addCity(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]), Integer.parseInt(lineParts[0]));
                                }
                            }

                        }
                        if (localDimension != Main.data.getCityListLength()) {
                            throw new Exception("Fehlerhafte Dimension in TSP-Datei.");
                        }
                    }

                    if (line.startsWith("COMMENT")) {
                        String lineParts[] = line.split(":");
                        if (lineParts.length > 1) { 
                            Main.data.addComment(lineParts[1].trim());
                        }
                    }

                    if (line.startsWith("NAME")) {
                        String lineParts[] = line.split(":");
                        if (lineParts.length > 1) { 
                            Main.data.setName(lineParts[1].trim());
                        }
                    }

                    if (line.startsWith("DIMENSION")) {
                        String lineParts[] = line.split(":");
                        if (lineParts.length > 1) { 
                            localDimension = Integer.parseInt(lineParts[1].trim());
                        }
                    }

                    

                }
                String tourFile = path.replace(".tsp", ".opt.tour");
                File file = new File(tourFile);
                if (file.exists()) {
                    Main.data.getOptTour(file);
                }
                Main.window.repaint();

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Die gewählte Datei wurde nicht gefunden \n" + e, path, JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Lesen der Datei \n" + e, path, JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) { //TODO mehr catchblöcke
                JOptionPane.showMessageDialog(null, e.getMessage(), path, JOptionPane.ERROR_MESSAGE);
            }      
        }
        
        return Main.data;
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
            for (City city : cityMap.values()) {
                writer.write(city.getNumber() + " " + city.getXPos() + " " + city.getYPos());
                writer.newLine();
            }
            writer.write("EOF");
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Route getOptTour(File file) {
         
         try {
                Route optRoute = new Route();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                while (reader.ready()) {
                    String line = reader.readLine().trim();
                    
                    if (line.startsWith("TYPE")) {
                        String lineParts[] = line.split(":");
                        if (lineParts.length > 1) { 
                            if (!lineParts[1].trim().equalsIgnoreCase("TOUR")) {
                                throw new Exception("Die ausgewählte Datei enthält keine Tour");
                            }
                        }
                    }

                    if (line.startsWith("NAME")) {
                        String lineParts[] = line.split(":");
                        if (lineParts.length > 1) { 
                            if (!lineParts[1].trim().equals(this.getName() + ".opt.tour")) {
                                throw new Exception("Die optimale Tour passt nicht zu dem ausgewähtem TSP");
                            }
                        }
                    }

                    if (line.startsWith("DIMENSION")) {
                        String lineParts[] = line.split(":");
                        if (lineParts.length > 1) { 
                            if(Integer.parseInt(lineParts[1].trim()) != this.getCityListLength()) {
                                throw new Exception("Die optimale Tour passt nicht zu dem ausgewähtem TSP");
                            }
                        }
                    }
                    
                    if (line.equals("TOUR_SECTION")) {
                        while (reader.ready()) {
                            String numberLine = reader.readLine();
                            if (!numberLine.equals("EOF")) {
                                int number = Integer.parseInt(numberLine);
                                if (number == -1) {
                                    optRoute.addCity(optRoute.getRoute().get(0));
                                    break;
                                } else {
                                    optRoute.addCity(this.getCity(number));
                                }
                            }

                        }
                        
                        if ((optRoute.getRoute().size() -1) != Main.data.getCityListLength()) {
                            throw new Exception("Die Länge der Route stimmt nicht mit der Anzahl der Städte des TSP überein!");
                        }
                    }
                }
                this.optimalRoute = optRoute;
                return optRoute;
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Die gewählte Datei wurde nicht gefunden \n" + e, file.getPath(), JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Lesen der Datei \n" + e, file.getPath(), JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) { //TODO mehr catchblöcke
                JOptionPane.showMessageDialog(null, e.getMessage(), file.getPath(), JOptionPane.ERROR_MESSAGE);
            }
         return null;
    }
    
    
    public void solveTSP() {
    }

    
    public Collection<City> getCityCollection() {
        return cityMap.values();
    }
    
    /**
     * @return the pheromonData
     */
    public double getPheromonData(int from, int to) {
        return pheromonData.get(from).get(to);
    }

    /**
     * @return the distanceData
     */
    public double getDistanceData(int from, int to) {
        City cityFrom = this.getCity(from);
        City cityTo = this.getCity(to);
        double dx = cityFrom.getXPos() - cityTo.getXPos();
        double dy = cityFrom.getYPos() - cityTo.getYPos();
        return Math.sqrt(dx*dx+dy*dy);
        //return distanceData.get(from).get(to);
    }

    /**
     * @return the cityList
     */
    public City getCity(int index) {
        return cityMap.get(index);
    }

    //public City getCityByNumber(int number) {
    //    return (City) cityMap.get(number);
    //}
    
    public int getCityListLength() {
        return cityMap.size();
    }

    public void addCity(double x, double y) {
        
        //cityTable.put(new City(x, y, maxCityNumber++));
        cityMap.put(maxCityNumber, new City(x, y, maxCityNumber++));
        //TODO Länge
        //TODO PHEROMON
    }
    
    public void addCity(double x, double y, int number) {
        if (maxCityNumber < number) {
            maxCityNumber = number + 1;
        }
        cityMap.put(number, new City(x, y, number));
    }
    
    public City getCityNearby(double x, double y, double rangeX, double rangeY) {
        for(City city : cityMap.values()) {
            if((x-(rangeX/2)) < city.getXPos() && city.getXPos() < (x+(rangeX/2)) 
                    && (y-(rangeY/2)) < city.getYPos() &&  city.getYPos() < (y+(rangeY/2))) {
                return city;
            }
        }
        return null;
    }

    public double getMaxX() {
        double max = Double.MIN_VALUE;
        for (City city : cityMap.values()) {
            if (max < city.getXPos()) {
                max = city.getXPos();
            }
        }
        return max;
    }

    public double getMaxY() {
        double max = Double.MIN_VALUE;
        for (City city : cityMap.values()) {
            if (max < city.getYPos()) {
                max = city.getYPos();
            }
        }
        return max;
    }

    public double getMinX() {
        double min = Double.MAX_VALUE;
        for (City city : cityMap.values()) {
            if (min > city.getXPos()) {
                min = city.getXPos();
            }
        }
        return min;
    }

    public double getMinY() {
        double min = Double.MAX_VALUE;
        for (City city : cityMap.values()) {
            if (min > city.getYPos()) {
                min = city.getYPos();
            }
        }
        return min;
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
    
    
    public void reCalculateDistanceData() {
    }

    /**
     * @return the optimalRoute
     */
    public Route getOptimalRoute() {
        if (this.optimalRoute != null) {
            if ((this.optimalRoute.getRoute().size() -1) == this.getCityListLength()) {
                return this.optimalRoute;
            } else {
                this.optimalRoute = null;
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * @param optimalRoute the optimalRoute to set
     */
    public void setOptimalRoute(Route optimalRoute) {
        this.optimalRoute = optimalRoute;
    }
}