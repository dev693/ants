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
            
        } else {
            route.addCity(route.getRoute().get(0));
            finished = true;
        }
     }
  }

    /**
     * @return the finished
     */
    public boolean isFinished() {
        return finished;
    }
  
  
  
  
  

}