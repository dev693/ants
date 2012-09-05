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
        MainData.addCity(30,30);
        MainData.addCity(20,20);
        MainData.addCity(20, 10);
        MainData.addCity(40, 50);
        MainData.addCity(60, 20);
        MainData.addCity(70, 80);
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}
