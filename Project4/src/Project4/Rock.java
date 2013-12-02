package Project4;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Rock extends Entity {

  public Rock(int x, int y) {
    super(x, y);
  }

  public void update() {
  }

  public void draw(Graphics2D g2d) {
    g2d.drawImage(getRockImg(), x, y, null);
  }

  public Image getRockImg() {
    ImageIcon ic = new ImageIcon("src/Project4/images/rocks.gif");
    return ic.getImage();
  }

  public void keyPressed(KeyEvent e) {
  }

  public void keyReleased(KeyEvent e) {
  }

  public Rectangle getBounds() {
    return new Rectangle(x, y, getRockImg().getWidth(null), getRockImg()
        .getHeight(null));

  }

}
