import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{
    boolean traversing = false;
    boolean traversed = false;
    int traversedSteps;
    int startX; //= ((MyWorld)getWorld()).playerX;
    int startY; //= ((MyWorld)getWorld()).playerY;
    int finishX; //= ((MyWorld)getWorld()).goalX;
    int finishY; //= ((MyWorld)getWorld()).goalY;
    int[] obstacleX; //= ((MyWorld)getWorld()).obstacle1X;
    int[] obstacleY; //= ((MyWorld)getWorld()).obstacle1Y;
    Node[][] map; //= ((MyWorld)getWorld()).map;
    int width = 10; //= 10;
    int height = 10; // = 10;
    public int timer = 7;
    public int i = 0;
    
    public Player(int playerX, int playerY, int finishX, int finishY,
        int[] obstacleX, int[] obstacleY, Node[][] map) {
        this.startX = playerX;
        this.startY = playerY;
        this.finishX = finishX;
        this.finishY = finishY;
        this.obstacleX = obstacleX;
        this.obstacleY = obstacleY;
        this.map = map;
        this.traversedSteps = traversedSteps;
    }
    
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        MyWorld world = (MyWorld)getWorld();
        ArrayList<Node> path = world.backtrack;
        if (timer < 0 && i < path.size()) {
            setImage("pikachu.gif-c200");
            getImage().scale(40, 40);
            getImage().mirrorHorizontally();
            timer = 7;
            i++;
            int x = path.get(i).x;
            int y = path.get(i).y;
            setLocation(x, y);
        }
        else {
            timer--;
        }
        
        if (i == path.size()) {
            setLocation(world.goalX, world.goalY);
        }
    }    
    
}
