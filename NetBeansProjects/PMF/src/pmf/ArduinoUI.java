/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmf;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Florian
 */
public class ArduinoUI extends JFrame {
    
    public ArduinoUI() {
        this.setTitle("Pimp My Fridge");
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);               
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new Panneau());

        this.setVisible(true);
    }
}