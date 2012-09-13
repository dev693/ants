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

    private double relation;
    private int thickness = 8;
    private int borderOffset = 10;
    private int xOffset = 0;
    private int yOffset = 0;
        

    @Override
    protected void paintComponent(Graphics g) {
        
        calcRelation();
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(Color.red);
        drawRoute(g, MainData.getGlobalBest());
        
        g.setColor(Color.blue);
        drawRoute(g, MainData.getLocalBest());
        
        
        //Städte Anzeigen
        g.setColor(Color.black);
        for (int i = 0; i < MainData.getCityListLength(); i++) {
            drawCity(g, MainData.getCity(i));
        }
        
    }

    public void refresh() {
        this.paintComponent(this.getGraphics());
    }

    public void calcRelation() {
        
               
        double width = MainData.getMaxX() - MainData.getMinX();
        
        double height = MainData.getMaxY() - MainData.getMinY();
        
        double relationY = (this.getHeight() - 2 * borderOffset) / height;
        double relationX = (this.getWidth() - 2 * borderOffset) / width;
        relation = Math.min(relationX, relationY);
        
        xOffset = (int) (MainData.getMinX()*relation);
        yOffset = (int) (MainData.getMinY()*relation);
    }

    public void drawCity(Graphics g, City city) {
        int x = (int) (city.getXPos() * relation);
        int y = (int) (city.getYPos() * relation);
        g.fillOval(x - xOffset + borderOffset-thickness/2, y - yOffset + borderOffset-thickness/2, thickness, thickness);
    }

    public void drawRoute(Graphics g, Route route) {
        City lastCity = null;
        if (route != null) {
            for (City city : route.getRoute()) {
                if (lastCity != null) {
                    g.drawLine((borderOffset) - xOffset + (int) (lastCity.getXPos() * relation),
                            (borderOffset) - yOffset + (int) (lastCity.getYPos() * relation),
                            (borderOffset) - xOffset + (int) (city.getXPos() * relation),
                            (borderOffset) - yOffset + (int) (city.getYPos() * relation));
                }
                lastCity = city;
            }
        }
    }
}
