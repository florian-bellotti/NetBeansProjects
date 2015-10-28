/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdz.controller;

import com.sdz.model.AbstractModel;
import java.io.IOException;

/**
 *
 * @author Florian
 */
public abstract class AbstractControler {
    
    protected AbstractModel temp;
     
    public AbstractControler(AbstractModel temp){
        this.temp = temp;
    }
    
    public abstract boolean checkDoor();
    public abstract boolean checkCondensation();
    public abstract void envoiConsigne(int consigne) throws IOException;
}
