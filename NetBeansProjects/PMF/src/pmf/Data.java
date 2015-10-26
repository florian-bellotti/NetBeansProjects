/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmf;

/**
 *
 * @author Florian
 */
public class Data<T> {
    private T value;
    
    public Data(T type) {
        value = type;
    } 
    
    public T getValue() {
        return value;
    }
    
    public void update(T type) {
        value = (type != value ? type : value);
    }
}
