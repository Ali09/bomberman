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
    int temp = PaintPane.rand.nextInt(3);
    switch (temp) {
    case 0:
      type = "RADIUS";
      break;
    case 1:
      type = "SPEED";
      break;
    case 2:
      type = "AMOUNT";
      break;
    default:
      break;
    }
  }

  public void update() {
  }

  public void draw(Graphics2D g2d) {
    g2d.drawImage(getUpgradeImg(), x, y, null);
  }

  public Image getUpgradeImg() {
    ImageIcon ic = null;
    switch (type) {
    case "RADIUS":
      ic = new ImageIcon("src/Project4/images/powerup_radius.gif");
      break;
    case "SPEED":
      ic = new ImageIcon("src/Project4/images/powerup_speed.gif");
      break;
    case "AMOUNT":
      ic = new ImageIcon("src/Project4/images/powerup_bomb.gif");
      break;
    default:
      break;
    }
    return ic.getImage();
  }

  public void keyPressed(KeyEvent e) {
  }

  public void keyReleased(KeyEvent e) {
  }

  public Rectangle getBounds() {
    return new Rectangle(x, y, getUpgradeImg().getWidth(null), getUpgradeImg()
        .getHeight(null) - 10);
  }
}
