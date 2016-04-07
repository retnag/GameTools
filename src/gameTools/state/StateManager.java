package gameTools.state;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * This class is managing a games states. use the start- stop- and setCurrentState
 * methods to manage your states.
 * @author ganter (idea from The Java Hub)
 * @see State
 */
public class StateManager {
    
    private ArrayList<State> states;
    
    private int currentState;
    
    private JFrame gameFrame;
    
    public StateManager(JFrame j){
        gameFrame = j;
        states = new ArrayList<>();
    }
    
    public void addState(State s){
        states.add(s);
    }
    
    public void startCurrentState(){
        try{
            gameFrame.add(states.get(currentState));
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            states.get(currentState).start();
            states.get(currentState).requestFocus();
        } catch(IndexOutOfBoundsException e){
            System.err.println("[ERROR] IndexOutOfBoundsException - propably because there are no states registered!");
            System.err.println(e.getMessage());
        }
    }
    
    public void stopCurrentState(){
        gameFrame.remove(states.get(currentState));
        states.get(currentState).stop();
    }
    
    public void setCurrentState(String s){
        for(int i = 0; i < states.size(); i++){
            if(s.equals(states.get(i).name)){
                currentState = i;
            }
        }
    }
    
}
