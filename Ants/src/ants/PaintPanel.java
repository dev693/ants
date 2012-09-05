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
        for (int i = 0; i < MainData.cityList.length; i++) {
            g.drawOval(MainData.cityList[i].getXPos(), MainData.cityList[i].getYPos(), 2, 2);
        }
    }
    
    public void refresh() {
        this.paintComponent(this.getGraphics());
    }
}
