package proj4;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class gameLogic
{
  // type of object in given cell
  public enum cellType { GRASS, STONE, DESTRUCTABLE_STONE, FIRE, BOMB,
                         PLAYER, PLAYER_DEAD, PLAYER_AND_BOMB, POWERUP };
  
  // type of moves player can make  
  // and given direction of player
  public enum direction { UP, RIGHT, DOWN, LEFT };
  
  // array of players playing
  // indexed by player number
  public static player [] players;
  
  // number of active/alive players
  static int numPlayersAlive;
  
  private static final int NUM_ROWS = 1000;
  private static final int NUM_COLS = 1000;
  public static cellType [][] gameGrid;
  
  
  // returns whether given location
  // is within grid map
  public static boolean validLocation(Location loc)
  {
    boolean xValid = (loc.x >= 0) && (loc.x < NUM_COLS);
    boolean yValid = (loc.y >= 0) && (loc.y < NUM_ROWS);
    return xValid && yValid;
  }
  
  // returns whether given location
  // is "walkable", i.e. within the grid
  // and either a grass or fire tile
  public static boolean walkableLocation(Location loc)
  {
    boolean validLoc = validLocation(loc);
    
    if (validLoc == false)
      return false;
    
    cellType locType = gameGrid[loc.y][loc.x];
    
    if (locType == cellType.GRASS ||
        locType == cellType.FIRE)
      return true;
    else
      return false;
  }
  
  // moves player to new location if possible
  // and updates player object and grid appropriately
  public static void movePlayer(int playerNum, direction moveDirection)
  {
    player movedPlayer = players[playerNum];
    
    // if player is dead, do nothing
    if (movedPlayer.alive == false)
      return;
    
    // regardless of object, player now
    // faces new direction
    movedPlayer.playerDirection = moveDirection;
    
    // new location
    Location oldLoc = movedPlayer.loc;
    Location newLoc = new Location(oldLoc.x, oldLoc.y);
    
    if (moveDirection == direction.UP)
      newLoc.y--;
    else if (moveDirection == direction.RIGHT)
      newLoc.x++;
    else if (moveDirection == direction.DOWN)
      newLoc.y++;
    else
      newLoc.x--;
    
    // if location is off grid, or not walkable (i.e. not grass or fire)
    // return (and redraw to show new player direction)
    if (walkableLocation(newLoc))
    {
      redrawGrid();
      return;
    }
    
    // the old location will now either be grass
    // or a bomb that was placed by player
    cellType oldLocType = gameGrid[newLoc.y][newLoc.x];
    
    if (oldLocType == cellType.GRASS)
    {
      gameGrid[oldLoc.y][oldLoc.x] = cellType.GRASS;
    }
    
    else if (oldLocType == cellType.PLAYER_AND_BOMB)
    {
      gameGrid[oldLoc.y][oldLoc.x] = cellType.BOMB; 
    }
    
    else
    {
      System.out.println("Error: player was in an invalid location!");
    }
    
    // if new location is just grass, move and return
    cellType locType = gameGrid[newLoc.y][newLoc.x];
    if (locType == cellType.GRASS)
    {
      movedPlayer.loc = newLoc;
      gameGrid[newLoc.y][newLoc.x] = cellType.PLAYER;
      redrawGrid();
      return;
    }
    
    // if new location is fire, 
    // set grid spot to dead player
    // tell player it has died
    // and call roundOver() if only one player remains
    if (locType == cellType.FIRE)
    {
      movedPlayer.loc = newLoc;
      movedPlayer.alive = false;
      gameGrid[newLoc.y][newLoc.x] = cellType.PLAYER_DEAD;

      clearDeadTimer(this, loc, 1000);      
      
      redrawGrid();
      
      numPlayersAlive--;
      if (numPlayersAlive == 1)
      {
        roundOver();
      }
      
      return;
    }
    
    
    System.out.println("Error: somehow got to end of movePlayer!");
  }
  
  // places bomb in player's current
  // location if the player is able to 
  // drop anymore bombs
  private static void roundOver() {
    // TODO Auto-generated method stub
    
  }

  public static void placeBomb(int playerNum)
  {
    player bombPlayer = players[playerNum];
    
    // if player is dead, do nothing
    if (bombPlayer.alive == false)
      return;
    
    // no bombs left, do nothing
    if (bombPlayer.numBombs == 0)
      return;
    
    bombPlayer.numBombs--;
    
    // place bomb in current location
    // create timer object for bomb
    Location curLoc = bombPlayer.loc;
    gameGrid[curLoc.y][curLoc.x] = cellType.PLAYER_AND_BOMB;
    bombTimer(this, curLoc, bombPLayer.bombRadius, playerNum <- (for explodeBomb), 3 seconds);
    
    redrawGrid();
    return;
  }
  
  
  // returns whether a given spot can be exploded (grass or player),
  // exploded and destroyed ( destructable stone, powerup, or another bomb (w/ or w/out player)
  // or stopped (stone or off map)
  public enum explodeType { NOT_EXPLODABLE, EXPLODABLE, DESTROY_AND_STOP };
  public explodeType explodableLocation(Location loc)
  {
    if (validLocation(loc) == false)
      return explodeType.NOT_EXPLODABLE;
    
    cellType locType = gameGrid[loc.y][loc.x];
    
    if (locType == cellType.STONE)
      return explodeType.NOT_EXPLODABLE;
    
    else if (locType == cellType.DESTRUCTABLE_STONE ||
             locType == cellType.BOMB ||
             locType == cellType.PLAYER_AND_BOMB ||
             locType == cellType.POWERUP)
      return explodeType.DESTROY_AND_STOP;
    
    else
      return explodeType.EXPLODABLE;
  }
  
  
  // explodes bomb in a given location with radius specified
  // creates fire in all directions until fire is of size radius
  // or it has hit a stone, 
  // or a destructable tile / powerup (in which case it destroys object)
  public void explodeBomb(Location bombLoc, int bombRadius, int playerNum)
  {
    // invalid location, do nada
    if (validLocation(bombLoc) == false)
      return;
    
    // if bomb does not exist in location anymore, do nada
    if (gameGrid[bombLoc.y][bombLoc.x] != cellType.BOMB && 
        gameGrid[bombLoc.y][bombLoc.x] != cellType.PLAYER_AND_BOMB)
      return;
    
    // first increment number of bomb's player can plant again
    player bombPlayer = players[playerNum];
    bombPlayer.numBombs++;
    
    // create stack of all locations which the explosion
    // would effect
    Vector<Location> locStack = new Vector<Location>();
    
    // first add bomb's location to stack
    locStack.add(bombLoc);
    
    // check north locations
    for (int i = 1; i <= bombRadius; i++)
    {
      Location northLoc = new Location(bombLoc.x, bombLoc.y - i);
      
      // type of explosion at loc (non, explode, destructable)
      explodeType explodeLocType = explodableLocation(northLoc);
      
      // not explodable, don't add any more locs
      if (explodeLocType == explodeType.NOT_EXPLODABLE)
        break;
      
      // destroyable, add loc but break
      else if (explodeLocType == explodeType.DESTROY_AND_STOP)
      {
        locStack.add(northLoc);
        break;
      }
      
      // else, explodable, keep adding
      else
        locStack.add(northLoc);
    }
    
    // east
    for (int i = 1; i <= bombRadius; i++)
    {
      Location northLoc = new Location(bombLoc.x + i, bombLoc.y);
      
      explodeType explodeLocType = explodableLocation(northLoc);
      
      if (explodeLocType == explodeType.NOT_EXPLODABLE)
        break;
      
      else if (explodeLocType == explodeType.DESTROY_AND_STOP)
      {
        locStack.add(northLoc);
        break;
      }
      
      else
        locStack.add(northLoc);
    }
    
    // south
    for (int i = 1; i <= bombRadius; i++)
    {
      Location northLoc = new Location(bombLoc.x + i, bombLoc.y);
      
      explodeType explodeLocType = explodableLocation(northLoc);
      
      if (explodeLocType == explodeType.NOT_EXPLODABLE)
        break;
      
      else if (explodeLocType == explodeType.DESTROY_AND_STOP)
      {
        locStack.add(northLoc);
        break;
      }
      
      else
        locStack.add(northLoc);
    }
    
    // west
    for (int i = 1; i <= bombRadius; i++)
    {
      Location northLoc = new Location(bombLoc.x + i, bombLoc.y);
      
      explodeType explodeLocType = explodableLocation(northLoc);
      
      if (explodeLocType == explodeType.NOT_EXPLODABLE)
        break;
      
      else if (explodeLocType == explodeType.DESTROY_AND_STOP)
      {
        locStack.add(northLoc);
        break;
      }
      
      else
        locStack.add(northLoc);
    }
    
    // check each location on stack
    // and destroy
    // update if player found
    for (Location loc : locStack)
    {
      cellType locType = gameGrid[loc.y][loc.x];
      
      // if cell contains a player
      // update that cell to be dead player
      // and update player in that location
      if (locType == cellType.PLAYER ||
          locType == cellType.PLAYER_AND_BOMB)
      {
        gameGrid[loc.y][loc.x] = cellType.PLAYER_DEAD;
        
        // player in given location
        // check all current alive players for their location
        player deadPlayer = null;
        for (player alivePlayer : players)
        {
          if (alivePlayer.alive == true &&
              alivePlayer.loc.x == loc.x &&
              alivePlayer.loc.y == loc.y)
            deadPlayer = alivePlayer;
        }
        
        deadPlayer.alive = false;
        
        numPlayersAlive--;
        if (numPlayersAlive == 1)
        {
          roundOver();
        }
        
        // player is dead so create dead player timer
        // removes dead player from grid after so many seconds
        Timer timer;
        timer = new Timer();
        timer.schedule(new clearDeadTimer(this, loc), 1000);
      }
      
      // else just set location on fire
      // and create fire timer to clear it
      else
      {
        gameGrid[loc.y][loc.x] = cellType.FIRE;
        Timer timer;
        timer = new Timer();
        timer.schedule(new fireBallTimer(this, loc), 500);
      }
    }
    
    // finally redraw
    redrawGrid();
  }

  // function that updates images
  // based on current state of grid
  public static void redrawGrid()
  {
    
  }
  
}
