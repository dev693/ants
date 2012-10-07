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

/**
 *
 * @author user
 */
public class TSPTest {
    
    public TSPTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of loadFromFile method, of class TSP.
     */
    @Test
    public void testLoadFromFile() {
        System.out.println("loadFromFile");
        String path = "";
        TSP.loadFromFile(path);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveToFile method, of class TSP.
     */
    @Test
    public void testSaveToFile() {
        System.out.println("saveToFile");
        File file = null;
        TSP instance = new TSP();
        instance.saveToFile(file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of solveTSP method, of class TSP.
     */
    @Test
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
            tsp.addCity(i, i);
        }
        for (int i = 0; i < 10; i++) {
            City city = tsp.getCity(i);
            assertTrue(city.getXPos() == i);
            assertTrue(city.getYPos() == i);
            assertTrue(city.getNumber() == i);
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
        double x = 0.0;
        double y = 0.0;
        double rangeX = 0.0;
        double rangeY = 0.0;
        TSP instance = new TSP();
        City expResult = null;
        City result = instance.getCityNearby(x, y, rangeX, rangeY);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxX method, of class TSP.
     */
    @Test
    public void testGetMaxX() {
        System.out.println("getMaxX");
        TSP instance = new TSP();
        double expResult = 0.0;
        double result = instance.getMaxX();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxY method, of class TSP.
     */
    @Test
    public void testGetMaxY() {
        System.out.println("getMaxY");
        TSP instance = new TSP();
        double expResult = 0.0;
        double result = instance.getMaxY();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinX method, of class TSP.
     */
    @Test
    public void testGetMinX() {
        System.out.println("getMinX");
        TSP instance = new TSP();
        double expResult = 0.0;
        double result = instance.getMinX();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinY method, of class TSP.
     */
    @Test
    public void testGetMinY() {
        System.out.println("getMinY");
        TSP instance = new TSP();
        double expResult = 0.0;
        double result = instance.getMinY();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
