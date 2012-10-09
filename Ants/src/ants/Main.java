/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

/**
 *
 * @author user
 */
public class Main{
    public static TSP data = new TSP();
    public static MainWindow window = new MainWindow();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Main.data.addCity(40,30);
        Main.data.addCity(20,20);
        Main.data.addCity(20, 10);
        Main.data.addCity(40, 50);
        Main.data.addCity(60, 20);
        Main.data.addCity(70, 80);
        window.setVisible(true);
        
        Route localRoute = new Route();
        
        for (int i = 1; i <= Main.data.getCityListLength(); i++)
        {
            localRoute.addCity(Main.data.getCity(i));
        }
        localRoute.addCity(Main.data.getCity(1));
        Main.data.setGlobalBest(localRoute);
        
        localRoute = new Route();
        localRoute.addCity(Main.data.getCity(6));
        localRoute.addCity(Main.data.getCity(4));
        localRoute.addCity(Main.data.getCity(2));
        localRoute.addCity(Main.data.getCity(3));
        localRoute.addCity(Main.data.getCity(1));
        localRoute.addCity(Main.data.getCity(5));
        localRoute.addCity(Main.data.getCity(6));
        
        Main.data.setLocalBest(localRoute);
        /*
        Main.data = TSP.loadFromFile("test/ants/berlin52.tsp");
        Route optRoute = Main.data.getOptTour("test/ants/berlin52.opt.tour");
*/
    }
}
