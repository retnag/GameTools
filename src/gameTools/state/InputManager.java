package gameTools.state;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * This Class is useful for handling input in games. After instantiating the
 * user can map keys to it using the addMapping method. Every mapping has a 
 * name(String) and a value(KeyCode). The manager constantly listenes to the 
 * keyboard input via KeyListener. The user can check if a key has been pressed
 * using the isPressed method, passing it the name of the mapping.
 * @author ganter (idea from The Java Hub)
 * @see addMapping
 * @see isPressed
 * @see State
 */
public class InputManager implements KeyListener {
    
    public ArrayList<Key> keys = new ArrayList<>();
    
    public InputManager(JPanel c){
        c.addKeyListener(this);
    }
    
    /**
     * Registeres a keyMapping to listen to
     * @param s the name of the mapping
     * @param keyCode the keyCode to listen to
     */
    public void addMapping(String s, int keyCode){
        keys.add(new Key(s, keyCode));
    }
    
    /**
     * @param s the keyMappings name
     * @return true if the key is pressed and false if it is not. If the mapping is not registered, it will default to false.
     */
    public boolean isPressed(String s){
        for(Key k : keys){
            if (s.equals(k.name)){
                return k.pressed;
            }
        }        
        return false;
    }
    public boolean isTyped(String s){
        for(Key k : keys){
            if (s.equals(k.name)){
                boolean ret = k.typed;
                k.typed = false;
                return ret;
            }
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(Key k : keys){
            if (e.getKeyCode() == k.keyCode){
                k.togglePressed(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for(Key k : keys){
            if (e.getKeyCode() == k.keyCode){
                k.togglePressed(false);
                k.typed = true;
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    private class Key {
        public String name;
        public int keyCode, pressCount;
        public boolean pressed = false;
        private boolean typed = false;

        public Key(String name, int keyCode){
            this.name = name;
            this.keyCode = keyCode;
        }

        public void togglePressed(boolean t){
            if(pressed != t){
                pressed = t;
            }
            if(pressed){
                pressCount++;
            }
        }
    }
}
