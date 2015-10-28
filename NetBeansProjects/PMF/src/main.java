import com.sdz.controller.*;
import com.sdz.model.*;
import com.sdz.view.*;

public class main {
    public static void main(String[] args) throws Exception {
        //Instanciation de notre modèle
        AbstractModel temp = new Temperature();
        //Création du contrôleur
	temp.initialize();
	Thread t=new Thread() {
		public void run() {
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