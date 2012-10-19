/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    private int xBaseOffset = 0;
    private int yBaseOffset = 0;
    private int zoom = 100;    
    private boolean autoscale = true;
    private BasicStroke dashedline = new BasicStroke(
                                    2.0f,                      // Width
                                    BasicStroke.CAP_SQUARE,    // End cap
                                    BasicStroke.JOIN_MITER,    // Join style
                                    10.0f,                     // Miter limit
                                    new float[] {4.0f,8.0f}, // Dash pattern
                                    0.0f);                     // Dash phase
    private BasicStroke dottedline = new BasicStroke(
                                    2.0f,                      // Width
                                    BasicStroke.CAP_SQUARE,    // End cap
                                    BasicStroke.JOIN_MITER,    // Join style
                                    10.0f,                     // Miter limit
                                    new float[] {2.0f,4.0f}, // Dash pattern
                                    0.0f);      
    private BasicStroke continousline = new BasicStroke(2.0f);          
    
    
    @Override
    protected void paintComponent(Graphics g) {
        
        if (!java.beans.Beans.isDesignTime()) {
            calcRelation();
            Graphics2D g2D = (Graphics2D) g;
            
            g2D.setColor(Color.white);
            g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
            
            g2D.setColor(Color.green);
            g2D.setStroke(this.dashedline);
            drawRoute(g2D, Main.data.getOptimalRoute());


            g2D.setColor(Color.blue);
            g2D.setStroke(this.continousline);
            drawRoute(g2D, Main.data.getLocalBest());

            g2D.setColor(Color.red);
            g2D.setStroke(this.dottedline);
            drawRoute(g2D, Main.data.getGlobalBest());

            
            //g.setColor(Color.GRAY);
            //g.fillRect((this.getWidth() / 2) -1,this.getHeight() / 2 -1, 2, 2);

            //Städte Anzeigen
            for (City city : Main.data.getCityCollection()) {
                drawCity(g2D, city);
            }
        }
    }

    public void refresh() {
        this.paintComponent(this.getGraphics());
    }

    public double XPixel2Coord(int x) {
        return (x + xOffset - borderOffset) / getRelation();
    }
    
    public double YPixel2Coord(int y) {
        return (y + yOffset - borderOffset) / getRelation();
    }
    
    
        
    public void calcRelation() {
        
        if (autoscale) {       
            double width = Main.data.getMaxX() - Main.data.getMinX();

            double height = Main.data.getMaxY() - Main.data.getMinY();

            double relationY = (this.getHeight() - 2 * borderOffset) / height;
            double relationX = (this.getWidth() - 2 * borderOffset) / width;
            System.out.println(zoom);
            relation = Math.min(relationX, relationY) * ((double) zoom / 100);
            xOffset = (int) (((Main.data.getMinX()*getRelation()) + (this.getWidth() / 2) - borderOffset) * ( (double) zoom / 100)) - (this.getWidth() / 2) + borderOffset;
            yOffset = (int) (((Main.data.getMinY()*getRelation()) + (this.getHeight() / 2) - borderOffset) * ( (double) zoom / 100)) - (this.getHeight() / 2) + borderOffset;
        } else {
            relation = (double) zoom / 100;
            xOffset = (int) ((xBaseOffset + (this.getWidth() / 2) - borderOffset) * ( (double) zoom / 100)) - (this.getWidth() / 2) + borderOffset;
            yOffset = (int) ((yBaseOffset + (this.getHeight() / 2) - borderOffset) * ( (double) zoom / 100)) - (this.getHeight() / 2) + borderOffset;
        }
    }

    public void drawCity(Graphics2D g, City city) {
        g.setColor(city.getColor());
        int x = (int) (city.getXPos() * getRelation());
        int y = (int) (city.getYPos() * getRelation());
        g.fillOval(x - getxOffset() + borderOffset-thickness/2, y - getyOffset() + borderOffset-thickness/2, thickness, thickness);
    }

    public void drawRoute(Graphics2D g, Route route) {
        City lastCity = null;
        if (route != null) {
            for (City city : route.getRoute()) {
                if (lastCity != null) {
                    g.drawLine((borderOffset) - getxOffset() + (int) (lastCity.getXPos() * getRelation()),
                            (borderOffset) - getyOffset() + (int) (lastCity.getYPos() * getRelation()),
                            (borderOffset) - getxOffset() + (int) (city.getXPos() * getRelation()),
                            (borderOffset) - getyOffset() + (int) (city.getYPos() * getRelation()));
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
     * @return the yOffset
     */
    public int getyOffset() {
        return yOffset;
    }

    public void shiftX(int dx) {
        //this.xBaseOffset = ((xOffset - borderOffset + this.getWidth() / 2) * 100) / zoom - this.getWidth() / 2 + borderOffset;
        System.out.println("dx / relation: " + (dx / relation));
        this.xBaseOffset += (int) (dx / relation);
    }
    
    public void shiftY(int dy) {
        //System.out.println(  (yOffset - borderOffset + this.getHeight() /(double) 2) / ( zoom /(double) 100 ) - this.getHeight() / 2 + borderOffset);
        //this. yBaseOffset = ((yOffset - borderOffset + this.getHeight() / 2) * 100) / zoom - this.getHeight() / 2 + borderOffset;
        System.out.println("dy / relation: " + (dy / relation));
        this.yBaseOffset += (int) (dy / relation);
    }

    /**
     * @param thickness the thickness to set
     */
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public int getThickness() {
        return this.thickness;
    }
    /**
     * @return the relation
     */
    public double getRelation() {
        return relation;
    }

    void resetOffset() {
        xOffset = 0;
        yOffset = 0;
        xBaseOffset = 0;
        yBaseOffset = 0;
    }
    
    void setBestOffset() {
        xBaseOffset = (int) (Main.data.getMinX() / relation);
        yBaseOffset = (int) (Main.data.getMinY() / relation);
        xOffset = 0;
        yOffset = 0;
    }
}
