package gameTools.map;


import gameTools.map.generators.MapGenerator;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *  grid of cells
 * @author ganter
 * @param <T>
 */
public class Map<T extends Tile>
        extends HashMap<ArrayList<Integer>, T>
        implements gameTools.Graphical{
    
    public Layout layout;
    protected T tile;
    
    public Map(MapGenerator<T> g, Layout l){
        super();
        ArrayList<T> tiles = g.generate();
        int i;
        for(i = 0; i<tiles.size(); i++){
            addTile(tiles.get(i));
        }
        tile = tiles.get(i-1);
        layout = l;
    }
    
    public final int addTile(T c){
        ArrayList<Integer> a = new ArrayList<>();
        a.add(c.x);
        a.add(c.y);
        this.put(a, c);
        return 0;
    }
    
    public final T getTile(int... i){
        if (i.length != 2) return null; //throw new NullPointerException("The map does not contain the requested Tile");
        ArrayList<Integer> a = new ArrayList<>();
        a.add(i[0]);
        a.add(i[1]);
        return this.get(a);
    }
    
    public final ArrayList<T> getNeighborTiles(int... i){
        ArrayList<int[]> coordinates;
        try{
            coordinates = getTile(i).getNeighbors();
        }catch(NullPointerException e){
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
        
        ArrayList<T> neighbors = new ArrayList();
        for(int[] coordinate : coordinates){
            T t = getTile(coordinate);
            if(t != null) neighbors.add(t);
        }
        return neighbors;
    }
    
    public final ArrayList<T> getSpecNeighborTiles(Tester<T> cc, int... i){
        ArrayList<T> validNeighbors = getNeighborTiles(i);
        for (Iterator<T> iterator = validNeighbors.iterator(); iterator.hasNext();) {
            T t = iterator.next();
            if(!cc.test(t)) iterator.remove();
        }
        return validNeighbors;
    }
    
    public boolean tileExists(int... i){
        return (getTile(i) != null);
        
    }
    
    public ArrayList<T> getTileArray(){
        return new ArrayList<>(this.values());
    }
    
    public ArrayList<T> getSpecTiles(Tester<T> cc){
        ArrayList<T> tiles = getTileArray();
        for (Iterator<T> iterator = tiles.iterator(); iterator.hasNext();) {
            T t = iterator.next();
            if(!cc.test(t)) iterator.remove();
        }
        return tiles;
    }
    
    public T fromPixel(int x, int y){
        return getTile(tile.fromPixel(x, y, layout));
    }
    
    @Override
    public void render(Graphics2D g) {
        for(T i : values()){
            i.render(g, layout);
        }
    }
}
