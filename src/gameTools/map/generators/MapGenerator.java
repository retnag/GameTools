/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameTools.map.generators;

import java.util.ArrayList;
import gameTools.map.Tile;
import sun.org.mozilla.javascript.ast.CatchClause;

/**
 *
 * @author ganter
 * @param <T>
 */
public abstract class MapGenerator<T extends Tile> {
    T t;
    int[] p;

    public MapGenerator(T t, int... p) {
        this.t = t;
        this.p=p;
    }
    
    public abstract ArrayList<T> generate();
    
}
