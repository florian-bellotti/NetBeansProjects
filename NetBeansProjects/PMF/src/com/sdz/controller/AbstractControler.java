/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdz.controller;

import com.sdz.model.AbstractModel;

/**
 *
 * @author Florian
 */
public abstract class AbstractControler {
    
    protected AbstractModel temp;
     
    public AbstractControler(AbstractModel temp){
        this.temp = temp;
   }
    
    //public abstract void checkCondensation(String humIn);
}
