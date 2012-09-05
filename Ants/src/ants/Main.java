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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainData.addCity(0.4,0.30);
        MainData.addCity(0.20,0.20);
        MainData.addCity(0.20, 0.10);
        MainData.addCity(0.40, 0.50);
        MainData.addCity(0.60, 0.20);
        MainData.addCity(0.70, 0.80);
        MainWindow window = new MainWindow();
        window.setVisible(true);
        
        Route localRoute = new Route();
        
        for (int i = 0; i < MainData.getCityListLength(); i++)
        {
            localRoute.addCity(MainData.getCity(i));
        }
        
        MainData.setGlobalBest(localRoute);
        
        localRoute = new Route();
        localRoute.addCity(MainData.getCity(5));
        localRoute.addCity(MainData.getCity(3));
        localRoute.addCity(MainData.getCity(1));
        localRoute.addCity(MainData.getCity(2));
        localRoute.addCity(MainData.getCity(0));
        localRoute.addCity(MainData.getCity(4));
        localRoute.addCity(MainData.getCity(5));
        
        MainData.setLocalBest(localRoute);
        
    }
}
