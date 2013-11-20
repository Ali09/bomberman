package Project4;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


public class Bomb extends Entity {
  public Bomb(int x, int y) {
    super(x, y);
  }

  public void update(){
  }
  
  public void draw(Graphics2D g2d){
    g2d.drawImage(getBombImg(), x, y, null);
  }
  
  public Image getBombImg(){
    ImageIcon ic = new ImageIcon("C:/Users/Samuel/Desktop/adt-bundle-windows-x86_64-20130219/eecs285/Project4/bomb.gif");
    return ic.getImage();
  }
  
  public void keyPressed(KeyEvent e){
  }
  
  public void keyReleased(KeyEvent e){
  }
    
  public Rectangle getBounds(){
    return new Rectangle(x, y, getBombImg().getWidth(null), getBombImg().getHeight(null)-10);
  
  }
}
