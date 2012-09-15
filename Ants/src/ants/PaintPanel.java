/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class PaintPanel extends JPanel {

    private double relation = 1;
    private int thickness = 8;
    private int borderOffset = 10;
    private int xOffset = 0;
    private int yOffset = 0;
    private int zoom = 100;    
    private boolean autoscale = true;
    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        
        if (!java.beans.Beans.isDesignTime()) {
            calcRelation();

            System.out.println("relation: " + relation + "; autoscale: " + autoscale + "; zoom: " + zoom + "; xOffset: " + getxOffset() + "; yOffset: " + getyOffset());
            g.setColor(Color.white);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            g.setColor(Color.red);
            drawRoute(g, Main.data.getGlobalBest());

            g.setColor(Color.blue);
            drawRoute(g, Main.data.getLocalBest());


            //Städte Anzeigen
            g.setColor(Color.black);
            for (int i = 0; i < Main.data.getCityListLength(); i++) {
                drawCity(g, Main.data.getCity(i));
            }
        }
    }

    public void refresh() {
        this.paintComponent(this.getGraphics());
    }

    public double XPixel2Coord(int x) {
        return (x + getxOffset() - borderOffset) / relation;
    }
    
    public double YPixel2Coord(int y) {
        return (y + getyOffset() - borderOffset) / relation;
    }
    
    
        
    public void calcRelation() {
        
        if (autoscale) {       
            double width = Main.data.getMaxX() - Main.data.getMinX();

            double height = Main.data.getMaxY() - Main.data.getMinY();

            double relationY = (this.getHeight() - 2 * borderOffset) / height;
            double relationX = (this.getWidth() - 2 * borderOffset) / width;
            System.out.println(zoom);
            relation = Math.min(relationX, relationY) * ((double) zoom / 100);
            setxOffset((int) (Main.data.getMinX()*relation));
            setyOffset((int) (Main.data.getMinY()*relation));
        } else {
            relation = (double) zoom / 100;
        }
    }

    public void drawCity(Graphics g, City city) {
        int x = (int) (city.getXPos() * relation);
        int y = (int) (city.getYPos() * relation);
        g.fillOval(x - getxOffset() + borderOffset-thickness/2, y - getyOffset() + borderOffset-thickness/2, thickness, thickness);
    }

    public void drawRoute(Graphics g, Route route) {
        City lastCity = null;
        if (route != null) {
            for (City city : route.getRoute()) {
                if (lastCity != null) {
                    g.drawLine((borderOffset) - getxOffset() + (int) (lastCity.getXPos() * relation),
                            (borderOffset) - getyOffset() + (int) (lastCity.getYPos() * relation),
                            (borderOffset) - getxOffset() + (int) (city.getXPos() * relation),
                            (borderOffset) - getyOffset() + (int) (city.getYPos() * relation));
                }
                lastCity = city;
            }
        }
    }

    /**
     * @param zoom the zoom to set
     */
    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    /**
     * @param autoscale the autoscale to set
     */
    public void setAutoscale(boolean autoscale) {
        this.autoscale = autoscale;
    }

    /**
     * @return the xOffset
     */
    public int getxOffset() {
        return xOffset;
    }

    /**
     * @param xOffset the xOffset to set
     */
    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    /**
     * @return the yOffset
     */
    public int getyOffset() {
        return yOffset;
    }

    public void addyOffset(int dy) {
        yOffset += dy;
    }
    
    public void addxOffset(int dx) {
        xOffset += dx;
    }
    
    /**
     * @param yOffset the yOffset to set
     */
    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    /**
     * @param thickness the thickness to set
     */
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }
}
