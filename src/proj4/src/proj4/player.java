package proj4;

// player class describing
// bomberman charecter
public class player
{ 
  public int playerNum;
  public Location loc;
  public gameLogic.direction playerDirection;
  public boolean alive;
  
  // number of bombs player is able
  // to drop at a given instant, initially 1
  public int numBombs;
  
  // radius of bomb explosion for player, initially 1
  public int bombRadius;
  
  //checks for movement spam
  public long spam = System.currentTimeMillis();
  
  public int score;
  
  public player()
  {
    
  }
}
