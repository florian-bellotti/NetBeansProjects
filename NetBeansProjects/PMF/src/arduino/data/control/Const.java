/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino.data.control;


import java.util.HashMap;

/**
 *
 * @author Axel
 * All constants, such as Update Frenquency, Debug mode, isInited, are written here.
 * One function, initFlags, links all data in _ELEMS and _REF
 */
public class Const {
    
    public final static Boolean __DEBUG = Boolean.TRUE;
    
    // Avoid to rewritte over _FLAGS_ 
    private static Boolean init = Boolean.FALSE;
    
    // List of all avaiable ports, used if Regedit Command is unavailable (rights or not existing)
    public static final String PORT_NAMES[] = {
       "/dev/tty.usbserial-A9007UX1","/dev/ttyUSB0","COM3", "COM4", "COM5","COM6","COM7", "COM8", "COM9","COM10"
    };
    
    // All existing flags, used to make a research faster.
    public static final HashMap<String,String> _FLAGS_ = new HashMap<>( );
    
   
    public static final Integer MAX_THREAD_COUNT    = 3;
    
    public static final Integer UPD_FREQUENCY       = 1000; // Thread Update frequency (ms)
    public static final Integer UPD_TEST_FRENQUENCY = 2000; // Thread Update frequency in Debug (ms)
    public static final Integer UPD_GRAPH_SLEEP     = UPD_TEST_FRENQUENCY ; // Graph Update frenquency (ms)
    public static final Integer UPD_GRAPH_PAUSE     = UPD_FREQUENCY; // Graph Update frequency in debug (ms)
    /** Milliseconds to block while waiting for port open */
    public static final int TIME_OUT                = 2000;
    
    /** Default bits per second for COM port. */
    public static final int DATA_RATE               = 9600;
    
        
    /** The number of subplots. */
    public static final int SUBPLOT_COUNT = 3;
    
    // Input tags, any incoming data will be written like this: [TAG][NUMBER]
    private static final String[] _ELEMS = {/*"ROut"*//*,"VOID00",*/"RIn"};
    
    // 
    private static final String[] _REF   = {/*"ResOut",*//*"VOID00",*/"ResIn"};
    public static final int    _SOUND_SEP  = 20; // seconds
    public static final String _SOUND_PATH = System.getProperty("user.dir") + "/sounds/mario.wav"; 
    //public static final String _SOUND_PATH = System.getProperty("user.dir") + "\\sounds\\alarm.wav"; // Windows Specific
    public static final int    _CRITICAL_HUMIDITY = 90;
    // public static final Short _INDEX_TMP_IN = 0, _INDEX_TMP_OUT = 1, _INDEX_HMD = 2;
    
    /**
     * Put in a HashMap as <Key,Value> data <_REF,_ELEMS>
     */
    public static void initFlags(){
        
        if(!init){
            for(int i = 0; i < _ELEMS.length; ++i) 
                _FLAGS_.put(_REF[i],_ELEMS[i]);
            
            init = Boolean.TRUE;
        }
    }
}