/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdz.view;

import com.sdz.controller.AbstractControler;
import com.sdz.observer.Observer;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Florian
 */
public class InterfaceGraphique extends JFrame implements Observer {
    private final JPanel container = new JPanel();
    private JLabel ecran = new JLabel();
    
    //L'instance de notre objet contrôleur
    private AbstractControler controler;
    
    public InterfaceGraphique(AbstractControler controler){                
        this.setSize(240, 260);
        this.setTitle("Pimp My Fridge");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        initComposant();                
        this.controler = controler;                
        this.setContentPane(container);
        this.setVisible(true);
    }
    
    
    
    private void initComposant(){
        Font police = new Font("Arial", Font.BOLD, 20);
        ecran = new JLabel("0");
        ecran.setFont(police);
        //ecran.setText("blabla");
        JPanel panEcran = new JPanel();
        panEcran.setPreferredSize(new Dimension(220, 30));
        
        panEcran.add(ecran);
        panEcran.setBorder(BorderFactory.createLineBorder(Color.black));
        
        container.add(panEcran, BorderLayout.NORTH);
        
        /*JPanel temperaturePanel = new JPanel();        
        temperaturePanel.setPreferredSize(new Dimension(200, 225));
        container.add(temperaturePanel, BorderLayout.EAST);*/
    }
   
    
    //Implémentation du pattern observer
    @Override
    public void update(String str) {
        ecran.setText(str);
    }  
    
}