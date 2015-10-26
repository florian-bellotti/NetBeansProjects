/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmf;

import gnu.io.*;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.awt.Label;
import static java.lang.Math.log;
import java.util.Enumeration;

/**
 *
 * @author Florian
 */
public class arduinoIO implements SerialPortEventListener {

	SerialPort serialPort;
        /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
			"/dev/cu.usbmodem1411", // Mac OS X
                        "/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	};
        
        DataTemperature dataTemperature = new DataTemperature();

        double A = 0.001126762965772;
        double B = 0.000234494509833;
        double C = 0.000000086322022;
        double tempIn = 0;
        double  tempOut = 0;
        String[] resistance = null;
        double resIn  = 0; 
        double resOut = 0; 
        
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;
    private Object math;

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
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

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
                                
                                resistance = inputLine.split("-");
                                resIn  = Float.valueOf(resistance[0]); 
                                resOut = Float.valueOf(resistance[1]); 
                               
                                tempIn = (1 / (A + B * Math.log(resIn) + C * Math.pow(Math.log(resIn),3))) - 273.15;
                                tempOut = (1 / (A + B * Math.log(resOut) + C * Math.pow(Math.log(resOut),3))) - 273.15;
                                
                                dataTemperature.setLastTempIn((int)tempIn);
                                //System.out.println("tempIn : " + (int)tempIn  + " C°");
                                System.out.println("tempIn : " + (int)tempIn + " C°;     tempOut : " + (int)tempOut + " C°");
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}
}

