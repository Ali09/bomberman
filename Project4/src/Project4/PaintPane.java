package Project4;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PaintPane extends JPanel implements ActionListener {

    private Image background;
    Timer mainTimer;
    player p;

    public PaintPane(Image image) {     
        // This is just an example, I'd prefer to use setters/getters
        // and would also need to provide alignment options ;)
        setFocusable(true);
        background = image;   
        p = new player(60, 50);
        
        addKeyListener(new KeyAdapt(p));
        
        
        
        mainTimer = new Timer(10, this);
        mainTimer.start();
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
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
      p.update();
      repaint();
    }

}