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
        MainData.cityList = new City[5];
        MainData.cityList[0] = new City(10,20,0);
        MainData.cityList[1] = new City(25,10,1);
        MainData.cityList[2] = new City(4,8,2);
        MainData.cityList[3] = new City(30,30,3);
        MainData.cityList[4] = new City(20,20,4);
        MainWindow window = new MainWindow();
        window.setVisible(true);
        window.drawCity(234,234);
    }
}
