/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.sdz.controller.*;
import com.sdz.model.*;
import com.sdz.view.*;

/**
 *
 * @author Florian
 */
public class main {
    public static void main(String[] args) {
        //Instanciation de notre modèle
        AbstractModel temp = new Temperature();
        //Création du contrôleur
        
        
        //arduinoIO main = new arduinoIO();
	temp.initialize();
	Thread t=new Thread() {
		public void run() {
			//the following line will keep this app alive for 1000 seconds,
			//waiting for events to occur and responding to them (printing incoming messages to console).
			try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
		}
	};
	t.start();
	System.out.println("Started");
        
        
        AbstractControler controler = new TemperatureControler(temp);
        //Création de notre fenêtre avec le contrôleur en paramètre
        InterfaceGraphique interfaceGraph = new InterfaceGraphique(controler);
        //Ajout de la fenêtre comme observer de notre modèle
        temp.addObserver(interfaceGraph);
    }
}