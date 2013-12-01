package Project4;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Project4 {
  static ClientServerSocket theServer = null;
  static ClientServerSocket theClient = null;
  
  public static void main(String[] args){

    String serverType = new String(" ");
    Scanner stdin = new Scanner(System.in);
    while (!(serverType.equals("host") || serverType.equals("client")))
    {
      System.out.println("Are you a host or client:");
      serverType = stdin.nextLine();
    }

    GUI thegui = new GUI();;
    thegui.pack();
    thegui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    thegui.setVisible(true);
    //ClientServerSocket theServer = null;
    if (serverType.equals("host"))
    {
      
      // print out current ip address for players
      InetAddress ip = null;
      try 
      {
        ip = InetAddress.getLocalHost();
        System.out.println("Current IP address : " + ip.getHostAddress());
      } 
      
      catch (UnknownHostException e) 
      {
        e.printStackTrace();
      }
      
      String recvdStr;
      
      theServer = new ClientServerSocket(ip.getHostAddress() , 45000);
      theServer.startServer();
      
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
        default:
          break;
        }
      }
    }
    else if (serverType.equals("client"))
    {
      String recvdStr;

      String serverIP = new String(" ");
      Scanner stdin2 = new Scanner(System.in);
      while (serverIP.equals(" "))
      {
        System.out.println("IP Address of Host: ");
        serverIP  = stdin2.nextLine();
      }

      theClient = new ClientServerSocket(serverIP, 45000);
      theClient.startClient();
      theClient.sendString("noob from client");
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
        default:
          break;
        }
      }
    }
  }


}


