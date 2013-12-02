package Project4;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class enterSeed extends JFrame {
  String seedNum = " ";
  JTextField seedField;

  public enterSeed() {
    // window for ip
    setTitle("Seed Input");
    setLayout(new BorderLayout());

    JLabel seedLabel = new JLabel("\t\t\tEnter Seed Number:");
    JButton okButton = new JButton("OK");

    seedField = new JTextField();

    JPanel topPanel = new JPanel(new FlowLayout());
    JPanel midPanel = new JPanel(new FlowLayout());
    JPanel botPanel = new JPanel(new FlowLayout());

    topPanel.add(seedLabel);
    midPanel.add(seedField);
    botPanel.add(okButton);

    // add listeners for ok button
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          seedNum = seedField.getText();
          setVisible(false);
        }

        catch (NumberFormatException exception) {
          System.out.println("Bad field input");
        }
      }
    });

    add(seedLabel, BorderLayout.NORTH);
    add(seedField, BorderLayout.CENTER);
    add(botPanel, BorderLayout.SOUTH);

    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

    setSize(500, 100);
    setVisible(true);
  }
}
