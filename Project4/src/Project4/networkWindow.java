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

public class networkWindow extends JFrame
{
  public String connectionType = " ";
  
  public networkWindow()
  {
    setTitle("Network Type");
    setLayout(new FlowLayout());    
    
    JLabel networkAsk = new JLabel("Network type:");
    
    JButton hostButton = new JButton("Host");
    JButton clientButton = new JButton("Client");
    
    add(networkAsk);
    add(hostButton);
    add(clientButton);
    pack();
    setVisible(true);
    
    hostButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        connectionType = "HOST";
        setVisible(false);
      }
    });
    
    clientButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        connectionType = "CLIENT";
        setVisible(false);
      }
    });
  }
  
}
