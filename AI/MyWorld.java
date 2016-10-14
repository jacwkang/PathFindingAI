import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.lang.*;

/**
 * Grid 
 * 
 * @author Jack Wang
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    // Map setup variables
    Node[][] map;
    public int width = 10;
    public int height = 10;
    public int playerX;
    public int playerY;
    public int goalX;
    public int goalY;
    public int[] obstacleX;
    public int[] obstacleY;
    
    // A* algorithm variables
    PriorityQueue<Node> open;
    ArrayList<Node> closed;
    ArrayList<Node> backtrack;
    int[][] path;
    
    /*
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // 10x10 cells with 40px cell size
        super(10, 10, 40); 
        
        // Set up map
        map = new Node[width][height];
        
        // Randomly generate goal location
        goalX = Greenfoot.getRandomNumber(2) + 7;
        goalY = Greenfoot.getRandomNumber(9);
        // Rnadomly generate player location
        playerX = Greenfoot.getRandomNumber(1);
        playerY = Greenfoot.getRandomNumber(9);
        
        Ash goal = new Ash();
        goal.getImage().scale(40, 40);
        addObject(goal, goalX, goalY);
        
        // Place obstacles to obstruct
        obstacleX = new int[10];
        obstacleY = new int[10];
        int obstacle1X = goalX - 1;
        int obstacle1Y = goalY;
        obstacleX[0] = obstacle1X;
        obstacleY[0] = obstacle1Y;
        Obstacle obstacle1 = new Obstacle();
        obstacle1.getImage().scale(40, 40);
        addObject(obstacle1, obstacle1X, obstacle1Y);
        
        for (int i = 1; i < 10; i++) {
            Obstacle obstacle = new Obstacle();
            obstacle.getImage().scale(40, 40);
            int x = Greenfoot.getRandomNumber(6) + 2;
            int y = Greenfoot.getRandomNumber(8) + 1;
            // Ensure that it doesn't occupy the samesquare
            if (x == goalX)
                x -= 1;
            if (y == goalY)
                y -= 1;
            obstacleX[i] = x;
            obstacleY[i] = y;
            addObject(obstacle, x, y);
        }
                
        // Set up map 
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = new Node(i, j, 0);
            }
        }
        
        // Randomly place player
        Player player = new Player(playerX, playerY, goalX, goalY,
            obstacleX, obstacleY, map);
        player.getImage().scale(40, 40);
        
        startTraversal();
        path = new int[10][10];
        findPath();
        addObject(player, playerX, playerY);
    }
    
    // Begins the search
    public void startTraversal() {
        open = new PriorityQueue<Node>();
        open.add(map[playerX][playerY]);
        closed = new ArrayList<Node>();
        
        int x = open.peek().x;
        int y = open.peek().y;
        while (open.size() > 0 && !((x = open.peek().x) == goalX && (y = open.peek().y) == goalY)) {
            Node current = open.poll();
            closed.add(current);
            searchNeighbors(current.x, current.y, current);
            
            // Testing purposes
            for (Node[] row : map) {
                System.out.println(Arrays.toString(row));
            }
            System.out.println("");
        }
        System.out.println("GoalX : " + goalX);
        System.out.println("GoalY : " + goalY);
        System.out.println("X : " + x);
        System.out.println("Y : " + y);
    }
    
    // Searches the neighbors given a parent
    public void searchNeighbors(int nodeX, int nodeY, Node current) {
        // Searches the neighbors
        for (int i = 0; i < 4; i++) {
            int neighborX = nodeX;
            int neighborY = nodeY;
            switch (i) {
                case 0: // LEFT
                    if (nodeX > 0)
                        neighborX = nodeX - 1;
                    else
                        continue;
                break;
                case 1: // TOP
                    if (nodeY > 0)
                        neighborY = nodeY - 1;
                    else
                        continue;
                break;
                case 2: // RIGHT
                    if (nodeX < 9)
                        neighborX = nodeX + 1;
                    else
                        continue;
                break;
                case 3: // BOTTOM
                    if (nodeY < 9)
                        neighborY = nodeY + 1;
                    else
                        continue;
                break;
                default:
                break;
            }
            if (isObstacle(neighborX, neighborY))
                continue;
            int cost = current.cost + 1;
            Node neighbor = map[neighborX][neighborY];
            
            // Cost is greater, ignore
            if (open.contains(neighbor) && cost < neighbor.cost) {
                open.remove(neighbor);
            }
            if (closed.contains(neighbor) && cost < neighbor.cost) {
                closed.remove(neighbor);
            }
            if (!closed.contains(neighbor) && !open.contains(neighbor)) {
                neighbor.cost = cost;
                neighbor.cameFrom = current;
                open.add(neighbor);
            }
        }
    }
    
    public boolean isObstacle(int x, int y) {
        for (int i = 0; i < obstacleX.length; i++) {
            if (obstacleX[i] == x && obstacleY[i] == y)
                return true;
        }
        return false;
    }
    
    public void findPath() {
        Node goal = map[goalX][goalY];
        Node start = map[playerX][playerY];
        backtrack = new ArrayList<Node>();
        while (!goal.equals(start)) {
            goal = goal.cameFrom;
            path[goal.x][goal.y] = 1;
            Goal goals = new Goal();
            addObject(goals, goal.x, goal.y);
            backtrack.add(goal);
        }
        Collections.reverse(backtrack);
       // Testing purposes
       for (int[] row : path) {
           System.out.println(Arrays.toString(row));
       }
       System.out.println("");
    }
}
