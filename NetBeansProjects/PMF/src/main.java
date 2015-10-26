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
        AbstractControler controler = new TemperatureControler(temp);
        //Création de notre fenêtre avec le contrôleur en paramètre
        InterfaceGraphique interfaceGraph = new InterfaceGraphique(controler);
        //Ajout de la fenêtre comme observer de notre modèle
        temp.addObserver(interfaceGraph);
    }
}