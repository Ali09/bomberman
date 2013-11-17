package proj4;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import proj4.gameLogic.direction;
//idk i need to import this somehow
import javafx.scene.input.KeyCode;

public class game {

//game
  
  //basically mouseListener
  private void processKeys()
  {
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
        new KeyEventDispatcher()  
        { 
            public boolean dispatchKeyEvent(KeyEvent e)
            {
                if(e.getID() == KeyEvent.KEY_PRESSED)
                {
                  handleKeyPress(e.getKeyCode());
                }
                return false;
            } 
        });
  }

  private void handleKeyPress(int keyCode)
  {
    switch (keyCode)
    {
    case LEFT://LEFT KEY
      if(spamCheck(1))
        gameLogic.movePlayer(1, direction.LEFT);
      break;
    case UP://UP KEY
      if(spamCheck(1))
        gameLogic.movePlayer(1, direction.UP);
      break;
    case RIGHT://RIGHT KEY
      if(spamCheck(1))
        gameLogic.movePlayer(1, direction.RIGHT);
      break;
    case DOWN://DOWN KEY
      if(spamCheck(1))
        gameLogic.movePlayer(1, direction.DOWN);
      break;
    case NUM0: //Bomb KEY
      gameLogic.placeBomb(1);
      break;
    case A://LEFT KEY
      if(spamCheck(0))
        gameLogic.movePlayer(0, direction.LEFT);
      break;
    case W://UP KEY
      if(spamCheck(0))
        gameLogic.movePlayer(0, direction.UP);
      break;
    case D://RIGHT KEY
      if(spamCheck(0))
        gameLogic.movePlayer(0, direction.RIGHT);
      break;
    case S://DOWN KEY
      if(spamCheck(0))
        gameLogic.movePlayer(0, direction.DOWN);
      break;
    case SPACE: //Bomb KEY
      gameLogic.placeBomb(0);
      break;
    }
  }
//if the player tries to move twice within 100 ms it ignores the command
  private boolean spamCheck(int playerNum)
  {
    if(System.currentTimeMillis() - gameLogic.players[playerNum].spam > 100)
    {
      gameLogic.players[playerNum].spam = System.currentTimeMillis();
      return true;
    }
    return false;
  }
}