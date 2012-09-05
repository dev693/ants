/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class PaintPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        for (int i = 0; i < MainData.getCityListLength(); i++) {
            this.getHeight();
            this.getWidth();
            g.fillOval(MainData.getCityList(i).getXPos(), MainData.getCityList(i).getYPos(), 8, 8);
        }
    }
    
    public void refresh() {
        this.paintComponent(this.getGraphics());
    }
}
