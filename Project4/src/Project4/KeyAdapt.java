package Project4;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyAdapt extends KeyAdapter {

  player p;

  public KeyAdapt(player p) {
    this.p = p;
  }

  public void keyPressed(KeyEvent e) {
    p.keyPressed(e);
  }

  public void keyReleased(KeyEvent e) {
    p.keyReleased(e);
  }

}
