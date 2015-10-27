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
    private JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, 0, 30, 15);
    
    
        /** The number of subplots. */
    public static final int SUBPLOT_COUNT_IN = 2;
    
    /** The datasets. */
    private TimeSeriesCollection[] datasets;
    
    /** The most recent value added to series 1. */
    private double[] lastValue = new double[SUBPLOT_COUNT_IN];

    
    
    
    //L'instance de notre objet contrôleur
    private AbstractControler controler;

    public JLabel getLabelTempCons() {
        return labelTempCons;
    }
    
    public InterfaceGraphique(AbstractControler controler){                
        this.setSize(500, 600);
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
        
        
        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new DateAxis("Temps"));
        this.datasets = new TimeSeriesCollection[SUBPLOT_COUNT_IN];
        
        String[] nameSeries = {"Température","Humidité"};
        String[] nameAxis = {"Température (°C)","pourcentage (%)"};
        
        for (int i = 0; i < SUBPLOT_COUNT_IN; i++) {
            this.lastValue[i] = 100.0;
            final TimeSeries series = new TimeSeries(nameSeries[i], Millisecond.class);
            this.datasets[i] = new TimeSeriesCollection(series);
            final NumberAxis rangeAxis = new NumberAxis(nameAxis[i]);
            rangeAxis.setAutoRangeIncludesZero(false);
            final XYPlot subplot = new XYPlot(
                    this.datasets[i], null, rangeAxis, new StandardXYItemRenderer()
            );
            subplot.setBackgroundPaint(Color.lightGray);
            subplot.setDomainGridlinePaint(Color.white);
            subplot.setRangeGridlinePaint(Color.white);
            plot.add(subplot);
        }

        final JFreeChart chart = new JFreeChart("", plot);
//        chart.getLegend().setAnchor(Legend.EAST);
        chart.setBorderPaint(Color.black);
        chart.setBorderVisible(true);
        chart.setBackgroundPaint(Color.white);
        
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
  //      plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 4, 4, 4, 4));
        final ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(240000.0);  // 60 seconds
        
        //final JPanel content = new JPanel(new BorderLayout());

        final ChartPanel chartPanel = new ChartPanel(chart);
        //content.add(chartPanel);

        //final JPanel buttonPanel = new JPanel(new FlowLayout());
        
        /*for (int i = 0; i < SUBPLOT_COUNT; i++) {
            final JButton button = new JButton("Series " + i);
            button.setActionCommand("ADD_DATA_" + i);
            button.addActionListener(this);
            buttonPanel.add(button);
        }
        final JButton buttonAll = new JButton("ALL");
        buttonAll.setActionCommand("ADD_ALL");
        buttonAll.addActionListener(this);
        buttonPanel.add(buttonAll);
        
        content.add(buttonPanel, BorderLayout.SOUTH);*/
        chartPanel.setPreferredSize(new java.awt.Dimension(480, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        //setContentPane(content);
        
        
        /*
        this.series = new TimeSeries("Température", Millisecond.class);
        final TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
        final JFreeChart chart = createChart(dataset);

        final ChartPanel chartPanel = new ChartPanel(chart);
        //final JButton button = new JButton("Add New Data Item");
        //button.setActionCommand("ADD_DATA");
        //button.addActionListener(this);

        JPanel content = new JPanel();
        content.add(chartPanel);
        //content.add(button, BorderLayout.SOUTH);
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 200));
        content.setBorder(BorderFactory.createLineBorder(Color.black));
        //setContentPane(content);
        
        
        
        */
    
        JPanel panConsigne = new JPanel();
        panConsigne.setPreferredSize(new Dimension(490, 70));
        panConsigne.add(labelTitreCons);
        panConsigne.add(labelTempCons); 
        panConsigne.add(framesPerSecond);
        panConsigne.setBorder(BorderFactory.createLineBorder(Color.black));

        
        //panel température intérieure
        JPanel panTempIn = new JPanel();
        panTempIn.setPreferredSize(new Dimension(490, 350));
        panTempIn.add(labelTitreTempIn);
        panTempIn.add(labelTempIn); 
        panTempIn.add(labelHumpIn);
        panTempIn.add(chartPanel);
        panTempIn.setBorder(BorderFactory.createLineBorder(Color.black));
        
        
        //panel température extérieure
        JPanel panTempOut = new JPanel();
        panTempOut.setPreferredSize(new Dimension(490, 100));
        panTempOut.add(labelTitreTempOut);
        panTempOut.add(labelTempOut);
        panTempOut.setBorder(BorderFactory.createLineBorder(Color.black));
        
        
        //container.add(content, BorderLayout.NORTH);
        container.add(panConsigne, BorderLayout.NORTH);
        container.add(panTempIn, BorderLayout.NORTH);
        container.add(panTempOut, BorderLayout.NORTH);
    }
   
    
    //Implémentation du pattern observer
    @Override
    public void update(String tempIn, String humIn, String tempOut) {
        this.lastValue[0] = Double.valueOf(tempIn);
        this.lastValue[1] = Double.valueOf(humIn);
        
        labelTempIn.setText(tempIn + " °C   ");
        labelHumpIn.setText(humIn + " %   ");
        labelTempOut.setText(tempOut + " °C   ");
        
        for (int i = 0; i < SUBPLOT_COUNT_IN; i++) {
            final Millisecond now = new Millisecond();
            this.datasets[i].getSeries(0).add(new Millisecond(), this.lastValue[i]);  
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