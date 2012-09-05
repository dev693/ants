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
        double relationY = (this.getHeight()-20) / MainData.getMaxY();
        double relationX = (this.getWidth()-20) / MainData.getMaxX();
        double relation = Math.min(relationX, relationY);
        for (int i = 0; i < MainData.getCityListLength(); i++) {
            int x = (int) (MainData.getCityList(i).getXPos() * relation);
            int y = (int) (MainData.getCityList(i).getYPos() * relation);
            g.fillOval(x+10, y+10, 8, 8);
        }
    }
    
    public void refresh() {
        this.paintComponent(this.getGraphics());
    }
}
