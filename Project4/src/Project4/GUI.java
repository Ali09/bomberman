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
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JFrame{
  
  static JLabel grid[][];
  public static int numPlayers = 2;
  //Number of players alive
  public static int numPlayersAlive;
  public static JLabel scores[];
  
  
  class backImage extends JComponent{
      private Image i;
     
      public backImage(Image i){
        this.i = i;
      }
      @Override
      public void paintComponent(Graphics g){
        g.drawImage(i, 0, 0, null);
      }
  }
  
  public GUI(){
    setTitle("Bomberman");
    //setLayout(new BorderLayout());
        
    setLayout(new BorderLayout());
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JPanel score = new JPanel(new GridLayout(1, numPlayers));

    add(score, BorderLayout.NORTH);
    
    scores = new JLabel[numPlayers];
    
    for (int i = 0; i < numPlayers; i++){
      scores[i] = new JLabel("Player " + i + ": 0, Num: 1, Rad: 1, Speed: 1");
      score.add(scores[i]);
    }
    
    
    
    numPlayersAlive = numPlayers;
    
    
    PaintPane pane = null;
    try {
      pane = new PaintPane(ImageIO.read(new File("src/Project4/images/map.gif")));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    pane.setLayout(new GridLayout(13,15));

    
    add(pane, BorderLayout.CENTER);

    grid = new JLabel[13][15];
    for (int i = 0; i < 13; i++){
      for (int j = 0; j < 15; j++){
        try {
          //if (i != 0 && i != 12 && j != 0 && j != 14 && !((i%2 == 0) && (j%2 == 0)) && (i == 1 && j == 1)){
            //Not adding the character here anymore since I have the movable guy.
            //grid[i][j] = new JLabel(new ImageIcon(ImageIO.read(new File("C:/Users/Samuel/Desktop/adt-bundle-windows-x86_64-20130219/eecs285/Project4/sprite.gif"))));
          //}
          //else{
            grid[i][j] = new JLabel(new ImageIcon(ImageIO.read(new File("src/Project4/images/empty.gif"))));
          //}
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
