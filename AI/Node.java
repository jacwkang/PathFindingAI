import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.lang.*;

/**
 * Write a description of class Node here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Node extends Actor implements Comparable<Node>
{
    public int x;
    public int y;
    public int cost;
    public Node cameFrom;
    
    public Node (int x, int y, int cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }
    /**
     * Act - do whatever the Node wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
    
    public String toString() {
        return "" + cost;
    }
    
    public int compareTo(Node x) {
        if (this.cost < x.cost) {
            return -1;
        }
        if (this.cost > x.cost) {
            return 1;
        }
        return 0;
    }
}
