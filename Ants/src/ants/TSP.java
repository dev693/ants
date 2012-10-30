package ants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Random;
import java.util.TreeMap;
import javax.swing.JOptionPane;

public class TSP implements Runnable {
    

    
    private String name;
    private String comment = "";
    private TreeMap<Integer, TreeMap<Integer, Double>> pheromonData = new TreeMap();
    private TreeMap<Integer, TreeMap<Integer, Double>> distanceData = new TreeMap();
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
    private double averageLocalRoute = 0;
    private double averageGlobalRoute = 0;
    private long progress = 0;
    private int maxCityNumber = 1;
    private double minX = Double.MAX_VALUE;
    private double minY = Double.MAX_VALUE;
    private double maxX = Double.MIN_VALUE;
    private double maxY = Double.MIN_VALUE;
    private double maxPheromon = 1;
    private boolean showPheromonLevel = false;
    private boolean stop = false;
    private long startTime = 0;
    private long stopTime = 0;
    private double averageStop = Double.POSITIVE_INFINITY;
    private boolean averageStopAktiv = false;
    private double bestStop = Double.POSITIVE_INFINITY;
    private boolean bestStopAktiv = false;
    private boolean optStopAktiv = false;
    private double gamma = 0;
    
    public TSP() {
    }

    public static TSP loadFromFile(String path) {
        //System.out.println(path);
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
        if (this.comment == null || this.comment.equals("")) {
            this.comment = JOptionPane.showInputDialog(Main.window, "Bitte geben Sie einen Kommentar ein: ", "Kommentar", JOptionPane.QUESTION_MESSAGE);
        }
        
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
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
            }
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
                        if (Integer.parseInt(lineParts[1].trim()) != this.getCityListLength()) {
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

                    if ((optRoute.getRoute().size() - 1) != Main.data.getCityListLength()) {
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

    public City getRandomCity() {
        int rd;
        do {
            rd = new Random().nextInt(cityMap.lastKey() + 1);
        } while (!cityMap.keySet().contains(rd));
        return cityMap.get(rd);
    }
    
    public void solveTSP() {
        try {
            this.startTime = System.currentTimeMillis();
            this.stop = false;
            this.localBest = null;
            this.globalBest = null;
            this.averageGlobalRoute = 0;
            this.averageLocalRoute = 0;
            this.progress = 0;
            this.initializePheromonData();

            for (int i = 0; i < this.iterations; i++) {
                this.localBest = null;
                this.averageLocalRoute = 0;
                for (int a = 0; a < this.ants; a++) {
                    if (stop || 
                       (this.averageStopAktiv && this.averageGlobalRoute <=  averageStop) || 
                       (this.bestStopAktiv && this.globalBest != null && this.globalBest.getLength() <= bestStop) ||
                       (this.optStopAktiv && this.optimalRoute != null && this.optimalRoute.getLength() >= this.globalBest.getLength())) {
                        Main.window.solverFinished();
                        return;
                    }

                    this.progress++;
                    City randomCity = this.getRandomCity();
                    Ant ant = new Ant(randomCity);
                    do {
                        ant.nextCity();
                    } while (!ant.isFinished());



                    if (localBest == null || ant.getRoute().getLength() < this.localBest.getLength()) {
                        localBest = ant.getRoute();

                        if (globalBest == null || localBest.getLength() < globalBest.getLength()) {
                            globalBest = localBest;
                        }
                        if (!this.showPheromonLevel) {
                            Main.window.refreshPaintPanel();
                        }

                    }

                    ant.updatePheromon();
                    this.evaporatePheromon();
                    this.averageLocalRoute = ((a * this.getAverageLocalRoute()) + ant.getRoute().getLength()) / (a+1);
                    this.averageGlobalRoute = ((i * this.getAverageGlobalRoute() ) + ant.getRoute().getLength()) / (i+1);

                    if (this.showPheromonLevel) {
                       Main.window.refreshPaintPanel();
                    }
                    this.stopTime = System.currentTimeMillis();
                    Main.window.refreshTSPInfos();

                }

                this.randomisePheromonData();
                if (this.showPheromonLevel) {
                    Main.window.refreshPaintPanel();
                }
            }

            this.stopTime = System.currentTimeMillis();
            this.localBest = null;
            Main.window.refreshPaintPanel();
            Main.window.solverFinished();
        } catch (Exception e){
            JOptionPane.showMessageDialog(Main.window, "Bei der Berechnung des TSP trat ein Schwerwiegender Fehler auf: " + e, "Fehler!", JOptionPane.ERROR_MESSAGE);
            Main.window.solverFinished();
        }
    }

    public Collection<City> getCityCollection() {
        return ((TreeMap) cityMap.clone()).values();
    }

    /**
     * @return the pheromonData
     */
    public double getPheromonData(int from, int to) {
        if (!pheromonData.isEmpty()) {
            return pheromonData.get(from).get(to);
        }
        return maxPheromon;
    }

    /**
     * @return the distanceData
     */
    public double getDistanceData(int from, int to) {
        if (distanceData.get(from) != null) {
            return distanceData.get(from).get(to);
        }
        return 0;
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

    public void initializePheromonData() {
        pheromonData = new TreeMap();
        this.maxPheromon = 0;
        for (City city : cityMap.values()) {
            pheromonData.put(city.getNumber(), new TreeMap());
            for (City innerCity : cityMap.values()) {
                if (innerCity != city) {
                    pheromonData.get(city.getNumber()).put(innerCity.getNumber(), 0.0);
                }
            }
        }
        randomisePheromonData();
    }
    
    public void randomisePheromonData() {
        this.maxPheromon = 0;
         for (City city : cityMap.values()) {
            pheromonData.put(city.getNumber(), new TreeMap());
            for (City innerCity : cityMap.values()) {
                if (innerCity != city) {
                    setPheromonData(city.getNumber(), innerCity.getNumber(), Math.random() * this.initialPheromon);
                }
            }
        }
    }

    private void setPheromonData(int from, int to, double value) {
        checkMaxPheromon(value);
        pheromonData.get(from).put(to, value);
        pheromonData.get(to).put(from, value);
    }
    
    public void addCity(double x, double y) {
        
        this.globalBest = null;
        this.localBest = null;
        
        City newCity = new City(x, y, maxCityNumber);

        cityMap.put(maxCityNumber, newCity);

        checkMinMax(newCity);

        addDistanceData(newCity);

        maxCityNumber++;

    }

    public void addCity(double x, double y, int number) {
        
        this.globalBest = null;
        this.localBest = null;
        
        if (maxCityNumber < number) {
            maxCityNumber = number + 1;
        }
        City newCity = new City(x, y, number);

        cityMap.put(number, newCity);

        checkMinMax(newCity);

        addDistanceData(newCity);
    }

    public City getCityNearby(double x, double y, double rangeX, double rangeY) {
        for (City city : cityMap.values()) {
            if ((x - (rangeX / 2)) < city.getXPos() && city.getXPos() < (x + (rangeX / 2))
                    && (y - (rangeY / 2)) < city.getYPos() && city.getYPos() < (y + (rangeY / 2))) {
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

    public void reCalculateDistanceData() {
        distanceData = new TreeMap();

        for (City city : cityMap.values()) {
            distanceData.put(city.getNumber(), new TreeMap());
            for (City innerCity : cityMap.values()) {
                if (innerCity != city) {
                    double dx = city.getXPos() - innerCity.getXPos();
                    double dy = city.getYPos() - innerCity.getYPos();
                    distanceData.get(city.getNumber()).put(innerCity.getNumber(), Math.sqrt(dx * dx + dy * dy));
                }
            }
        }
    }

    public void addDistanceData(City newCity) {
        distanceData.put(newCity.getNumber(), new TreeMap());
        for (City city : cityMap.values()) {
            if (city != newCity) {
                double distance = this.calculateDistance(city, newCity);
                distanceData.get(city.getNumber()).put(newCity.getNumber(), distance);
                distanceData.get(newCity.getNumber()).put(city.getNumber(), distance);
            }
        }
    }

    /**
     * @return the optimalRoute
     */
    public Route getOptimalRoute() {
        if (this.optimalRoute != null) {
            if ((this.optimalRoute.getRoute().size() - 1) == this.getCityListLength()) {
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

    /**
     * @return the pheromon
     */
    public double getPheromon() {
        return pheromon;
    }

    public void moveCity(double dx, double dy, City city) {
        
        this.globalBest = null;
        this.localBest = null;
        
        city.moveCity(dx, dy);
        this.recalculateMinMax();
        this.addDistanceData(city);
    }

    /**
     * @return the localInformation
     */
    public double getLocalInformation() {
        return localInformation;
    }

    /**
     * @return the evaporation
     */
    public double getEvaporation() {
        return evaporation;
    }

    /**
     * @return the initialPheromon
     */
    public double getInitialPheromon() {
        return initialPheromon;
    }

    /**
     * @return the pheromonUpdate
     */
    public double getPheromonUpdate() {
        return pheromonUpdate;
    }

    public void updatePheromonData(int from, int to, double update) {
        double localPhero = update + pheromonData.get(from).get(to);
        checkMaxPheromon(localPhero);
        pheromonData.get(from).put(to, localPhero);
        pheromonData.get(to).put(from, localPhero);
    }

    private double calculateDistance(City from, City to) {
        double dx = from.getXPos() - to.getXPos();
        double dy = from.getYPos() - to.getYPos();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void evaporatePheromon() {
        double localMaxPheromon = 0;
        for (City city : cityMap.values()) {
            for (City innerCity : cityMap.values()) {
                if (innerCity != city) {
                    double localPhero = pheromonData.get(city.getNumber()).get(innerCity.getNumber());
                    localPhero = (localPhero * (1 - this.evaporation));
                    if (localPhero > localMaxPheromon) {
                        localMaxPheromon = localPhero;
                    }
                    pheromonData.get(city.getNumber()).put(innerCity.getNumber(), localPhero);
                    pheromonData.get(innerCity.getNumber()).put(city.getNumber(), localPhero);
                }
            }
        }
        this.maxPheromon = localMaxPheromon;
    }

    private void checkMinMax(City newCity) {
        if (minY > newCity.getYPos()) {
            minY = newCity.getYPos();
        }
        if (minX > newCity.getXPos()) {
            minX = newCity.getXPos();
        }
        if (maxX < newCity.getXPos()) {
            maxX = newCity.getXPos();
        }
        if (maxY < newCity.getYPos()) {
            maxY = newCity.getYPos();
        }
    }

    private void checkMaxPheromon(double pheromon) {
        if (pheromon > this.getMaxPheromon()) {
            this.maxPheromon = pheromon;
        }
    }
    
    private void recalculateMinMax() {
        minX = Double.MAX_VALUE;
        minY = Double.MAX_VALUE;
        maxX = Double.MIN_VALUE;
        maxY = Double.MIN_VALUE;
        for (City city : cityMap.values()) {
            if (minY > city.getYPos()) {
                minY = city.getYPos();
            }
            if (minX > city.getXPos()) {
                minX = city.getXPos();
            }
            if (maxX < city.getXPos()) {
                maxX = city.getXPos();
            }
            if (maxY < city.getYPos()) {
                maxY = city.getYPos();
            }
        }
    }

    /**
     * @return the maxPheromon
     */
    public double getMaxPheromon() {
        return maxPheromon;
    }
    
    public void stopRunning() {
        this.stop = true;
    }

    /**
     * @param showPheromonLevel the showPheromonLevel to set
     */
    public void setShowPheromonLevel(boolean showPheromonLevel) {
        this.showPheromonLevel = showPheromonLevel;
    }

    @Override
    public void run() {
        solveTSP();
    }

    /**
     * @return the averageLocalRoute
     */
    public double getAverageLocalRoute() {
        return averageLocalRoute;
    }

    /**
     * @return the averageGlobalRoute
     */
    public double getAverageGlobalRoute() {
        return averageGlobalRoute;
    }

    public long getAntCount() {
        return this.progress;
    }
    
    public long getDuration() {
        return stopTime - startTime;
    }
    
    /**
     * @return the progress
     */
    public int getProgress() {
        if (this.iterations * this.ants == 0) {
            return 0;
        }
        return (int) (progress * 100) / (this.iterations * this.ants);
    }

    /**
     * @param averageStop the averageStop to set
     */
    public void setAverageStop(double averageStop) {
        this.averageStop = averageStop;
    }

    /**
     * @param averageStopAktiv the averageStopAktiv to set
     */
    public void setAverageStopAktiv(boolean averageStopAktiv) {
        this.averageStopAktiv = averageStopAktiv;
    }

    /**
     * @param bestStop the bestStop to set
     */
    public void setBestStop(double bestStop) {
        this.bestStop = bestStop;
    }

    /**
     * @param bestStopAktiv the bestStopAktiv to set
     */
    public void setBestStopAktiv(boolean bestStopAktiv) {
        this.bestStopAktiv = bestStopAktiv;
    }

    /**
     * @param optStopAktiv the optStopAktiv to set
     */
    public void setOptStopAktiv(boolean optStopAktiv) {
        this.optStopAktiv = optStopAktiv;
    }

    /**
     * @param gamma the gamma to set
     */
    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    /**
     * @return the gamma
     */
    public double getGamma() {
        return gamma;
    }
}