package ants;

import java.util.Collection;

public class Ant {

    public Ant(City start) {
        cityPool = Main.data.getCityCollection();
        cityPool.remove(start);
        route.addCity(start);
    }
    private Collection<City> cityPool = null;
    private Route route = new Route();
    private boolean finished = false;

    public void nextCity() {
        if (!finished) {
            if (!cityPool.isEmpty()) {
                City bestCity = null;
                City currentCity = getRoute().getLastCity();
                double bestProbability = 0;
                double alpha = Main.data.getPheromon();
                double beta = Main.data.getLocalInformation();
                double sum = 0;
                for (City city : cityPool) {
                    double tau = Main.data.getPheromonData(currentCity.getNumber(), city.getNumber());
                    double eta = 1 / Main.data.getDistanceData(currentCity.getNumber(), city.getNumber());

                    if (!Double.isInfinite(tau) && !Double.isInfinite(eta)) {
                        sum += (Math.pow(tau, alpha) * Math.pow(eta, beta));
                    }
                    
                }

                for (City city : cityPool) {
                    double tau = Main.data.getPheromonData(currentCity.getNumber(), city.getNumber());
                    double eta = 1 / Main.data.getDistanceData(currentCity.getNumber(), city.getNumber());
                    double probability = ((Math.pow(tau, alpha) * Math.pow(eta, beta)) / sum);
                    if (probability > bestProbability) {
                        bestProbability = probability;
                        bestCity = city;
                    }
                }

                cityPool.remove(bestCity);
                getRoute().addCity(bestCity);

            } else {
                getRoute().addCity(getRoute().getRoute().get(0));
                finished = true;
            }
        }
    }

    public void updatePheromon() {
        if (this.isFinished()) {
            City lastCity = null;
            for(City city : getRoute().getRoute()) {
                if (lastCity != null) {
                    double update = (Main.data.getPheromonUpdate() * Main.data.getGlobalBest().getLength() )/ this.getRoute().getLength();
                    //update = update * update;
                    Main.data.updatePheromonData(lastCity.getNumber(), city.getNumber(), update);
                }
                lastCity = city;
            }
        }
    }
    
    
    /**
     * @return the finished
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * @return the route
     */
    public Route getRoute() {
        return route;
    }
}