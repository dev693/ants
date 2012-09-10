/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

/**
 *
 * @author user
 */
public class DoubleConverter extends org.jdesktop.beansbinding.Converter<Integer, String> {

    @Override
    public String convertForward(Integer value) {
        Double dValue = ((double)value) / 10000;
        return dValue.toString();
    }

    @Override
    public Integer convertReverse(String value) {
        double dValue = Double.parseDouble(value.replace(',', '.'));
        return (int) (dValue * 10000);
    }
    
}
