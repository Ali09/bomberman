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
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Project4.gameLogic.cellType;
import Project4.gameLogic.explodeType;

// player class describing
// bomberman charecter
public class player extends Entity {
  private static final int NUM_COLS = 0;
  private static final int NUM_ROWS = 0;
  public int playerNum;
  public Location loc;
  public gameLogic.direction playerDirection;
  
  // number of bombs player is able
  // to drop at a given instant, initially 1
  public int numBombs = 1;

  // radius of bomb explosion for player, initially 1
  public int bombRadius = 1;

  public int scores[] = Project4.scores;

  int velX = 0, velY = 0;
  int speed = 1;

  int oldx, oldy;

  public player(int x, int y, int playerNum) {
    super(x, y);
    this.playerNum = playerNum;
    update();
  }

  public void update() {
    GUI.scores[playerNum].setText("Player " + playerNum + ": " + scores[playerNum]
        + ", Num: " + numBombs + ", Rad: " + bombRadius + ", Speed: " + speed);
    boolean flagx = true;
    boolean flagy = true;
    int newx = x + velX;
    int newy = y + velY;
    /*
     * if (collides(newx, newy)){ for (int i = velX, j = velY; i != 0 && j != 0;
     * i--, j--){ newx = x+i; newy = y+j; if (!collides(newx, newy)){ break; } }
     * }
     */getUpgrade(newx, newy);

    if (collides(x + velX, y + velY)) {
      x = oldx;
      y = oldy;
      flagx = false;
      flagy = false;
    }

    if ((y + velY < 50) || (y + velY > 560)) {
      flagy = false;
    }
    if ((x + velX < 60) || (x + velX > 810)) {
      flagx = false;
    }
    if (flagy) {
      oldy = y;
      y += velY;
    }
    if (flagx) {
      oldx = x;
      x += velX;
    }
  }

  public void draw(Graphics2D g2d) {
    g2d.drawImage(getPlayerImg(), x, y, null);
  }

  public Image getPlayerImg() {
    ImageIcon ic = new ImageIcon("src/Project4/images/player" + playerNum
        + ".gif");
    return ic.getImage();
  }

  public void keyPressed(KeyEvent e){
    int key = e.getKeyCode();
    if ((key == KeyEvent.VK_UP && playerNum == 0) || (key == KeyEvent.VK_W && playerNum == 1)){
      velY = -speed;
      String stuff = Integer.toString(playerNum);
      String sendThis = "UP_PRESSED_" + stuff;
      if (playerNum == 0){
        Project4.theServer.sendString(sendThis);
      }
      else {
        Project4.theClient.sendString(sendThis);
      }
    } else if ((key == KeyEvent.VK_DOWN && playerNum == 0) || (key == KeyEvent.VK_S && playerNum == 1)){
      velY = speed;
      String stuff = Integer.toString(playerNum);
      String sendThis = "DOWN_PRESSED_" + stuff;
      if (playerNum == 0){
        Project4.theServer.sendString(sendThis);
      }
      else {
        Project4.theClient.sendString(sendThis);
      }
    } else if ((key == KeyEvent.VK_LEFT && playerNum == 0) || (key == KeyEvent.VK_A && playerNum == 1)){
      velX = -speed;
      String stuff = Integer.toString(playerNum);
      String sendThis = "LEFT_PRESSED_" + stuff;
      if (playerNum == 0){
        Project4.theServer.sendString(sendThis);
      }
      else {
        Project4.theClient.sendString(sendThis);
      }
    } else if ((key == KeyEvent.VK_RIGHT && playerNum == 0) || (key == KeyEvent.VK_D && playerNum == 1)){
      velX = speed;
      String stuff = Integer.toString(playerNum);
      String sendThis = "RIGHT_PRESSED_" + stuff;
      if (playerNum == 0){
        Project4.theServer.sendString(sendThis);
      }
      else {
        Project4.theClient.sendString(sendThis);
      }
    } 
    if (((key == KeyEvent.VK_SPACE && playerNum == 0) || (key == KeyEvent.VK_SHIFT && playerNum == 1))&& numBombs > 0){
      String stuff = Integer.toString(playerNum);
      String sendThis = "SPACE_PRESSED_" + stuff;
      if (playerNum == 0){
        Project4.theServer.sendString(sendThis);
      }
      else {
        Project4.theClient.sendString(sendThis);
      }
      doBomb();
    }
  }
  
