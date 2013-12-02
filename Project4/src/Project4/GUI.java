package Project4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sun.audio.*;
import java.io.*;

public class GUI extends JFrame {

  static JLabel grid[][];
  public static int numPlayers = 2;
  // Number of players alive
  public static int numPlayersAlive;
  public static JPanel[] scores;
  
  static JLabel Player0 = null;
  static JLabel score0 = new JLabel("");
  static JLabel boots0 = null;
  static JLabel speed0 = new JLabel("");
  static JLabel bombs0 = null;
  static JLabel num0 = new JLabel("");
  static JLabel radius0 = null;
  static JLabel rad0 = new JLabel("");
  
  static JLabel Player1 = null;
  static JLabel score1 = new JLabel("");
  static JLabel boots1 = null;
  static JLabel speed1 = new JLabel("");
  static JLabel bombs1 = null;
  static JLabel num1 = new JLabel("");
  static JLabel radius1 = null;
  static JLabel rad1 = new JLabel("");
  

  class backImage extends JComponent {
    private Image i;

    public backImage(Image i) {
      this.i = i;
    }

    @Override
    public void paintComponent(Graphics g) {
      g.drawImage(i, 0, 0, null);
    }
  }

  public static synchronized void playSound(final String url) {
    new Thread(new Runnable() {
      // The wrapper thread is unnecessary, unless it blocks on the
      // Clip finishing; see comments.
      public void run() {
        try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem
              .getAudioInputStream(GUI.class.getResourceAsStream("sounds/"
                  + url));
          clip.open(inputStream);
          clip.start();
        } catch (Exception e) {
          System.err.println(e.getMessage());
        }
      }
    }).start();
  }

  public GUI() {
    GUIcons();
    // For Looping background music
    new Thread(new Runnable() {
      public void run() {
        try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem
              .getAudioInputStream(GUI.class
                  .getResourceAsStream("sounds/music.wav"));
          clip.open(inputStream);
          clip.loop(Clip.LOOP_CONTINUOUSLY);
          Thread.sleep(1000000);
        } catch (Exception e) {
          System.err.println(e.getMessage());
        }
      }
    }).start();

  }

  public void GUIcons() {
    setTitle("Bomberman");
    // setLayout(new BorderLayout());

    setLayout(new BorderLayout());
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JPanel score = new JPanel(new GridLayout(1, numPlayers));

    add(score, BorderLayout.NORTH);

    scores = new JPanel[numPlayers];
    for (int i = 0; i < numPlayers; i++){
      scores[i] = new JPanel(new FlowLayout());
    }


    try {
      Player0 = new JLabel(new ImageIcon(ImageIO.read(new File("src/Project4/images/player0.gif"))));
      boots0 = new JLabel(new ImageIcon(ImageIO.read(new File("src/Project4/images/powerup_speed.gif"))));
      bombs0 = new JLabel(new ImageIcon(ImageIO.read(new File("src/Project4/images/powerup_bomb.gif"))));
      radius0 = new JLabel(new ImageIcon(ImageIO.read(new File("src/Project4/images/powerup_radius.gif"))));      
      Player1 = new JLabel(new ImageIcon(ImageIO.read(new File("src/Project4/images/player1.gif"))));
      boots1 = new JLabel(new ImageIcon(ImageIO.read(new File("src/Project4/images/powerup_speed.gif"))));
      bombs1 = new JLabel(new ImageIcon(ImageIO.read(new File("src/Project4/images/powerup_bomb.gif"))));
      radius1 = new JLabel(new ImageIcon(ImageIO.read(new File("src/Project4/images/powerup_radius.gif"))));   
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    
    
    scores[0].add(Player0);
    scores[0].add(score0);
    scores[0].add(boots0);
    scores[0].add(speed0);
    scores[0].add(bombs0);
    scores[0].add(num0);
    scores[0].add(radius0);
    scores[0].add(rad0);
    score.add(scores[0]);
    
    scores[1].add(Player1);
    scores[1].add(score1);
    scores[1].add(boots1);
    scores[1].add(speed1);
    scores[1].add(bombs1);
    scores[1].add(num1);
    scores[1].add(radius1);
    scores[1].add(rad1);
    score.add(scores[1]);
    
    
    numPlayersAlive = numPlayers;

    PaintPane pane = null;
    try {
      pane = new PaintPane(
          ImageIO.read(new File("src/Project4/images/map.gif")));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    pane.setLayout(new GridLayout(13, 15));

    add(pane, BorderLayout.CENTER);

    grid = new JLabel[13][15];
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 15; j++) {
        try {
          // if (i != 0 && i != 12 && j != 0 && j != 14 && !((i%2 == 0) && (j%2
          // == 0)) && (i == 1 && j == 1)){
          // Not adding the character here anymore since I have the movable guy.
          // grid[i][j] = new JLabel(new ImageIcon(ImageIO.read(new
          // File("C:/Users/Samuel/Desktop/adt-bundle-windows-x86_64-20130219/eecs285/Project4/sprite.gif"))));
          // }
          // else{
          grid[i][j] = new JLabel(new ImageIcon(ImageIO.read(new File(
              "src/Project4/images/empty.gif"))));
          // }
        } catch (IOException e) {
          System.out.println("Image not found");
          e.printStackTrace();
        }
        pane.add(grid[i][j]);

      }
    }

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }
}
