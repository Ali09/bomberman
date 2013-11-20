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

import javax.swing.JPanel;
import javax.swing.Timer;

public class PaintPane extends JPanel implements ActionListener {
  
    Random rand = new Random();
  
    private Image background;
    Timer mainTimer;
    player p;
    static ArrayList<Obstruction> obstacles = new ArrayList<Obstruction>();
    static ArrayList<Rock> rocks = new ArrayList<Rock>();
    static Rock grid[][] = new Rock[11][13];
    
    int rockNum;

    public PaintPane(Image image) {     
        setFocusable(true);
        background = image;   
        p = new player(60, 50);
        addKeyListener(new KeyAdapt(p));
        
        mainTimer = new Timer(10, this);
        mainTimer.start();
        rockNum = rand.nextInt(15)+55;
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
                (grid[rockx][rocky] != null)){
              rockx = rand.nextInt(11);
              rocky = rand.nextInt(13);
            }
            grid[rockx][rocky] = new Rock(rockx, rocky);
            addRock(new Rock(60+(rocky*60), 50+(rockx*50)));
            rockx = rand.nextInt(11);
            rocky = rand.nextInt(13);
        }
        
        for (int i = 1; i <= 6; i++){
          for (int j = 1; j <= 5; j++){
            addObstacle(new Obstruction((i*60*2), (j*50*2)));
          }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return background == null ? new Dimension(0, 0) : new Dimension(background.getWidth(this), background.getHeight(this));            
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
    
    public void paint(Graphics g){
      super.paint(g);
      Graphics2D g2d = (Graphics2D) g;
      
      p.draw(g2d);
      
      for (int i = 0; i < rocks.size(); i++){
        Rock temp = rocks.get(i);
        temp.draw(g2d);
      }
      
      for (int i = 0; i < obstacles.size(); i++){
        Obstruction temp = obstacles.get(i);
        temp.draw(g2d);
      }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
      p.update();
      repaint();
    }

    public void addRock(Rock r){
      rocks.add(r);
    }
    
    public static ArrayList<Rock> getRockList(){
      return rocks;
    }
    
    public void addObstacle(Obstruction o){
      obstacles.add(o);
    }
    
    public static ArrayList<Obstruction> getObstacleList(){
      return obstacles;
    }
    
}