  public void doBomb(){
    GUI.playSound("bomb.wav");
    gameLogic.placeBomb(playerNum);
    final int they = y/50;
    final int thex = x/60;
    final Bomb temp = new Bomb((60+((thex-1)*60)), (50+((they-1)*50)));
    gameLogic.gameGrid[they][thex] = cellType.PLAYER_AND_BOMB;
    try {
      GUI.grid[y/50][x/60].setIcon(new ImageIcon(ImageIO.read(new File("src/Project4/images/bomb_player" + playerNum + "_1.gif"))));
      Timer timer1 = new Timer();
      timer1.schedule(new TimerTask(){
        public void run(){
          PaintPane.addBomb(temp);
          try {
            GUI.grid[they][thex].setIcon(new ImageIcon(ImageIO.read(new File("src/Project4/images/bomb_player" + playerNum + "_2.gif"))));
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
            GUI.grid[they][thex].setIcon(new ImageIcon(ImageIO.read(new File("src/Project4/images/bomb_player" + playerNum + "_3.gif"))));
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
            GUI.grid[they][thex].setIcon(new ImageIcon(ImageIO.read(new File("src/Project4/images/empty.gif"))));
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          GUI.playSound("explosion.wav");
          PaintPane.removeBomb(temp);
          gameLogic.explodeBomb(new Location(thex, they), bombRadius, playerNum);
        }
      }, 3000);  
      
      
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
  }

  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();
    if ((key == KeyEvent.VK_UP && playerNum == 0)
        || (key == KeyEvent.VK_W && playerNum == 1)) {
      velY = 0;
      String stuff = Integer.toString(playerNum);
      String sendThis = "UP_RELEASED_" + stuff;
      if (playerNum == 0){
        Project4.theServer.sendString(sendThis);
      }
      else {
        Project4.theClient.sendString(sendThis);
      }
    } else if ((key == KeyEvent.VK_DOWN && playerNum == 0)
        || (key == KeyEvent.VK_S && playerNum == 1)) {
      velY = 0;
      String stuff = Integer.toString(playerNum);
      String sendThis = "DOWN_RELEASED_" + stuff;
      if (playerNum == 0){
        Project4.theServer.sendString(sendThis);
      }
      else {
        Project4.theClient.sendString(sendThis);
      }
    } else if ((key == KeyEvent.VK_LEFT && playerNum == 0)
        || (key == KeyEvent.VK_A && playerNum == 1)) {
      velX = 0;
      String stuff = Integer.toString(playerNum);
      String sendThis = "LEFT_RELEASED_" + stuff;
      if (playerNum == 0){
        Project4.theServer.sendString(sendThis);
      }
      else {
        Project4.theClient.sendString(sendThis);
      }
    } else if ((key == KeyEvent.VK_RIGHT && playerNum == 0)
        || (key == KeyEvent.VK_D && playerNum == 1)) {
      velX = 0;
      String stuff = Integer.toString(playerNum);
      String sendThis = "RIGHT_RELEASED_" + stuff;
      if (playerNum == 0){
        Project4.theServer.sendString(sendThis);
      }
      else {
        Project4.theClient.sendString(sendThis);
      }
    }
  }

  public boolean collides(int newx, int newy) {
    ArrayList<Rock> rocks = PaintPane.getRockList();

    for (int i = 0; i < rocks.size(); i++) {
      Rock temp = rocks.get(i);

      if (getBounds(newx, newy).intersects(temp.getBounds())) {
        return true;
      }
    }
    ArrayList<Obstruction> obstacles = PaintPane.getObstacleList();

    for (int i = 0; i < obstacles.size(); i++) {
      Obstruction temp = obstacles.get(i);

      if (getBounds(newx, newy).intersects(temp.getBounds())) {
        return true;
      }
    }
    ArrayList<Bomb> bombs = PaintPane.getBombList();

    for (int i = 0; i < bombs.size(); i++) {
      Bomb temp = bombs.get(i);

      if (getBounds(newx, newy).intersects(temp.getBounds())) {
        return true;
      }
    }
    /*
    ArrayList<Fire> fires = PaintPane.getFireList();

    for (int i = 0; i < fires.size(); i++) {
      Fire temp = fires.get(i);

      if (getBounds(newx, newy).intersects(temp.getBounds())) {
        //PaintPane.removePlayer(this);
        GUI.numPlayersAlive--;
        //gameLogic.roundOver(playerNum, false);
        return true;
      }
    }
    */
    return false;
  }

  public void getUpgrade(int newx, int newy) {
    ArrayList<Upgrade> upgrades = PaintPane.getUpgradeList();
    for (int i = 0; i < upgrades.size(); i++) {
      Upgrade temp = upgrades.get(i);
      if (getBounds(newx, newy).intersects(temp.getBounds())) {
        switch (temp.type) {
        case "RADIUS":
          bombRadius++;
          break;
        case "SPEED":
          speed++;
          break;
        case "AMOUNT":
          numBombs++;
          break;
        default:
          break;
        }
        PaintPane.removeUpgrade(temp);
        PaintPane.gameLogic.gameGrid[newy/50][newx/60] = cellType.PLAYER;
      }
    }
  }

  public Rectangle getBounds(int newx, int newy) {
    return new Rectangle(newx, newy, getPlayerImg().getWidth(null),
        getPlayerImg().getHeight(null) - 5);
  }

}
