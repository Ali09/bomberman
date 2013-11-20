package Project4;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
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
    if ((y + velY < 50) || (y + velY > 560)){
      flagy = false;
    }
    if ((x + velX < 60) || (x + velX > 810)){
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
    if (key == KeyEvent.VK_SPACE){
      final int they = y/50;
      final int thex = x/60;
      final Bomb temp = new Bomb((60+((thex-1)*60)), (50+((they-1)*50)));
      try {
        GUI.grid[y/50][x/60].setIcon(new ImageIcon(ImageIO.read(new File("C:/Users/Samuel/Desktop/adt-bundle-windows-x86_64-20130219/eecs285/Project4/bomb.gif"))));
        Timer timer1 = new Timer();
        timer1.schedule(new TimerTask(){
          public void run(){
            PaintPane.addBomb(temp);
            try {
              GUI.grid[they][thex].setIcon(new ImageIcon(ImageIO.read(new File("C:/Users/Samuel/Desktop/adt-bundle-windows-x86_64-20130219/eecs285/Project4/bomb2.gif"))));
            } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        }, 1000);
        
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask(){
          public void run(){
            try {
              GUI.grid[they][thex].setIcon(new ImageIcon(ImageIO.read(new File("C:/Users/Samuel/Desktop/adt-bundle-windows-x86_64-20130219/eecs285/Project4/bomb3.gif"))));
            } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        }, 2000);    
        
        Timer timer3 = new Timer();
        timer3.schedule(new TimerTask(){
          public void run(){
            try {
              GUI.grid[they][thex].setIcon(new ImageIcon(ImageIO.read(new File("C:/Users/Samuel/Desktop/adt-bundle-windows-x86_64-20130219/eecs285/Project4/empty.gif"))));
            } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            PaintPane.removeBomb(temp);
          }
        }, 3000);  
        
        
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
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
    ArrayList<Bomb> bombs = PaintPane.getBombList();

    for (int i = 0; i < bombs.size(); i++){
      Bomb temp = bombs.get(i);
      System.out.println(temp.x + " " + temp.y);
      
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
