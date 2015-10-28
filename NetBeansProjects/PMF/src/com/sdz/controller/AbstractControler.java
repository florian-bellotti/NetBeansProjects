package com.sdz.controller;

import com.sdz.model.AbstractModel;
import java.io.IOException;


public abstract class AbstractControler {
    
    protected AbstractModel temp;
    public double temp_rosee = 0.00;
     
    public AbstractControler(AbstractModel temp){
        this.temp = temp;
    }
    
    public abstract boolean checkDoor();
    public abstract boolean checkCondensation();
    public abstract void envoiConsigne(int consigne) throws IOException;
}
