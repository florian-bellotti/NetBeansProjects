/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.HashMap;

/**
 *
 * @author Florian
 */
public class DataTemperature2 {
    
    HashMap<Long, Integer> histTempIn = new HashMap<>();
    HashMap<Long, Integer> histTempOut = new HashMap<>();
    HashMap<Long, Integer> lastTempIn = new HashMap<>();
    HashMap<Long, Integer> lastTempOut = new HashMap<>();

    public HashMap<Long, Integer> getHistTempIn() {
        return histTempIn;
    }

    public void setHistTempIn(long date, int tempIn) {
        histTempIn.put(date, tempIn);
    }

    public HashMap<Long, Integer> getHistTempOut() {
        return histTempOut;
    }

    public void setHistTempOut(long date, int tempOut) {
        histTempOut.put(date, tempOut);
    }

    public HashMap<Long, Integer> getLastTempIn() {
        return lastTempIn;
    }

    public void setLastTempIn(long date, int tempIn) {
        lastTempIn.put(date, tempIn);
    }
    
    public void clearLastTempIn() {
        lastTempIn.clear();
    }

    public HashMap<Long, Integer> getLastTempOut() {
        return lastTempOut;
    }

    public void setLastTempOut(long date, int tempOut) {
        lastTempOut.put(date, tempOut);
    }
    
    public void clearLastTempOut() {
        lastTempOut.clear();
    }

    

}
