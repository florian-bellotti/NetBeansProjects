/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdz.controller;

import com.sdz.model.AbstractModel;
import java.io.IOException;
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
        
        
        if (nowTemp >= 25.00) {
            bool = true;
        }
        
        
        return bool;
    }
    
    public boolean checkCondensation() {
        boolean bool = false;
        double temp_rosee = 0.00;
        double alpha = 0.00;
        double a = 17.27;
        double b = 237.7;
        
        
        // humidité relative en nombre (donné par capteur)
        double humidity = (temp.humIn) / 100;
                
        
        // temperature donnée par capteur
        float tempOut = temp.tempOut.get(temp.tempOut.size()-1);
        float tempIn = temp.tempIn.get(temp.tempIn.size()-1);
      

        //Calcul du point de rosée
        alpha = (a*tempOut)/(b+tempOut) + Math.log(humidity);   
        temp_rosee = (b*alpha)/(a-alpha);
      
        System.out.println(temp_rosee);
        //si température intérieure est inf ou egal à température de rosé, alors ALERT ROUGE
        if (tempIn <= temp_rosee){
            bool = true;
        }
        
        //return la valeur du boolean
        return bool;
    }
    
    public void envoiConsigne(int consigne) throws IOException {
        if (consigne <= temp.tempIn.get(temp.tempIn.size()-1)) {
            temp.writeData(String.valueOf(0));
        } else {
            temp.writeData(String.valueOf(1));
        }
        
        //temp.writeData(consigne <= temp.tempIn.get(temp.tempIn.size()-1));
    }

    
}
