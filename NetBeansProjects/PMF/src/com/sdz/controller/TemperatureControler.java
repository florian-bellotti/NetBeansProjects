package com.sdz.controller;

import com.sdz.model.AbstractModel;
import java.io.IOException;


public class TemperatureControler extends AbstractControler {
    private AbstractModel temp;

    //constructeur de notre classe
    public TemperatureControler(AbstractModel temp) {
        super(temp);
        this.temp = temp; 
    }

    //fonction de vérification de température pour la porte
    @Override
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
    
    //fonction de vérification de condensation (point de rosée)
    @Override
    public boolean checkCondensation() {
        boolean bool = false;
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
        this.temp_rosee = (b*alpha)/(a-alpha);
        //si température intérieure est inf ou egal à température de rosé, alors ALERT ROUGE
        if (tempIn <= this.temp_rosee){
            bool = true;
        }
        return bool;
    }
    
    @Override
    public void envoiConsigne(int consigne) throws IOException {
        if (consigne <= temp.tempIn.get(temp.tempIn.size()-1)) {
            temp.writeData(String.valueOf(0));
        } else {
            temp.writeData(String.valueOf(1));
        }
    }

    
}
