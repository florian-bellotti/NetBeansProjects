/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmf;

import java.util.ArrayList;

/**
 *
 * @author Florian
 */
public class DataTemperature {
   
    ArrayList<Integer> histTempIn = new ArrayList<>();
    ArrayList<Integer> histTempOut = new ArrayList<>();
    //ArrayList<Integer> lastTempIn = new ArrayList<>();
    int lastTempIn = 0;
    ArrayList<Integer> lastTempOut = new ArrayList<>();

    public ArrayList<Integer> getHistTempIn() {
        return histTempIn;
    }

    public void setHistTempIn(int tempIn) {
        histTempIn.add(tempIn);
    }

    public ArrayList<Integer> getHistTempOut() {
        return histTempOut;
    }

    public void setHistTempOut(int tempOut) {
        histTempOut.add(tempOut);
    }

    public void setLastTempIn(int tempIn) {
        this.lastTempIn = tempIn;
    }
    
    public int getLastTempIn() {
        return lastTempIn;
    }
    
    /*public ArrayList<Integer> getLastTempIn() {
        return lastTempIn;
    }

    public void setLastTempIn(int tempIn) {
        lastTempIn.add(tempIn);
    }
    
    public void clearLastTempIn() {
        lastTempIn.clear();
    }*/

    public ArrayList<Integer> getLastTempOut() {
        return lastTempOut;
    }

    public void setLastTempOut(int tempOut) {
        lastTempOut.add(tempOut);
    }
    
    public void clearLastTempOut() {
        lastTempOut.clear();
    }
}
