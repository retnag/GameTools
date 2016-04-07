/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameTools;

import java.awt.Rectangle;

/**
 * When implementing this interface the user must include a getBounds, getPointBounds, Interscts method in the
 * object, intented for geting the objects bounds for collisiondetecting. The method must return a java.awt.Rectangle
 * @author ganter
 */
public interface Physical {    
    
    public PointHD[] getPointBounds();
    public Rectangle getBounds();
    
    public boolean intersects(Physical obj);
    
}