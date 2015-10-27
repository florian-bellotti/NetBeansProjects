/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdz.view;

import com.sdz.controller.AbstractControler;
import com.sdz.observer.Observer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.ChartFactory;
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
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author Florian
 */
public class InterfaceGraphique extends JFrame implements Observer {
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
    private JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, 0, 30, 15);
    
    
        /** The number of subplots. */
    public static final int SUBPLOT_COUNT_IN = 2;
    public static final int SUBPLOT_COUNT_OUT = 1;
    
    /** The datasets. */
    private TimeSeriesCollection[] datasetsIn;
    private TimeSeriesCollection[] datasetsOut;
    
    /** The most recent value added to series 1. */
    private double[] lastValueIn = new double[SUBPLOT_COUNT_IN];
    private double[] lastValueOut = new double[SUBPLOT_COUNT_OUT];

    
    
    
    //L'instance de notre objet contrôleur
    private AbstractControler controler;

    public JLabel getLabelTempCons() {
        return labelTempCons;
    }
    
    public InterfaceGraphique(AbstractControler controler){                
        this.setSize(500, 750);
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
        
        labelWarnDoor = new JLabel("Attention : Porte ouverte !!!");
        labelWarnDoor.setFont(police);
        labelWarnDoor.setVisible(false);
        
        labelWarnCond = new JLabel("Attention : Condensation !!!");
        labelWarnCond.setFont(police);
        labelWarnCond.setVisible(false);
        
        labelTitreCons = new JLabel("Consigne :   ");
        labelTitreCons.setFont(police);
        
        labelTempCons = new JLabel("15 °C   ");
        labelTempCons.setFont(police);
        
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
        
        
        framesPerSecond.setMajorTickSpacing(10);
        framesPerSecond.setMinorTickSpacing(1);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);
        SliderListener sListener = new SliderListener();
        sListener.setIG(this);
        framesPerSecond.addChangeListener(sListener);
        
        
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
        
    
        JPanel panConsigne = new JPanel();
        panConsigne.setPreferredSize(new Dimension(490, 70));
        panConsigne.add(labelTitreCons);
        panConsigne.add(labelTempCons); 
        panConsigne.add(framesPerSecond);
        panConsigne.setBorder(BorderFactory.createLineBorder(Color.black));

        
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
        
        
        //container.add(content, BorderLayout.NORTH);
        container.add(panConsigne, BorderLayout.NORTH);
        container.add(panTempIn, BorderLayout.NORTH);
        container.add(panTempOut, BorderLayout.NORTH);
    }
   
    
    //Implémentation du pattern observer
    @Override
    public void update(String tempIn, String humIn, String tempOut) {
        //controler.checkCondensation(humIn);
        
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

class SliderListener implements ChangeListener{
    InterfaceGraphique obj;
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        obj.getLabelTempCons().setText(source.getValue() + " °C   ");
        //Temperature.writeData(source.getValue() + "");
    }
    public void setIG(InterfaceGraphique ui) {
        if(ui != null && obj == null) obj = ui; 
    }
}