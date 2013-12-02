package Project4;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class LocalOrNet extends JFrame {
  public String gameType = " ";

  public LocalOrNet() {
    setTitle("Local or Network");
    setLayout(new FlowLayout());

    JLabel ask = new JLabel("Choose:");

    JButton localButton = new JButton("Local");
    JButton networkButton = new JButton("Network");

    add(ask);
    add(localButton);
    add(networkButton);
    pack();
    setVisible(true);

    localButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        gameType = "LOCAL";
        setVisible(false);
      }
    });

    networkButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        gameType = "NETWORK";
        setVisible(false);
      }
    });
  }

}
