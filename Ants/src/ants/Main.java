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
        Main.data.addCity(4,3);
        Main.data.addCity(2,2);
        Main.data.addCity(2, 1);
        Main.data.addCity(4, 5);
        Main.data.addCity(6, 2);
        Main.data.addCity(7, 8);
        window.setVisible(true);
        
        Route localRoute = new Route();
        
        for (int i = 0; i < Main.data.getCityListLength(); i++)
        {
            localRoute.addCity(Main.data.getCity(i));
        }
        
        Main.data.setGlobalBest(localRoute);
        
        localRoute = new Route();
        localRoute.addCity(Main.data.getCity(5));
        localRoute.addCity(Main.data.getCity(3));
        localRoute.addCity(Main.data.getCity(1));
        localRoute.addCity(Main.data.getCity(2));
        localRoute.addCity(Main.data.getCity(0));
        localRoute.addCity(Main.data.getCity(4));
        localRoute.addCity(Main.data.getCity(5));
        
        Main.data.setLocalBest(localRoute);

    }
}
