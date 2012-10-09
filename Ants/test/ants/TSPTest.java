/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author user
 */
public class TSPTest {

    /**
     * Test of loadFromFile method, of class TSP.
     */
    @Test
    public void testLoadFromFile() {
        System.out.println("loadFromFile");
        String path = "test/ants/berlin52.tsp";
        TSP tsp = TSP.loadFromFile(path);
        assertEquals(tsp.getCityListLength(), 52, 0);
        assertEquals(tsp.getName(), "berlin52");
        assertEquals(565, tsp.getCity(1).getXPos(), 0);
        assertEquals(1740, tsp.getCity(52).getXPos(), 0);
    }

    /**
     * Test of saveToFile method, of class TSP.
     */
    @Test
    public void testSaveToFile() {
        System.out.println("saveToFile");
        File file = new File("test/ants/test.tsp");
        TSP tsp = new TSP();
        tsp.addCity(20, 30, 10);
        tsp.addCity(50, 60, 20);
        tsp.saveToFile(file);
        TSP tsp2 = TSP.loadFromFile(file.getPath());
        assertEquals(tsp2.getCity(10).getXPos(),tsp.getCity(10).getXPos(),0);
        assertEquals(tsp2.getCity(10).getYPos(),tsp.getCity(10).getYPos(),0);
        assertEquals(tsp2.getCity(20).getXPos(),tsp.getCity(20).getXPos(),0);
        assertEquals(tsp2.getCity(20).getYPos(),tsp.getCity(20).getYPos(),0);
    }

    /**
     * Test of solveTSP method, of class TSP.
     */
    @Test
    @Ignore
    public void testSolveTSP() {
        System.out.println("solveTSP");
        TSP instance = new TSP();
        instance.solveTSP();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of getCity method, of class TSP.
     */
    @Test
    public void testGetCity() {
        System.out.println("getCity");
        TSP tsp = new TSP();
        for (int i = 0; i < 10; i++) {
            tsp.addCity(i, i, i);
        }
        for (int i = 0; i < 10; i++) {
            City city = tsp.getCity(i);
            assertEquals(city.getXPos(), i, 0);
            assertEquals(city.getYPos(), i, 0);
            assertEquals(city.getNumber(), i, 0);
        }
    }

    /**
     * Test of addCity method, of class TSP.
     */
    @Test
    public void testAddCity() {
        System.out.println("addCity");
        TSP tsp = new TSP();
        for (int i = 0; i < 10; i++) {
            tsp.addCity(i, i);
            assertTrue(tsp.getCityListLength() == i+1);
        }
    }

    /**
     * Test of getCityNearby method, of class TSP.
     */
    @Test
    public void testGetCityNearby() {
        System.out.println("getCityNearby");
        TSP tsp = new TSP();
        tsp.addCity(5.5, 2.4);
        tsp.addCity(0.3, 1.4);
        tsp.addCity(6.9, 1.3);
        tsp.addCity(9.4, 10.0);
        tsp.addCity(-5.8, 4.3);
        City city = tsp.getCityNearby(0.2, 1.5, 0.3, 0.3);
        if (city != null) {
            assertEquals(city.getXPos(), 0.3, 0.0);
            assertEquals(city.getYPos(), 1.4, 0.0);
        } else {
            fail("no city found");
        }
    }

    /**
     * Test of getMaxX method, of class TSP.
     */
    @Test
    public void testGetMaxX() {
        System.out.println("getMaxX");
        TSP tsp = new TSP();
        tsp.addCity(5.5, 2.4);
        tsp.addCity(0.3, 1.4);
        tsp.addCity(6.9, 1.3);
        tsp.addCity(9.4, 10.0);
        tsp.addCity(-5.8, 4.3);
        double expResult = 9.4;
        double result = tsp.getMaxX();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getMaxY method, of class TSP.
     */
    @Test
    public void testGetMaxY() {
        System.out.println("getMaxY");
        TSP tsp = new TSP();
        tsp.addCity(5.5, 2.4);
        tsp.addCity(0.3, 1.4);
        tsp.addCity(6.9, 1.3);
        tsp.addCity(9.4, 10.0);
        tsp.addCity(-5.8, 4.3);
        double expResult = 10.0;
        double result = tsp.getMaxY();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getMinX method, of class TSP.
     */
    @Test
    public void testGetMinX() {
        System.out.println("getMinX");
        TSP tsp = new TSP();
        tsp.addCity(5.5, 2.4);
        tsp.addCity(0.3, 1.4);
        tsp.addCity(6.9, 1.3);
        tsp.addCity(9.4, 10.0);
        tsp.addCity(-5.8, 4.3);
        double expResult = -5.8;
        double result = tsp.getMinX();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getMinY method, of class TSP.
     */
    @Test
    public void testGetMinY() {
        System.out.println("getMinY");
        TSP tsp = new TSP();
        tsp.addCity(5.5, 2.4);
        tsp.addCity(0.3, 1.4);
        tsp.addCity(6.9, 1.3);
        tsp.addCity(9.4, 10.0);
        tsp.addCity(-5.8, 4.3);
        double expResult = 1.3;
        double result = tsp.getMinY();
        assertEquals(expResult, result, 0.0);
    }
    
    @Test
    public void testGetCityListLength() {
        System.out.println("getCityListLength");
        TSP tsp = new TSP();
        tsp.addCity(5.5, 2.4, 10);
        tsp.addCity(0.3, 1.4, 20);
        tsp.addCity(6.9, 1.3, 30);
        tsp.addCity(9.4, 10.0, 40);
        tsp.addCity(-5.8, 4.3, 50);
        assertEquals(5, tsp.getCityListLength(), 0);
    }
    
    @Test
    public void testGetOptTour() {
        System.out.println("getOptTour");
        TSP tsp = TSP.loadFromFile("test/ants/berlin52.tsp");
        Route optRoute = tsp.getOptTour(new File("test/ants/berlin52.opt.tour"));
        if (optRoute != null) {
            System.out.println("Länge: " + optRoute.getLength());
            assertEquals(7542.00, optRoute.getLength(), 100.00);
        } else {
            fail("Es wirde keine Route geladen");
        }
    }

}
