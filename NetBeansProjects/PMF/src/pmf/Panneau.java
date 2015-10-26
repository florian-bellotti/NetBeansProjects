/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmf;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Florian
 */
public class Panneau extends JPanel { 
    
    String tempIn = "16 °C";
    //String tempIn = main.tempIn;
    String tempOut = "22 °C";
    
    arduinoIO main = new arduinoIO();
    
    public void paintComponent(Graphics g){                
        Font font = new Font("Courier", Font.BOLD, 20);
        g.setFont(font);  
        
        g.drawString("Température Ext", 10, 30);
        tempIn = main.dataTemperature.getLastTempIn() + " °C";
        g.drawString(tempIn, 60, 60);
        
        g.drawString("Température Int", 400, 30);  
        g.drawString(tempOut, 460, 60);
    }                       
}


