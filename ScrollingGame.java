import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

//A Simple version of the scrolling game, featuring Avoids, Gets, and RareGets
//Players must reach a score threshold to win
//If player runs out of HP (via too many Avoid collisions) they lose
public class ScrollingGame extends GameEngine {
    
    
    //Starting Player coordinates
    protected static final int STARTING_PLAYER_X = 0;
    protected static final int STARTING_PLAYER_Y = 100;
    
    //Score needed to win the game
    protected static final int SCORE_TO_WIN = 300;
    
    //Maximum that the game speed can be increased to
    //(a percentage, ex: a value of 300 = 300% speed, or 3x regular speed)
    protected static final int MAX_GAME_SPEED = 300;
    protected static final int MIN_GAME_SPEED = 20;
    //Interval that the speed changes when pressing speed up/down keys
    protected static final int SPEED_CHANGE_INTERVAL = 20;   
    
    protected int gameSpeed = 100;
    
    public static final String INTRO_SPLASH_FILE = "game_assets/splash.gif";        
    //Key pressed to advance past the splash screen
    public static final int ADVANCE_SPLASH_KEY = KeyEvent.VK_ENTER;
    
    //Interval that Entities get spawned in the game window
    //ie: once every how many ticks does the game attempt to spawn new Entities
    protected static final int SPAWN_INTERVAL = 45;

    
    //A Random object for all your random number generation needs!
    public static final Random rand = new Random();
    
    //Player's current score
    protected int score;

    //Pause Game Variable
    private int savedSpeed;
    
    
    //Stores a reference to game's Player object for quick reference
    //(This Player will also be in the displayList)
    protected Player player;
    
    
    public ScrollingGame(){
        super();
    }
    
    public ScrollingGame(int gameWidth, int gameHeight){
        super(gameWidth, gameHeight);
    }
    
    
    //Performs all of the initialization operations that need to be done before the game starts
    protected void pregame(){
        this.setBackgroundColor(Color.BLACK);
        player = new Player(STARTING_PLAYER_X, STARTING_PLAYER_Y);
        displayList.add(player); 
        score = 0;
        this.setSplashImage(INTRO_SPLASH_FILE);
        setTitleText("Super Scrolling Game"); 
    }
    
    //Called on each game tick
    protected void updateGame(){
        //scroll all scrollable Entities on the game board
        scrollEntities();   
        //Spawn new entities only at a certain interval
        if (super.getTicksElapsed() % SPAWN_INTERVAL == 0){
            spawnEntities();
        }
        //Update the title text on the top of the window
        setTitleText("HP: " + player.getHP() + ", Score: " + score);
        //checks if the player has collided with any Consumables
        Collection<Entity> collidingEntities = findCollisions(player);
        for (Entity e : collidingEntities){
            if (e instanceof Consumable){
                handlePlayerCollision((Consumable)e);
            }
        }
    }
    
    
    //Scroll all scrollable entities per their respective scroll speeds
    protected void scrollEntities(){

        for (Entity e : displayList){
            if (e instanceof Scrollable){
                ((Scrollable)e).scroll();
                if (e.getX() < -e.getWidth()){
                    toBeGC.add(e);
                }
            }
        }

    }
    
    
    
