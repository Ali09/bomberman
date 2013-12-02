package Project4;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Project4 {
  static ClientServerSocket theServer = null;
  static ClientServerSocket theClient = null;
  
  static int scores[] = new int[2];
  
  static GUI thegui = null;
  
  public static void main(String[] args){
    
    networkWindow netWin = new networkWindow();
    
    while (netWin.connectionType.equals(" "))
    {
      System.out.print("");
    }

    thegui = new GUI();
    thegui.pack();
    thegui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    thegui.setVisible(true);
    
    System.out.println(netWin.connectionType);
    //ClientServerSocket theServer = null;
    if (netWin.connectionType.equals("HOST"))
    {
      
      // print out current ip address for players
      InetAddress ip = null;
      try 
      {
        ip = InetAddress.getLocalHost();
        
        JFrame ipWin = new JFrame("IP Address");
        ipWin.setLayout(new BorderLayout());
        JLabel ipLabel = new JLabel("Current IP address : " + ip.getHostAddress());
        ipWin.add(ipLabel, BorderLayout.NORTH);
        JLabel waitLabel = new JLabel("Waiting for client to connect...");
        ipWin.add(waitLabel, BorderLayout.SOUTH);
        ipWin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ipWin.pack();
        ipWin.setVisible(true);
       } 
      
      catch (UnknownHostException e) 
      {
        e.printStackTrace();
      }
      
      String recvdStr;
      
      theServer = new ClientServerSocket(ip.getHostAddress() , 45000);
      theServer.startServer();
      
      JFrame connectedWin = new JFrame("Succesfully Connected");
      connectedWin.setLayout(new FlowLayout());
      JLabel connectedLabel = new JLabel("Client connection accepted");
      connectedWin.add(connectedLabel);
      connectedWin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
      connectedWin.pack();
      connectedWin.setVisible(true);
      
      while (true)
      {
        String recvd = theServer.recvString();
        player temp = PaintPane.players.get(1);
        switch(recvd){
        case "UP_PRESSED_1": temp.velY = -temp.speed;
          break;
        case "DOWN_PRESSED_1": temp.velY = temp.speed;
          break;
        case "LEFT_PRESSED_1": temp.velX = -temp.speed;
          break;
        case "RIGHT_PRESSED_1": temp.velX = temp.speed;
          break;
        case "SPACE_PRESSED_1": temp.doBomb();
          break;
        case "UP_RELEASED_1": temp.velY = 0;
          break;
        case "DOWN_RELEASED_1": temp.velY = 0;
          break;
        case "LEFT_RELEASED_1": temp.velX = 0;
          break;
        case "RIGHT_RELEASED_1": temp.velX = 0;
          break;
        case "RESET": Reset starOver = new Reset(1);
          break;
        default:
          break;
        }
      }
    }
    else if (netWin.connectionType.equals("CLIENT"))
    {
      String recvdStr;
      
      ipInputWindow ipInWin = new ipInputWindow();
      
      while (ipInWin.ipAddr.equals(" "))
      {
        System.out.print("");
      }

      theClient = new ClientServerSocket(ipInWin.ipAddr, 45000);
      theClient.startClient();

      while (true)
      {
        String recvd = theClient.recvString();
        player temp = PaintPane.players.get(0);
        switch(recvd){
        case "UP_PRESSED_0": temp.velY = -temp.speed;
          break;
        case "DOWN_PRESSED_0": temp.velY = temp.speed;
          break;
        case "LEFT_PRESSED_0": temp.velX = -temp.speed;
          break;
        case "RIGHT_PRESSED_0": temp.velX = temp.speed;
          break;
        case "SPACE_PRESSED_0": temp.doBomb();
          break;
        case "UP_RELEASED_0": temp.velY = 0;
          break;
        case "DOWN_RELEASED_0": temp.velY = 0;
          break;
        case "LEFT_RELEASED_0": temp.velX = 0;
          break;
        case "RIGHT_RELEASED_0": temp.velX = 0;
          break;
        case "RESET": Reset startOver = new Reset(0);
          break;
        default:
          break;
        }
      }
    }
  }


}


