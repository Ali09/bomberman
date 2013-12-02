package Project4;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import Project4.gameLogic.cellType;

public class Reset {

  Reset(int playerNum){
    System.out.println(playerNum);
    System.out.println(GUI.numPlayersAlive);
    if (GUI.numPlayersAlive == 0){
      
    }
    else if (GUI.numPlayersAlive == 1){
      for (int i = 0; i < GUI.numPlayers; i++){
        if (i == playerNum){
          continue;
        }
        else{
          Project4.scores[i]++;
        }
      }
    }

    //PaintPane.fires.clear();
    GUI.numPlayersAlive = 2;
    for (int i = 0; i < 2; i++){
      player move = PaintPane.players.get(i);
      if (i == 0){
        move.x = 60+15;
        move.y = 50+2;
      }
      else{
        move.x = 13*60+15;
        move.y = 11*50+2;
      }
    }
  }
}
