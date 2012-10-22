/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
    private boolean showPheromonLevel = false;
    private int transparency = 100;
    private BufferedImage buffer = null;
    private BufferedImage backBuffer = null;
    private BasicStroke dashedline = new BasicStroke(
                                    2.0f,                      // Width
                                    BasicStroke.CAP_SQUARE,    // End cap
                                    BasicStroke.JOIN_MITER,    // Join style
                                    10.0f,                     // Miter limit
                                    new float[] {4.0f,8.0f}, // Dash pattern
                                    0.0f);                     // Dash phase
    private BasicStroke normalline = new BasicStroke(2.0f);          
    private BasicStroke smallline = new BasicStroke(1.0f);    
    private BasicStroke bigline = new BasicStroke(5.0f);
    
    
    @Override
    protected void paintComponent(Graphics g) {
        
        if (!java.beans.Beans.isDesignTime()) {
            if (buffer != null) {
                g.drawImage(buffer, 0, 0, buffer.getWidth(), buffer.getHeight(), null);
            }
            
            backBuffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2D = (Graphics2D) backBuffer.getGraphics();
            
            calcRelation();
            
            g2D.setColor(Color.white);
            g2D.fillRect(0, 0, this.getWidth(), this.getHeight());          
            
            if (this.showPheromonLevel) {
                g2D.setStroke(this.normalline);
                drawPheromonLevels(g2D, transparency / 2);
            }
            
            //g2D.setColor(Color.green);
            g2D.setColor(new Color(0,255,0,transparency)); // Green
            g2D.setStroke(this.normalline);
            drawRoute(g2D, Main.data.getOptimalRoute());


            //g2D.setColor(Color.blue);
            g2D.setColor(new Color(0,0,255,transparency)); // Green
            g2D.setStroke(this.normalline);
            drawRoute(g2D, Main.data.getLocalBest());

            //g2D.setColor(Color.red);
            g2D.setColor(new Color(255,0,0,transparency)); // Green
            g2D.setStroke(this.normalline);
            drawRoute(g2D, Main.data.getGlobalBest());


            //Städte Anzeigen
            for (City city : Main.data.getCityCollection()) {
                drawCity(g2D, city);
            }
            
            g.drawImage(backBuffer, 0, 0, null);
            buffer = backBuffer;
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
    
    private void drawPheromonLevels(Graphics2D g, int alpha) {
        for (City city : Main.data.getCityCollection()) {
            for (City innerCity : Main.data.getCityCollection()) {
                if (innerCity != city) {
                    
                    double pheromon = Main.data.getPheromonData(city.getNumber(), innerCity.getNumber());
                    double max = Main.data.getMaxPheromon();
                    int color = (255 - (int) ((pheromon / max + 0.00001) * 255)) % 256;
                    g.setColor(new Color(color, color, color, alpha));
                    
                    g.drawLine((borderOffset) - getxOffset() + (int) (innerCity.getXPos() * getRelation()),
                            (borderOffset) - getyOffset() + (int) (innerCity.getYPos() * getRelation()),
                            (borderOffset) - getxOffset() + (int) (city.getXPos() * getRelation()),
                            (borderOffset) - getyOffset() + (int) (city.getYPos() * getRelation()));
                }
            }
        }
    }
    
        
    private void calcRelation() {
        
        if (autoscale) {       
            double width = Main.data.getMaxX() - Main.data.getMinX();

            double height = Main.data.getMaxY() - Main.data.getMinY();

            double relationY = (this.getHeight() - 2 * borderOffset) / height;
            double relationX = (this.getWidth() - 2 * borderOffset) / width;
            //System.out.println(zoom);
            relation = Math.min(relationX, relationY) * ((double) zoom / 100);
            xOffset = (int) (((Main.data.getMinX()*getRelation()) + (this.getWidth() / 2) - borderOffset) * ( (double) zoom / 100)) - (this.getWidth() / 2) + borderOffset;
            yOffset = (int) (((Main.data.getMinY()*getRelation()) + (this.getHeight() / 2) - borderOffset) * ( (double) zoom / 100)) - (this.getHeight() / 2) + borderOffset;
        } else {
            relation = (double) zoom / 100;
            xOffset = (int) ((xBaseOffset + (this.getWidth() / 2) - borderOffset) * ( (double) zoom / 100)) - (this.getWidth() / 2) + borderOffset;
            yOffset = (int) ((yBaseOffset + (this.getHeight() / 2) - borderOffset) * ( (double) zoom / 100)) - (this.getHeight() / 2) + borderOffset;
        }
    }

    private void drawCity(Graphics2D g, City city) {
        g.setColor(city.getColor());
        int x = (int) (city.getXPos() * getRelation());
        int y = (int) (city.getYPos() * getRelation());
        g.fillOval(x - getxOffset() + borderOffset-thickness/2, y - getyOffset() + borderOffset-thickness/2, thickness, thickness);
    }

    private void drawRoute(Graphics2D g, Route route) {
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
        //System.out.println("dx / relation: " + (dx / relation));
        this.xBaseOffset += (int) (dx / relation);
    }
    
    public void shiftY(int dy) {
        //System.out.println(  (yOffset - borderOffset + this.getHeight() /(double) 2) / ( zoom /(double) 100 ) - this.getHeight() / 2 + borderOffset);
        //this. yBaseOffset = ((yOffset - borderOffset + this.getHeight() / 2) * 100) / zoom - this.getHeight() / 2 + borderOffset;
        //System.out.println("dy / relation: " + (dy / relation));
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

    public void resetOffset() {
        xOffset = 0;
        yOffset = 0;
        xBaseOffset = 0;
        yBaseOffset = 0;
    }
    
    public void setBestOffset() {
        xBaseOffset = (int) (Main.data.getMinX() / relation);
        yBaseOffset = (int) (Main.data.getMinY() / relation);
        xOffset = 0;
        yOffset = 0;
    }

    /**
     * @param showPheromonLevel the showPheromonLevel to set
     */
    public void setShowPheromonLevel(boolean showPheromonLevel) {
        this.showPheromonLevel = showPheromonLevel;
    }

    /**
     * @param transparency the transparency to set
     */
    public void setTransparency(int transparency) {
        if (transparency > 255) {
            this.transparency = 255;
        } else {
            this.transparency = transparency;
        }
    }
}
