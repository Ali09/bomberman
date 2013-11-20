package Project4;

import java.util.TimerTask;

import Project4.gameLogic.cellType;

public class clearDeadTimer extends TimerTask {

  public clearDeadTimer(gameLogic gameLogic, Location loc) 
  {
    //the space becomes grass instead of a dead body
    gameLogic.gameGrid[loc.y][loc.x] = cellType.GRASS;
  }

  public void run() {
    

  }

}
