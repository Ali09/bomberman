package Project4;
import javax.swing.JFrame;


public class Project4 {
  public static void main(String[] args){
    GUI thegui = new GUI();;
    thegui.pack();
    thegui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    thegui.setVisible(true);
  }
}
