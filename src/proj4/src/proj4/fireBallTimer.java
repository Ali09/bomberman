package proj4;

import java.util.TimerTask;

import proj4.gameLogic.cellType;

public class fireBallTimer extends TimerTask {

  public fireBallTimer(gameLogic gameLogic, Location loc) 
  {
    proj4.gameLogic.gameGrid[loc.y][loc.x] = cellType.GRASS;
  }

  public void run() {
    

  }

}