    //Called whenever it has been determined that the Player collided with a consumable
    protected void handlePlayerCollision(Consumable collidedWith){

        if (collidedWith instanceof RareGet){
            score += collidedWith.getPoints();
            player.setHP(player.getHP() + 1);
        }
        else if (collidedWith instanceof Get){
            score += collidedWith.getPoints();
        }
        else if (collidedWith instanceof Avoid){
            player.setHP(player.getHP() + collidedWith.getDamage());
        }
        toBeGC.add((Entity) collidedWith);

    }
    
    
    //Spawn new Entities on the right edge of the game board
    // They should not overlap with any other Entities on the board
    protected void spawnEntities(){

        int amount = rand.nextInt(20);
        if (amount > 2 && amount <= 6)
            amount = 1;
        else if (amount >= 7 && amount <= 12)
            amount = 2;
        else if (amount >= 13 && amount <= 19)
            amount = 3;
        else
            amount = 4;


        for (int i = 0; i < amount; i++) {
            int type = rand.nextInt(20);
            int y = rand.nextInt(getWindowHeight() - 75);
            boolean collision = true;
            while (collision){
                collision = false;
                for (Entity entity : displayList) {
                    if (entity.isCollidingWith(new Avoid(getWindowWidth(), y))) {
                        y = (y + entity.getHeight() + 1) % (getWindowHeight() - 75);
                        collision = true;
                        break;
                    }
                }
            }

            if (type < 1) {
                displayList.add(new RareGet(getWindowWidth(), y));
            } else if (type < 9) {
                displayList.add(new Get(getWindowWidth(), y));
            } else {
                displayList.add(new Avoid(getWindowWidth(), y));
            }
        }
    }
    
    //Called once the game is over, performs any end-of-game operations
    protected void postgame(){
        if (player.getHP() <= 0)
            super.setTitleText("GAME OVER - You Lose!");
        else
            super.setTitleText("GAME OVER - You Won!");
    }
    
    //Determines if the game is over or not
    //Game can be over due to either a win or lose state
    protected boolean isGameOver(){
        
        if (player.getHP() <= 0){
            return true;
        }
        if (score >= SCORE_TO_WIN){
            return true;
        }
        return false;

    }
    
    //Reacts to a single key press on the keyboard
    protected void reactToKey(int key){
        
        //if a splash screen is up, only react to the advance splash key
        if (getSplashImage() != null){
            if (key == ADVANCE_SPLASH_KEY)
                super.setSplashImage(null);
            return;
        }
    
        if(key == UP_KEY){
            if (player.getY() > player.getMovementSpeed()){
                player.setY(player.getY() - player.getMovementSpeed());
            }
            else 
                player.setY(0);
        }
        if(key == LEFT_KEY){
            if (player.getX() > player.getMovementSpeed()){
                player.setX(player.getX() - player.getMovementSpeed());
            }
            else 
                player.setX(0);
        }
        if(key == DOWN_KEY){
            if (player.getY() < getWindowHeight() - player.getMovementSpeed() - player.getHeight()){
                player.setY(player.getY() + player.getMovementSpeed());
            }
            else 
                player.setY(getWindowHeight() - player.getHeight());
        }
        if(key == RIGHT_KEY){
            if (player.getX() < getWindowWidth() - player.getMovementSpeed() - player.getWidth()){
                player.setX(player.getX() + player.getMovementSpeed());
            }
            else 
                player.setX(getWindowWidth() - player.getWidth());
        }

        if(key == KEY_PAUSE_GAME){
            pauseGame();
        }

        if(key == SPEED_UP_KEY){
            speedUp();
        }

        if(key == SPEED_DOWN_KEY){
            slowDown();
        }




  }    

  protected void pauseGame(){
    if(isPaused){
        player.setMovementSpeed(savedSpeed);
        isPaused = false;
    }
    else{
        savedSpeed = player.getMovementSpeed();
        player.setMovementSpeed(0);
        isPaused = true;
    }
  }

  public void speedUp(){
    if (getGameSpeed() < MAX_GAME_SPEED){
        setGameSpeed(getGameSpeed()+SPEED_CHANGE_INTERVAL);
    }
  }

  public void slowDown(){
    if (getGameSpeed() > MIN_GAME_SPEED)
    setGameSpeed(getGameSpeed()-SPEED_CHANGE_INTERVAL);
  }
  
    
    
    //Handles reacting to a single mouse click in the game window
    protected MouseEvent reactToMouseClick(MouseEvent click){
       
        //Mouse functionality is not used at all in the Simple game...
        //you may want to override this function for a CreativeGame feature though!

        return click;//returns the mouse event for any child classes overriding this method
    }    
        
}
