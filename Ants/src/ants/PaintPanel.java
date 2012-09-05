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

    @Override
    protected void paintComponent(Graphics g) {
        double relationY = (this.getHeight() - 20) / MainData.getMaxY();
        double relationX = (this.getWidth() - 20) / MainData.getMaxX();
        double relation = Math.min(relationX, relationY);

        //Städte Anzeigen
        for (int i = 0; i < MainData.getCityListLength(); i++) {
            int x = (int) (MainData.getCity(i).getXPos() * relation);
            int y = (int) (MainData.getCity(i).getYPos() * relation);
            g.fillOval(x + 10, y + 10, 8, 8);
        }

        //Routen darstellen
        City lastCity = null;
        if (MainData.getGlobalBest() != null) {
            //Globalbeste
            g.setColor(Color.blue);
            for (City city : MainData.getGlobalBest().getRoute()) {
                if (lastCity != null) {
                    g.drawLine(14+(int) (lastCity.getXPos() * relation),
                            14+(int) (lastCity.getYPos() * relation),
                            14+(int) (city.getXPos() * relation),
                            14+(int) (city.getYPos() * relation));
                }
                lastCity = city;
            }
        }

        //lokal beste Route
        lastCity = null;

        if (MainData.getLocalBest() != null) {
            g.setColor(Color.red);
            for (City city : MainData.getLocalBest().getRoute()) {
                if (lastCity != null) {
                    g.drawLine(14+(int) (lastCity.getXPos() * relation),
                            14+(int) (lastCity.getYPos() * relation),
                            14+(int) (city.getXPos() * relation),
                            14+(int) (city.getYPos() * relation));
                }
                lastCity = city;
            }
        }
    }

    public void refresh() {
        this.paintComponent(this.getGraphics());
    }
}
