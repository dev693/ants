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
        MainData.cityList[0] = new City(565,575,0);
        MainData.cityList[1] = new City(25,185,1);
        MainData.cityList[2] = new City(345,750,2);
        MainData.cityList[3] = new City(954,685,3);
        MainData.cityList[4] = new City(845,655,4);
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}
