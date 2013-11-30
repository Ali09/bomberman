package Project4;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;

public class Upgrade extends Entity {
  public String type;

  public Upgrade(int x, int y) {
	    super(x, y);
            Random rand = new Random();
	    int temp = rand.nextInt(3);
            switch(temp){
            case 1: type = "RADIUS";
            break;
            case 2: type = "SPEED";
            break;
            case 3: type = "AMOUNT";
            break;
            case 4: type = "?";
            break;
            default:
            break;
            }
	  }

	  public void update(){
	  }
	  
	  public void draw(Graphics2D g2d){
	    g2d.drawImage(getUpgradeImg(), x, y, null);
	  }
	  
	  public Image getUpgradeImg(){
	    ImageIcon ic = new ImageIcon("C:/Users/Samuel/Desktop/adt-bundle-windows-x86_64-20130219/eecs285/Project4/bomb2.gif");
	    return ic.getImage();
	  }
	  
	  public void keyPressed(KeyEvent e){
	  }
	  
	  public void keyReleased(KeyEvent e){
	  }
	    
	  public Rectangle getBounds(){
	    return new Rectangle(x, y, getUpgradeImg().getWidth(null), getUpgradeImg().getHeight(null)-10);
	  }
}
