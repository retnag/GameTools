/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameTools.state;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * <p>This Abstract Class represents a graphical state in a game. for example: menu state, game state, settings state...</p>
 * <p></p>
 * @param name The states name
 * @param inputManager Each state comes with its own InputManager. This is it.
 * @param soundManager Each state comes with its own SoundManager. This is it. 
 * All sounds stop playing when states change.
 * @author ganter (idea from The Java Hub)
 * @see InputManager
 * @see SoundManager
 * @see StateManager
 */
public abstract class State extends JPanel{
    private final State THIS = this;
    
    private volatile boolean running = false;
    private long ticks = 0;
    
    private BufferedImage screen;
    protected Graphics2D g;
    
    private Thread renderThread, updateThread;
    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            while(running){
                update(THIS);
                ticks++;
            }
        }
    };    
    private final Runnable renderRunnable = new Runnable() {
        @Override
        public void run() {
            while(running){
//                createNewGraphics();
                fps++;   
                render();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
        }
    };
    
    public InputManager inputManager = new InputManager(this);
    public SoundManager soundManager = new SoundManager();
    
    public String name;
    private int width, height;
    
    public int fps;
    private long time1, time2;
    private boolean rendering;
    
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
    
    public State(String s){
        this(s, 500, 500);
    }
    
    public State(String s, int width, int height){
        name = s;
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(width, height));
        updateThread = new Thread(updateRunnable);
        renderThread = new Thread(renderRunnable);
        setDoubleBuffered(true);
        createNewGraphics();
        time1=System.currentTimeMillis();
    }
    
    private void createNewGraphics(){
        screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = screen.createGraphics();
    }
    
    public void start(){
        if(!running){
            running = true;
            updateThread.start();
            renderThread.start();
        }
    }
    
    public void stop(){
        if(running){
            running = false;
        }
        soundManager.stopAllSounds();
    }
    
    public Graphics2D getGraphics2D(){
        return g;
    }

    public boolean isRunning() {
        return running;
    }

    public long getTicks() {
        return ticks;
    }
    
    /**
    * this method is called in every cycle in the States Thread. 
    * When extendig State, you propably want to overwrite this, and put your code here.
     * @param s
    * @param State is the state calling the update
    */
    public abstract void update(State s);
    
    /**
     * this method is called in every cycle in the States Thread. 
     * When extendig State, you propably want to overwrite this, and put your rendering code here.
     * <br>
     * Dont forget to call super.render() in the end in order to actually do the rendering.
     */
    public void render(){
        repaint();
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(screen, 0, 0, width, height, this);
//        System.out.printf("ordered:\t%d%n",System.currentTimeMillis());
//        System.out.printf("step:   \t%d%n",System.currentTimeMillis());
//        g.dispose();
//        g.drawImage(screen, 0, 0, width, height, this);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                System.out.printf("done:    \t%d%n%n",System.currentTimeMillis());
                renderThread.interrupt();
            }
        });
    }
      
}
