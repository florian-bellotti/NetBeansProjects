/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdz.view;

import arduino.data.control.Const;
import arduino.soundplayer.JavaxSoundPlayer;
import com.sdz.controller.AbstractControler;
import com.sdz.observer.Observer;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 *
 * @author Florian
 */
public class InterfaceGraphique extends JFrame implements Observer {
    
    //boolean pour afficher ou non les alertes
    boolean boolDoor = false;
    boolean boolCond = false;
    
    
    //initialisation des différents Panel/Label/Slider
    private final JPanel container = new JPanel();
    private JLabel labelTempIn = new JLabel();
    private JLabel labelTempOut = new JLabel();
    private JLabel labelTempCons = new JLabel();
    private JLabel labelTitreTempIn = new JLabel();
    private JLabel labelTitreCons = new JLabel();
    private JLabel labelTitreTempOut = new JLabel();
    private JLabel labelHumpIn = new JLabel();
    private JLabel labelWarnDoor = new JLabel();
    private JLabel labelWarnCond = new JLabel();
    private JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 30, 15);
    private JavaxSoundPlayer jsp;
    
    
    //Définition du nombre de graphiques
    public static final int SUBPLOT_COUNT_IN = 2;
    public static final int SUBPLOT_COUNT_OUT = 1;
    
    
    //Définitin des variables de données
    private TimeSeriesCollection[] datasetsIn;
    private TimeSeriesCollection[] datasetsOut;
    
    
    //Définition des varibles pour stocker la derniere valeur du graphique
    private double[] lastValueIn = new double[SUBPLOT_COUNT_IN];
    private double[] lastValueOut = new double[SUBPLOT_COUNT_OUT];
    
    
    //L'instance de notre objet contrôleur
    private AbstractControler controler;

    
    //Définition de notre police
    Font police = new Font("Arial", Font.BOLD, 20);
    
    public int consigne = 15;
    
    public JLabel getLabelTempCons() {
        return labelTempCons;
    }
    
    
    //constructeur de notre classe
    public InterfaceGraphique(AbstractControler controler) throws Exception{                
        this.setSize(500, 750);
        this.setTitle("Pimp My Fridge");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        initComposantConsigne();
        initComposant();                
        this.controler = controler;                
        this.setContentPane(container);
        this.setVisible(true);
    }
    
    
    //initialisation de notre panel de la consigne 
    private void initComposantConsigne(){
        
        //Définition des labels
        labelTitreCons = new JLabel("Consigne :   ");
        labelTitreCons.setFont(police);
        labelTempCons = new JLabel("15 °C   ");
        labelTempCons.setFont(police);
        
        
        //Définition de notre Slider
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                consigne = source.getValue();
                labelTempCons.setText(consigne + " °C   ");
                try {
                    controler.envoiConsigne(consigne);
                } catch (IOException ex) {
                    Logger.getLogger(InterfaceGraphique.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
         });
        
        
        //Définition de notre panel
        JPanel panConsigne = new JPanel();
        panConsigne.setPreferredSize(new Dimension(490, 70));
        panConsigne.add(labelTitreCons);
        panConsigne.add(labelTempCons); 
        panConsigne.add(slider);
        panConsigne.setBorder(BorderFactory.createLineBorder(Color.black));
        
        
        //on ajoute notre panel au container
        container.add(panConsigne, BorderLayout.NORTH);
    }
    
    
    private void initComposant() throws Exception{
        labelWarnDoor = new JLabel("Attention : Porte ouverte !!!");
        labelWarnDoor.setFont(police);
        labelWarnDoor.setVisible(boolDoor);
        labelWarnDoor.setForeground(Color.red);
        
        labelWarnCond = new JLabel("Attention : Condensation !!!");
        labelWarnCond.setFont(police);
        labelWarnCond.setVisible(boolCond);
        labelWarnCond.setForeground(Color.red);
        
        labelTitreTempIn = new JLabel("Intérieur :   ");
        labelTitreTempIn.setFont(police);
        
        labelTempIn = new JLabel("21 °C   ");
        labelTempIn.setFont(police);
        
        labelHumpIn = new JLabel("50 %   ");
        labelHumpIn.setFont(police);
        
        labelTitreTempOut= new JLabel("Exterieur :");
        labelTitreTempOut.setFont(police);
        
        labelTempOut = new JLabel("12 °C");
        labelTempOut.setFont(police);        
        
        final CombinedDomainXYPlot plotIn = new CombinedDomainXYPlot(new DateAxis("Temps"));
        final CombinedDomainXYPlot plotOut = new CombinedDomainXYPlot(new DateAxis("Temps"));
        this.datasetsIn = new TimeSeriesCollection[SUBPLOT_COUNT_IN];
        this.datasetsOut = new TimeSeriesCollection[SUBPLOT_COUNT_OUT];
        
        String[] nameSeriesIn = {"Température","Humidité"};
        String[] nameAxisIn = {"Température (°C)","pourcentage (%)"};
        
        for (int i = 0; i < SUBPLOT_COUNT_IN; i++) {
            this.lastValueIn[i] = 100.0;
            final TimeSeries seriesIn = new TimeSeries(nameSeriesIn[i], Millisecond.class);
            this.datasetsIn[i] = new TimeSeriesCollection(seriesIn);
            final NumberAxis rangeAxisIn = new NumberAxis(nameAxisIn[i]);
            rangeAxisIn.setAutoRangeIncludesZero(false);
            final XYPlot subplotIn = new XYPlot(
                    this.datasetsIn[i], null, rangeAxisIn, new StandardXYItemRenderer()
            );
            subplotIn.setBackgroundPaint(Color.lightGray);
            subplotIn.setDomainGridlinePaint(Color.white);
            subplotIn.setRangeGridlinePaint(Color.white);
            plotIn.add(subplotIn);
        }
        
        String[] nameSeriesOut = {"Température"};
        String[] nameAxisOut = {"Température (°C)"};
        
        for (int i = 0; i < SUBPLOT_COUNT_OUT; i++) {
            this.lastValueOut[i] = 100.0;
            final TimeSeries seriesOut = new TimeSeries(nameSeriesOut[i], Millisecond.class);
            this.datasetsOut[i] = new TimeSeriesCollection(seriesOut);
            final NumberAxis rangeAxisOut = new NumberAxis(nameAxisOut[i]);
            rangeAxisOut.setAutoRangeIncludesZero(false);
            final XYPlot subplotOut = new XYPlot(
                    this.datasetsOut[i], null, rangeAxisOut, new StandardXYItemRenderer()
            );
            subplotOut.setBackgroundPaint(Color.lightGray);
            subplotOut.setDomainGridlinePaint(Color.white);
            subplotOut.setRangeGridlinePaint(Color.white);
            plotOut.add(subplotOut);
        }

        final JFreeChart chartIn = new JFreeChart("", plotIn);
        chartIn.setBorderPaint(Color.black);
        chartIn.setBorderVisible(true);
        chartIn.setBackgroundPaint(Color.white);
        
        final JFreeChart chartOut = new JFreeChart("", plotOut);
        chartOut.setBorderPaint(Color.black);
        chartOut.setBorderVisible(true);
        chartOut.setBackgroundPaint(Color.white);
        
        plotIn.setBackgroundPaint(Color.lightGray);
        plotIn.setDomainGridlinePaint(Color.white);
        plotIn.setRangeGridlinePaint(Color.white);
        
        plotOut.setBackgroundPaint(Color.lightGray);
        plotOut.setDomainGridlinePaint(Color.white);
        plotOut.setRangeGridlinePaint(Color.white);
        
        final ValueAxis axisIn = plotIn.getDomainAxis();
        axisIn.setAutoRange(true);
        axisIn.setFixedAutoRange(240000.0);  // 60 seconds
        
        final ValueAxis axisOut = plotOut.getDomainAxis();
        axisOut.setAutoRange(true);
        axisOut.setFixedAutoRange(240000.0);  // 60 seconds
        
        final ChartPanel chartPanelIn = new ChartPanel(chartIn);
        final ChartPanel chartPanelOut = new ChartPanel(chartOut);

        
        chartPanelIn.setPreferredSize(new java.awt.Dimension(480, 300));
        chartPanelIn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        chartPanelOut.setPreferredSize(new java.awt.Dimension(480, 200));
        chartPanelOut.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        //panel température intérieure
        JPanel panTempIn = new JPanel();
        panTempIn.setPreferredSize(new Dimension(490, 400));
        panTempIn.add(labelTitreTempIn);
        panTempIn.add(labelTempIn); 
        panTempIn.add(labelHumpIn);
        panTempIn.add(chartPanelIn);
        panTempIn.add(labelWarnDoor);
        panTempIn.add(labelWarnCond);
        panTempIn.setBorder(BorderFactory.createLineBorder(Color.black));
        
        
        //panel température extérieure
        JPanel panTempOut = new JPanel();
        panTempOut.setPreferredSize(new Dimension(490, 240));
        panTempOut.add(labelTitreTempOut);
        panTempOut.add(labelTempOut);
        panTempOut.add(chartPanelOut);
        panTempOut.setBorder(BorderFactory.createLineBorder(Color.black));
        
        container.add(panTempIn, BorderLayout.NORTH);
        container.add(panTempOut, BorderLayout.NORTH);
    }
   
    
    //Implémentation du pattern observer
    @Override
    public void update(String tempIn, String humIn, String tempOut) {
        try {
            controler.envoiConsigne(consigne);
        } catch (IOException ex) {
            Logger.getLogger(InterfaceGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        boolean boolDoorOld = boolDoor;
        boolDoor = controler.checkDoor();
        labelWarnDoor.setVisible(boolDoor);
        
        boolean boolCondOld = boolCond;
        boolCond = controler.checkCondensation();
        labelWarnCond.setVisible(boolCond);
        
        if (boolDoor == true || boolCond == true) {
            if (boolDoorOld != boolDoor || boolCondOld != boolCond) {
                System.out.println("erreur");
                try {
                    jsp = new JavaxSoundPlayer(Const._SOUND_PATH);
                } catch (Exception ex) {
                    Logger.getLogger(InterfaceGraphique.class.getName()).log(Level.SEVERE, null, ex);
                }
                jsp.play(); 
            } 
        }
        
        this.lastValueIn[0] = Double.valueOf(tempIn);
        this.lastValueIn[1] = Double.valueOf(humIn);
        this.lastValueOut[0] = Double.valueOf(tempOut);
        
        labelTempIn.setText(tempIn + " °C   ");
        labelHumpIn.setText(humIn + " %   ");
        labelTempOut.setText(tempOut + " °C   ");
        
        for (int i = 0; i < SUBPLOT_COUNT_IN; i++) {
            final Millisecond now = new Millisecond();
            this.datasetsIn[i].getSeries(0).add(new Millisecond(), this.lastValueIn[i]);  
        }
        
        for (int i = 0; i < SUBPLOT_COUNT_OUT; i++) {
            final Millisecond now = new Millisecond();
            this.datasetsOut[i].getSeries(0).add(new Millisecond(), this.lastValueOut[i]);  
        }
    }  
}