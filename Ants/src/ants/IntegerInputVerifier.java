/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

import java.awt.Color;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author user
 */
public class IntegerInputVerifier extends InputVerifier{

    @Override
    public boolean verify(JComponent input) {
        JTextField field = (JTextField) input;
        try {
            Integer.parseInt(field.getText());
            field.setBackground(Color.white);
            return true;
        } catch(Exception e) {
            field.setBackground(Color.orange);
            return false;
        }
    }
    
}
