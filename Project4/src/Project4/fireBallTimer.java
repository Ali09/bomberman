package Project4;

import java.util.TimerTask;

import Project4.gameLogic.cellType;

public class fireBallTimer extends TimerTask {

  public fireBallTimer(gameLogic gameLogic, Location loc) 
  {
    //the space becomes grass instead of a fireball
    gameLogic.gameGrid[loc.y][loc.x] = cellType.GRASS;
  }

  public void run() {
    

  }

}
