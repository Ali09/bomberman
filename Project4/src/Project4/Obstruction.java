package Project4;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Obstruction extends Entity {
  
  public Obstruction(int x, int y) {
    super(x, y);
  }

  public void update(){
  }
  
  public void draw(Graphics2D g2d){
    g2d.drawImage(getObstacleImg(), x, y, null);
  }
  
  public Image getObstacleImg(){
    ImageIcon ic = new ImageIcon("src/Project4/images/empty.gif");
    return ic.getImage();
  }
  
  public void keyPressed(KeyEvent e){
  }
  
  public void keyReleased(KeyEvent e){
  }
    
  public Rectangle getBounds(){
    return new Rectangle(x, y, getObstacleImg().getWidth(null), getObstacleImg().getHeight(null));
  
  }
}
