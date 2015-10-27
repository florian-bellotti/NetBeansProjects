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
    private JLabel labelTempIn = new JLabel();
    private JLabel labelTempOut = new JLabel();
    private JLabel labelTextTempIn = new JLabel();
    private JLabel labelTextTempOut = new JLabel();
    
    //L'instance de notre objet contrôleur
    private AbstractControler controler;
    
    public InterfaceGraphique(AbstractControler controler){                
        this.setSize(500, 300);
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
        labelTextTempIn= new JLabel("Température intérieure :");
        labelTextTempIn.setFont(police);
        labelTempIn = new JLabel("21 °C");
        labelTempIn.setFont(police);
        labelTextTempOut= new JLabel("Température exterieure :");
        labelTextTempOut.setFont(police);
        labelTempOut = new JLabel("12 °C");
        labelTempOut.setFont(police);
        
        //panel température intérieure
        JPanel panTempIn = new JPanel();
        panTempIn.setPreferredSize(new Dimension(340, 35));
        panTempIn.add(labelTextTempIn);
        panTempIn.add(labelTempIn);
        panTempIn.setBorder(BorderFactory.createLineBorder(Color.black));
        
        //panel température extérieure
        JPanel panTempOut = new JPanel();
        panTempOut.setPreferredSize(new Dimension(340, 35));
        panTempOut.add(labelTextTempOut);
        panTempOut.add(labelTempOut);
        panTempOut.setBorder(BorderFactory.createLineBorder(Color.black));
        
        container.add(panTempIn, BorderLayout.NORTH);
        container.add(panTempOut, BorderLayout.NORTH);
        
        /*JPanel temperaturePanel = new JPanel();        
        temperaturePanel.setPreferredSize(new Dimension(200, 225));
        container.add(temperaturePanel, BorderLayout.EAST);*/
    }
   
    
    //Implémentation du pattern observer
    @Override
    public void update(String tempIn, String tempOut) {
        labelTempIn.setText(tempIn + " °C");
        labelTempOut.setText(tempOut + " °C");
    }  
    
}