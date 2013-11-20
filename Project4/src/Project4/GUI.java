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
  
  private static JLabel grid[][];
  private static int numPlayers = 1;
  private static int scores[];
  
  
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
    
    scores = new int[numPlayers];
    
    for (int i = 0; i < numPlayers; i++){
      score.add(new JLabel("Player " + i + ": " + scores[i]));
    }
    
    
    
    
    
    player[] people = new player[numPlayers];
    for (int i = 0; i < numPlayers; i++){
      switch(i){
      case 1:
        people[i] = new player(100, 100);
        break;
      case 2:
        people[i] = new player(200, 200);
        break;
      case 3:
        people[i] = new player(300, 300);
        break;
      case 4:
        people[i] = new player(400, 400);
        break;
      }
    }
    
    
    PaintPane pane = null;
    try {
      pane = new PaintPane(ImageIO.read(new File("C:/Users/Samuel/Desktop/adt-bundle-windows-x86_64-20130219/eecs285/Project4/map.gif")));
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
            grid[i][j] = new JLabel(new ImageIcon(ImageIO.read(new File("C:/Users/Samuel/Desktop/adt-bundle-windows-x86_64-20130219/eecs285/Project4/empty.gif"))));
          //}
        } catch (IOException e) {
          System.out.println("Image not found");
          e.printStackTrace();
        }
        pane.add(grid[i][j]);

      }
    }
    ImageIcon rocks = null;
    try {
      rocks = new ImageIcon(ImageIO.read(new File("C:/Users/Samuel/Desktop/adt-bundle-windows-x86_64-20130219/eecs285/Project4/rocks.gif")));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    Random rand = new Random();
    int rockNum = rand.nextInt(15)+55;
    int rockx = rand.nextInt(11);
    int rocky = rand.nextInt(13);   
    for (int i = 0; i < rockNum; i++){
      while ((((rockx+1)%2 == 0) && ((rocky+1)%2 ==0)) ||
          (rockx == 0 && rocky == 0) ||
          (rockx == 0 && rocky == 1) ||
          (rockx == 1 && rocky == 0) ||
          (rockx == 0 && rocky == 12) ||
          (rockx == 0 && rocky == 11) ||
          (rockx == 1 && rocky == 12) ||
          (rockx == 10 && rocky == 0) ||
          (rockx == 9 && rocky == 0) ||
          (rockx == 10 && rocky == 1) ||
          (rockx == 10 && rocky == 12) ||
          (rockx == 9 && rocky == 12) ||
          (rockx == 10 && rocky == 11) ||
          (grid[rockx+1][rocky+1].getIcon() == rocks)){
        rockx = rand.nextInt(11);
        rocky = rand.nextInt(13);
      }
      grid[rockx+1][rocky+1].setIcon(rocks);
    
      rockx = rand.nextInt(11);
      rocky = rand.nextInt(13);
    }

    
    pack();
    setLocationRelativeTo(null);
    setVisible(true);    
  }
}
