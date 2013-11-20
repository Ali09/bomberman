package Project4;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

// player class describing
// bomberman charecter
public class player extends Entity
{ 
  public int playerNum;
  public Location loc;
  public gameLogic.direction playerDirection;
  public boolean alive;
  
  // number of bombs player is able
  // to drop at a given instant, initially 1
  public int numBombs;
  
  // radius of bomb explosion for player, initially 1
  public int bombRadius;
  
  //checks for movement spam
  public long spam = System.currentTimeMillis();
  
  public int score;

  int velX = 0, velY = 0;
  int speed = 2;
  
  int oldx, oldy;
  
  public player(int x, int y) {
    super(x, y);
    update();
  }

  public void update(){
    boolean flagx = true;
    boolean flagy = true;
    
    if (collides()){
      x = oldx;
      y = oldy;
      flagx = false;
      flagy = false;
    }
    if ((y + velY < 50) || (y + velY > 550)){
      flagy = false;
    }
    if ((x + velX < 60) || (x + velX > 780)){
      flagx = false;
    }
    if (flagy){
      oldy = y;
      y += velY;
    }
    if (flagx){
      oldx = x;
      x += velX;
    }
  }
  
  public void draw(Graphics2D g2d){
    g2d.drawImage(getPlayerImg(), x, y, null);
  }
  
  public Image getPlayerImg(){
    ImageIcon ic = new ImageIcon("C:/Users/Samuel/Desktop/adt-bundle-windows-x86_64-20130219/eecs285/Project4/sprite2.gif");
    return ic.getImage();
  }
  public void keyPressed(KeyEvent e){
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_UP){
      velY = -speed;
    } else if (key == KeyEvent.VK_DOWN){
      velY = speed;
    } else if (key == KeyEvent.VK_LEFT){
      velX = -speed;
    } else if (key == KeyEvent.VK_RIGHT){
      velX = speed;
    } 
  }
  
  public void keyReleased(KeyEvent e){
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_UP){
      velY = 0;
    } else if (key == KeyEvent.VK_DOWN){
      velY = 0;
    } else if (key == KeyEvent.VK_LEFT){
      velX = 0;
    } else if (key == KeyEvent.VK_RIGHT){
      velX = 0;
    } 
  }
  
  public boolean collides(){
    ArrayList<Rock> rocks = PaintPane.getRockList();
    
    for (int i = 0; i < rocks.size(); i++){
      Rock temp = rocks.get(i);
      
      if (getBounds().intersects(temp.getBounds())){
        return true;
      }
    }
    ArrayList<Obstruction> obstacles = PaintPane.getObstacleList();
    
    for (int i = 0; i < obstacles.size(); i++){
      Obstruction temp = obstacles.get(i);
      
      if (getBounds().intersects(temp.getBounds())){
        return true;
      }
    }   
    
    return false;
  }
  
  public Rectangle getBounds(){
    return new Rectangle(x, y, getPlayerImg().getWidth(null), getPlayerImg().getHeight(null)-5);
  }
  
}
