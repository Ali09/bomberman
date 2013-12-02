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

public class ipInputWindow extends JFrame {
  String ipAddr = " ";
  JTextField ipField;

  public ipInputWindow() {
    // window for ip
    setTitle("IP Address Input");
    setLayout(new BorderLayout());

    JLabel ipLabel = new JLabel("\t\t\tEnter IP Address of Host:");
    JButton okButton = new JButton("OK");

    ipField = new JTextField();

    JPanel topPanel = new JPanel(new FlowLayout());
    JPanel midPanel = new JPanel(new FlowLayout());
    JPanel botPanel = new JPanel(new FlowLayout());

    topPanel.add(ipLabel);
    midPanel.add(ipField);
    botPanel.add(okButton);

    // add listeners for ok button
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          ipAddr = ipField.getText();
          setVisible(false);
        }

        catch (NumberFormatException exception) {
          System.out.println("Bad field input");
        }
      }
    });

    add(ipLabel, BorderLayout.NORTH);
    add(ipField, BorderLayout.CENTER);
    add(botPanel, BorderLayout.SOUTH);

    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

    setSize(500, 100);
    setVisible(true);
  }
}
