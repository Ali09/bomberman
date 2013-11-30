package Project4;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import Project4.gameLogic.cellType;

public class PaintPane extends JPanel implements ActionListener {
  
    Random rand = new Random();
  
    private Image background;
    Timer mainTimer;
    player p;
    static ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    static ArrayList<Obstruction> obstacles = new ArrayList<Obstruction>();
	static ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
    static ArrayList<Rock> rocks = new ArrayList<Rock>();
    static Rock grid[][] = new Rock[11][13];
    
    int rockNum;

    public PaintPane(Image image) {     
        setFocusable(true);
        background = image;   
        p = new player(60, 50, 0); 
        gameLogic gameLogic = new gameLogic();
        gameLogic.players = new player[4];
        gameLogic.players[0] = p; //FIX
        addKeyListener(new KeyAdapt(p));
        
        mainTimer = new Timer(10, this);
        mainTimer.start();
        
        gameLogic.gameGrid = new cellType[gameLogic.NUM_ROWS][gameLogic.NUM_COLS];        
        for (int i = 0; i < 13; i++){
        	for (int j = 0; j < 15; j++){
        		gameLogic.gameGrid[i][j] = cellType.GRASS;
        	}
        }
        for (int i = 0; i < 13; i++){
        	for (int j = 0; j < 15; j++){
        		if ((i == 0) || (i == 12) || (j == 0) || (j == 14)){
        			gameLogic.gameGrid[i][j] = cellType.STONE;
        		}
        	}
        }
        
        
        rockNum = rand.nextInt(15)+55;
        int rockx = rand.nextInt(11);
        int rocky = rand.nextInt(13); 
        //ROCKX IS ROCKY
        for (int i = 0; i < rockNum; i++){
            while ((((rockx+1)%2 == 0) && ((rocky+1)%2 ==0)) ||
                (rockx == 0 && rocky == 0) ||
                (rockx == 0 && rocky == 1) ||
                (rockx == 1 && rocky == 0) ||
                (rockx == 0 && rocky == 12) ||
                (rockx == 0 && rocky == 11) ||
                (rockx == 1 && rocky == 12) ||
                (rockx == 10 && rocky == 0) ||
                (rockx == 9 && rocky == 0) ||
                (rockx == 10 && rocky == 1) ||
                (rockx == 10 && rocky == 12) ||
                (rockx == 9 && rocky == 12) ||
                (rockx == 10 && rocky == 11) ||
                (grid[rockx][rocky] != null)){
              rockx = rand.nextInt(11);
              rocky = rand.nextInt(13);
            }
            grid[rockx][rocky] = new Rock(rockx, rocky);
            addRock(new Rock(60+(rocky*60), 50+(rockx*50)));
            rockx = rand.nextInt(11);
            rocky = rand.nextInt(13);
            gameLogic.gameGrid[rockx][rocky] = cellType.DESTRUCTABLE_STONE;
        }
        
        for (int i = 1; i <= 5; i++){
          for (int j = 1; j <= 6; j++){
            addObstacle(new Obstruction((j*60*2), (i*50*2)));
            gameLogic.gameGrid[i*2][j*2] = cellType.STONE;
          }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return background == null ? new Dimension(0, 0) : new Dimension(background.getWidth(this), background.getHeight(this));            
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (background != null) {                
            Insets insets = getInsets();

            int width = getWidth() - 1 - (insets.left + insets.right);
            int height = getHeight() - 1 - (insets.top + insets.bottom);

            int x = (width - background.getWidth(this)) / 2;
            int y = (height - background.getHeight(this)) / 2;

            g.drawImage(background, x, y, this);                
        }

    }
    
    public void paint(Graphics g){
      super.paint(g);
      Graphics2D g2d = (Graphics2D) g;
      
      p.draw(g2d);
      
      for (int i = 0; i < rocks.size(); i++){
        Rock temp = rocks.get(i);
        temp.draw(g2d);
      }
      
      for (int i = 0; i < obstacles.size(); i++){
        Obstruction temp = obstacles.get(i);
        temp.draw(g2d);
      }
      
      for (int i = 0; i < upgrades.size(); i++){
        Upgrade temp = upgrades.get(i);
        temp.draw(g2d);
      }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
      p.update();
      repaint();
    }

    public void addRock(Rock r){
      rocks.add(r);
    }
    
    public static void removeRock(int y, int x){
      ArrayList<Rock> rocks = PaintPane.getRockList();
      for (int i = 0; i < rocks.size(); i++){
        Rock temp = rocks.get(i);
        
        if ((temp.x == x*60) && (temp.y == y*50)){
          rocks.remove(temp);
          Upgrade replace = new Upgrade(x*60, y*50);
          addUpgrade(replace);
          break;
        }
      }
    }
    
    public static ArrayList<Rock> getRockList(){
      return rocks;
    }
    
    public void addObstacle(Obstruction o){
      obstacles.add(o);
    }
    
    public static ArrayList<Obstruction> getObstacleList(){
      return obstacles;
    }
    
    public static void addBomb(Bomb b){
      bombs.add(b);
    }
    
    public static void removeBomb(Bomb b){
      bombs.remove(b);
    }
    
    public static ArrayList<Bomb> getBombList(){
      return bombs;
    }    
	
	  public static void addUpgrade(Upgrade u){
      upgrades.add(u);
    }
    
    public static void removeUpgrade(Upgrade u){
      upgrades.remove(u);
    }
    
    public static ArrayList<Upgrade> getUpgradeList(){
      return upgrades;
    }    
}