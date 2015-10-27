/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdz.controller;

import com.sdz.model.AbstractModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Florian
 */
public class TemperatureControler extends AbstractControler {
    //public double tempInCtrl = 0.00;
    private AbstractModel temp;
   
    public TemperatureControler(AbstractModel temp) {
        super(temp);
        this.temp = temp; 
    }
    
    public boolean checkDoor() {
        boolean bool = false;
        double nowTemp = temp.tempIn.get(temp.tempIn.size()-1);
        
        if (temp.tempIn.size() > 10 ) {
            double lastTemp = temp.tempIn.get(temp.tempIn.size()-10);
            double diffTemp = nowTemp - lastTemp;
            
            if (diffTemp >= 0.5) {
                bool = true;
            }
        }
        if (nowTemp > 25.00) {
            bool = true;
        }
        return bool;
    }
    
    public boolean checkCondensation() {
        boolean bool = false;
                
        return bool;
    }

    
}
