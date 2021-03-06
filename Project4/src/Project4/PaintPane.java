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
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.Timer;

import Project4.gameLogic.cellType;

public class PaintPane extends JPanel implements ActionListener {

  static Random rand = new Random(Project4.seednum);

  private Image background;
  Timer mainTimer;
  static ArrayList<player> players = new ArrayList<player>();
  static ArrayList<Bomb> bombs = new ArrayList<Bomb>();
  static ArrayList<Obstruction> obstacles = new ArrayList<Obstruction>();
  static ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
  static ArrayList<ArrayList<Fire>> fires = new ArrayList<ArrayList<Fire>>();
  static ArrayList<Rock> rocks = new ArrayList<Rock>();
  static Rock grid[][] = new Rock[11][13];
  static gameLogic gameLogic;

  int rockNum;

  public PaintPane(Image image) {
    setFocusable(true);
    background = image;

    gameLogic = new gameLogic();
    gameLogic.players = new player[GUI.numPlayers];

    for (int i = 0; i < GUI.numPlayers; i++) {
      if (i == 0) {
        player temp = new player(60 + 15, 50 + 2, i);
        addKeyListener(new KeyAdapt(temp));
        gameLogic.players[i] = temp;
        players.add(temp);
      } else if (i == 1) {
        player temp = new player(13 * 60 + 15, 11 * 50 + 2, i);
        addKeyListener(new KeyAdapt(temp));
        players.add(temp);
        gameLogic.players[i] = temp;
      }
    }

    mainTimer = new Timer(10, this);
    mainTimer.start();

    gameLogic.gameGrid = new cellType[gameLogic.NUM_ROWS][gameLogic.NUM_COLS];
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 15; j++) {
        gameLogic.gameGrid[i][j] = cellType.GRASS;
      }
    }
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 15; j++) {
        if ((i == 0) || (i == 12) || (j == 0) || (j == 14)) {
          gameLogic.gameGrid[i][j] = cellType.STONE;
        }
      }
    }

    rockNum = rand.nextInt(15) + 55;
    int rockx = rand.nextInt(11);
    int rocky = rand.nextInt(13);
    // ROCKX IS ROCKY
    for (int i = 0; i < rockNum; i++) {
      while ((((rockx + 1) % 2 == 0) && ((rocky + 1) % 2 == 0))
          || (rockx == 0 && rocky == 0) || (rockx == 0 && rocky == 1)
          || (rockx == 1 && rocky == 0) || (rockx == 0 && rocky == 12)
          || (rockx == 0 && rocky == 11) || (rockx == 1 && rocky == 12)
          || (rockx == 10 && rocky == 0) || (rockx == 9 && rocky == 0)
          || (rockx == 10 && rocky == 1) || (rockx == 10 && rocky == 12)
          || (rockx == 9 && rocky == 12) || (rockx == 10 && rocky == 11)
          || (grid[rockx][rocky] != null)) {
        rockx = rand.nextInt(11);
        rocky = rand.nextInt(13);
      }
      grid[rockx][rocky] = new Rock(rockx, rocky);
      addRock(new Rock(((rocky + 1) * 60), ((rockx + 1) * 50)));
      gameLogic.gameGrid[rockx + 1][rocky + 1] = cellType.DESTRUCTABLE_STONE;
      rockx = rand.nextInt(11);
      rocky = rand.nextInt(13);
    }

    for (int i = 1; i <= 5; i++) {
      for (int j = 1; j <= 6; j++) {
        addObstacle(new Obstruction((j * 60 * 2), (i * 50 * 2)));
        gameLogic.gameGrid[i * 2][j * 2] = cellType.STONE;
      }
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return background == null ? new Dimension(0, 0) : new Dimension(
        background.getWidth(this), background.getHeight(this));
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

  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;

    // Just reinforces the barriers as not destroyable
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 15; j++) {
        if ((i % 2 == 0) && (j % 2 == 0)) {
          gameLogic.gameGrid[i][j] = cellType.STONE;
        }
      }
    }

    for (int i = 0; i < players.size(); i++) {
      player temp = players.get(i);
      gameLogic.updateLocation(temp.playerNum);
      temp.draw(g2d);
    }

    for (int i = 0; i < rocks.size(); i++) {
      Rock temp = rocks.get(i);
      temp.draw(g2d);
    }

    for (int i = 0; i < obstacles.size(); i++) {
      Obstruction temp = obstacles.get(i);
      temp.draw(g2d);
    }

    for (int i = 0; i < upgrades.size(); i++) {
      Upgrade temp = upgrades.get(i);
      temp.draw(g2d);
    }
    for (int i = 0; i < fires.size(); i++) {
      ArrayList<Fire> temp1 = fires.get(i);
      for (int j = 0; j < temp1.size(); j++) {
        Fire temp2 = temp1.get(j);
        temp2.draw(g2d);
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    for (int i = 0; i < players.size(); i++) {
      player temp = players.get(i);
      temp.update();
    }

    repaint();
  }

  public void addPlayer(player p) {
    players.add(p);
  }

  public static void removePlayer(player p) {
    players.remove(p);
  }

  public static ArrayList<player> getPlayerList() {
    return players;
  }

  public void addRock(Rock r) {
    rocks.add(r);
  }

  public static void removeRock(int y, int x) {
    ArrayList<Rock> rocks = PaintPane.getRockList();
    for (int i = 0; i < rocks.size(); i++) {
      Rock temp = rocks.get(i);
      if ((temp.x == x * 60) && (temp.y == y * 50)) {
        rocks.remove(temp);
        if (rand.nextInt(3) < 2) {
          Upgrade replace = new Upgrade(x * 60, y * 50);
          addUpgrade(replace);
          gameLogic.gameGrid[y][x] = cellType.POWERUP;
        }
        break;
      }
    }
  }

  public static ArrayList<Rock> getRockList() {
    return rocks;
  }

  public void addObstacle(Obstruction o) {
    obstacles.add(o);
  }

  public static ArrayList<Obstruction> getObstacleList() {
    return obstacles;
  }

  public static void addBomb(Bomb b) {
    bombs.add(b);
  }

  public static void removeBomb(Bomb b) {
    bombs.remove(b);
  }

  public static ArrayList<Bomb> getBombList() {
    return bombs;
  }

  public static void addUpgrade(Upgrade u) {
    upgrades.add(u);
  }

  public static void removeUpgrade(Upgrade u) {
    upgrades.remove(u);
  }

  public static void removeUpgrade(int y, int x) {
    ArrayList<Upgrade> upgrades = PaintPane.getUpgradeList();
    for (int i = 0; i < upgrades.size(); i++) {
      Upgrade temp = upgrades.get(i);
      if ((temp.x == x * 60) && (temp.y == y * 50)) {
        upgrades.remove(temp);
        PaintPane.gameLogic.gameGrid[y][x] = cellType.GRASS;
        break;
      }
    }
  }

  public static ArrayList<Upgrade> getUpgradeList() {
    return upgrades;
  }

}