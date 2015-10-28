/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdz.model;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;


;

/**
 *
 * @author Florian
 */
public class Temperature extends AbstractModel {
            
    public void initialize() {
        CommPortIdentifier portId = null;
        
	Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
		if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
		}
            }
	}
	
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
	}

	try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
				SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
	} catch (Exception e) {
		System.err.println(e.toString());
	}
    }

    /**
    * This should be called when you stop using the port.
    * This will prevent port locking on platforms like Linux.
    */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
	}
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
    */
    public synchronized void serialEvent(SerialPortEvent oEvent) {
	if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
		String inputLine=input.readLine();
                                
                this.resistance = inputLine.split("-");
                this.tempIn.add(Float.valueOf(this.resistance[0]));
                //this.tempIn  = Float.valueOf(this.resistance[0]); 
                this.humIn = Float.valueOf(this.resistance[1]); 
                this.resOut = Float.valueOf(this.resistance[2]); 
                if (this.resistance.length >= 4) {
                //this.test = Float.valueOf(this.resistance[3]); 
                System.out.println(this.resistance[3]);
                }
                
                this.resOut = (1 / (A + B * Math.log(this.resOut) + C * Math.pow(Math.log(this.resOut),3))) - 273.15;
                this.tempOut.add((float)this.resOut);
                
                //tempOut = (1 / (A + B * Math.log(resOut) + C * Math.pow(Math.log(resOut),3))) - 273.15;
                                
                //dataTemperature.setLastTempIn((int)tempIn);
                //System.out.println("tempIn : " + (int)this.tempIn  + " C°");
                
                //On lance aussi la mise à jour !
                notifyObserver(String.valueOf((float)this.tempIn.get(tempIn.size()-1)), String.valueOf((int)this.humIn), String.valueOf((float)this.tempOut.get(tempOut.size()-1)));
                
                //System.out.println("Consigne : " + (float)this.tempIn.get(tempIn.size()-1) + " C°;     tempOut : " + (int)this.tempOut.get(tempOut.size()-1) + " C°");
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }
    
    public void writeData(String data) throws IOException {
        System.out.println(data);
        output.write(data.getBytes());
    }
}
