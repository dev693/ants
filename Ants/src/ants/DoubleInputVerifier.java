/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

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
            double value = Double.parseDouble(field.getText());
            if (value > lBound && value < uBound) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
}
