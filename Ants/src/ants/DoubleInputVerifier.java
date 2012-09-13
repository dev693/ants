/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author user
 */
public class DoubleInputVerifier extends javax.swing.InputVerifier{

    private double lBound = Double.NEGATIVE_INFINITY;
    private double uBound = Double.POSITIVE_INFINITY;
    
    public DoubleInputVerifier(double lowerBound, double upperBound) {
        this.lBound = lowerBound;
        this.uBound = upperBound;
    }
    
    public DoubleInputVerifier(double lowerBound) {
        this.lBound = lowerBound;
    }
    
    @Override
    public boolean verify(JComponent input) {
        JTextField field = (JTextField) input;
        try {
            double value = Double.parseDouble(field.getText().replace(',', '.'));
            if (value > lBound && value <= uBound) {
                field.setForeground(Color.black);
                return true;
                
            } else {
                field.setForeground(Color.red);
                return false;
            }
        } catch (Exception e) {
            field.setForeground(Color.red);
            return false;
        }
    }
    
}
