/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdz.model;

import com.sdz.observer.Observer;
import gnu.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author Florian
 */
public abstract class AbstractModel implements SerialPortEventListener {
    
    //protected double tempIn = 0;
    public ArrayList<Float> tempIn = new ArrayList<>(); 
    public ArrayList<Float> tempOut = new ArrayList<>();
    public double humIn = 0;
    //protected double test = 0;
    //protected double tempOut = 0;
    protected double resOut = 0;
    //protected double resOut = 0;
    protected ArrayList<Observer> listObserver = new ArrayList<>();   
    SerialPort serialPort;
    protected static final String PORT_NAMES[] = { 
			"/dev/cu.usbmodem1411", // Mac OS X
                        "/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
    };
    protected double A = 0.001126762965772;
    protected double B = 0.000234494509833;
    protected double C = 0.000000086322022;
    protected String[] resistance = null;
    
    /**
    * A BufferedReader which will be fed by a InputStreamReader 
    * converting the bytes into characters 
    * making the displayed results codepage independent
    */
    protected BufferedReader input;
    /** The output stream to the port */
    protected OutputStream output;
    /** Milliseconds to block while waiting for port open */
    protected static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    protected static final int DATA_RATE = 9600;
    protected Object math;
    
    
    
    public abstract void initialize();
    
  
    //Implémentation du pattern observer
    public void addObserver(Observer obs) {
      this.listObserver.add(obs);
    }

    public void notifyObserver(String tempIn, String humIn, String tempOut) {
        //str = str.substring(1, str.length());

        for(Observer obs : listObserver)
            obs.update(tempIn, humIn, tempOut);
    }

    public void removeObserver() {
        listObserver = new ArrayList<Observer>();
    } 
    
    public abstract void writeData(String data) throws IOException;

}
