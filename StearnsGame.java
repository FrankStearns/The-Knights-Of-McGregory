import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class StearnsGame extends ScrollingGame {

    private String PLAYER_IMAGE_FILE = "game_assets/knight_running.gif";
    private String PLAYER_JUMP_FILE = "game_assets/knight_jump.gif";
    private String PLAYER_FALL_FILE = "game_assets/knight_fall.gif";
    private String PLAYER_IDLE_FILE = "game_assets/knight_idle.gif";
    private String PLAYER_ATTACK_FILE = "game_assets/knight_attack.gif";
    private String PLAYER_DEATH_FILE = "game_assets/knight_death.gif";
    private String INTRO_SPLASH_FILE = "game_assets/castle_splash.gif";
    private String CHANDELIER_FILE = "game_assets/chandelier.gif";
    private String TABLE_FILE = "game_assets/table.png";
    private String DEATH_SCREEN_FILE = "game_assets/You_Died.png";
    private String COIN_FILE = "game_assets/coin.png";
    private String DEAD_KNIGHT_FILE = "game_assets/dead_knight.png";
    private String CLOCK_FILE = "game_assets/clock.png";
    private String SKELETON_FILE = "game_assets/skeleton.gif";
    private String EMPTY_FILE = "game_assets/Empty.png";
    private String BAT_FILE = "game_assets/bat.gif";
    private String WIN_SCREEN_FILE = "game_assets/you_win.gif";

    public static final int SPACE_BAR = KeyEvent.VK_SPACE;
    private static final int STARTING_PLAYER_X = 0;
    private static int STARTING_PLAYER_Y = 400;
    private static final int STARTING_PLAYER_HEIGHT = 150;
    private static final int STARTING_PLAYER_WIDTH = 150;
    private static final int ATTACKING_PLAYER_HEIGHT = 236;
    private static final int ATTACKING_PLAYER_WIDTH = 236;
    private static final int ATTACKING_PLAYER_Y = 350;

    private static final int CHANDELIER_HEIGHT = 150;
    private static final int CHANDELIER_WIDTH = 150;

    private static final int SCORE_TO_WIN = 160;


    public static boolean isJumping = false;
    public static boolean isDoubleJumping = false;
    public static int jumpTime = 0;
    public static int jumpVelocity = 18;
    public static int doubleJumpVelocity = 18;

    public static boolean isAttacking = false;
    public static int attackCooldown = 50;
    public static int attackFrames = 20;

    private static int SPAWN_INTERVAL = 600/3;

    private static int collidingWithHeight = 0;
    private static boolean isColliding = false;

    private static int savedTicks = 0;

    private static int invulnerableTime = 100;
    private static boolean invulnerable = false;

    protected Heart heart1 = new Heart(10, 10);
    protected Heart heart2 = new Heart(60, 10);
    protected Heart heart3 = new Heart(110, 10);

    protected int coinX = 700;


    Random ran = new Random();


    StearnsGame(){
        super();
    }

    StearnsGame(int windowWidth, int windowHeight){
        super(windowWidth, windowHeight);
    }

    protected void pregame(){
        this.setBackgroundColor(Color.BLACK);
        player = new Player(STARTING_PLAYER_X, STARTING_PLAYER_Y, STARTING_PLAYER_WIDTH, STARTING_PLAYER_HEIGHT, PLAYER_IMAGE_FILE);
        displayList.add(new ScrollingBackground(0, 0));
        displayList.add(player); 
        displayList.add(heart1);
        displayList.add(heart2);
        displayList.add(heart3);
        score = 0;
        this.setSplashImage(INTRO_SPLASH_FILE);
        setTitleText("The Knights of McGregory"); 
    }


    protected void updateGame(){
        //scroll all scrollable Entities on the game board
        scrollEntities();   
        //Spawn new entities only at a certain interval
        if (super.getTicksElapsed() % SPAWN_INTERVAL == 0){
            spawnEntities();
        }
        if(super.getTicksElapsed() % (getWindowWidth()/5) == 0)
        {
            displayList.add(0, new ScrollingBackground(getWindowWidth(), 0));
        }
        //Update the title text on the top of the window
        setTitleText("HP: " + player.getHP() + ", Score: " + score);
        //checks if the player has collided with any Consumables
        collidingWithHeight = 0;
        Collection<Entity> collidingEntities = findCollisions(player);
        if(collidingEntities.size() == 0)
            collidingWithHeight = 0;
        for (Entity e : collidingEntities){
            if (e instanceof Consumable){
                handlePlayerCollision((Consumable)e);
            }
            else if (e instanceof Collidable){
                handlePlayerCollision((Collidable)e);
            }
        }
        jump();
        doubleJump();
        attack();
        correctPlayerHeight();
        UpdateHearts();
        Invulnerability();
    }

    protected void handlePlayerCollision(Consumable collidedWith){

        if (collidedWith instanceof RareGet){
            score += collidedWith.getPoints();
            player.setHP(player.getHP() + 1);
            toBeGC.add((Entity) collidedWith);
        }
        else if (collidedWith instanceof Get){
            score += collidedWith.getPoints();
            toBeGC.add((Entity) collidedWith);
            displayList.add(new Coin(coinX, 10));
            coinX += 10;
        }
        if (player.getImageName() == PLAYER_ATTACK_FILE){
            int x = collidedWith.getX() + collidedWith.getWidth()/2;
            int y = collidedWith.getY() + collidedWith.getHeight()/2;
            coinSpawnBuffer(x, y);
            toBeGC.add((Entity) collidedWith);
        }
        else{
        if (collidedWith instanceof Avoid && !invulnerable){
            player.setHP(player.getHP() + collidedWith.getDamage());
            invulnerable = true;
        }
        }

    }

    protected void handlePlayerCollision(Collidable collidedWith){
        collidingWithHeight = collidedWith.getHeight() - 15;
        isColliding = false;
        if (player.getY() + player.getHeight() > collidedWith.getY() + 15 && player.getX() + player.getWidth() <= collidedWith.getX() + 20 &&!invulnerable){
            player.setX(collidedWith.getX() - player.getWidth());
            collidingWithHeight = 0;
            isColliding = true;
        }
        else if (player.getX() > collidedWith.getX() + collidedWith.getWidth() - 50){
            isJumping = true;
            collidingWithHeight = 0;
            jumpVelocity = 0;
            jump();
        }
        else if(player.getY() + player.getHeight() - 15 > collidedWith.getY() && (!isJumping) && player.getX() + player.getWidth() > collidedWith.getX() + 20){
            if (player.getImageName() != PLAYER_ATTACK_FILE){
                player.setY(collidedWith.getY() - player.getHeight() + 15);
                if (!isAttacking)
                    player.setImageName(PLAYER_IMAGE_FILE);
            }
        }
        else if (player.getY() < collidedWith.getY() + collidedWith.getHeight() - 50 && player.getY() > collidedWith.getY() + collidedWith.getHeight() - 75){
            if (jumpVelocity > 0 || doubleJumpVelocity > 0){
                jumpVelocity = 0;
                doubleJumpVelocity = 0;
            }
        }
        if (player.getX() < -player.getWidth()/2 && !invulnerable){
            player.setHP(player.getHP() - 1);
            invulnerable = true;
        }

     }

    protected boolean isGameOver(){
        
        if (player.getHP() <= 0 && player.getY() == STARTING_PLAYER_Y - collidingWithHeight){
            return true;
        }
        if (score >= SCORE_TO_WIN){
            return true;
        }
        return false;

    }

    protected void UpdateHearts(){
        if (player.getHP() == 2){
            heart3.emptyHeart();
        }
        else if (player.getHP() == 1){
            heart2.emptyHeart();
        }
        else if (player.getHP() == 0){
            heart1.emptyHeart();
        }
    }

    protected void Invulnerability(){
        if (invulnerable){
            if (invulnerableTime > 0){
                invulnerableTime--;
                System.out.println(invulnerableTime);
                if (invulnerableTime % 10 == 0){
                    player.setImageName(EMPTY_FILE);
                }
                else if (invulnerableTime % 5 == 0){
                    player.setImageName(PLAYER_IMAGE_FILE);
                }
            }
            else{
                player.setImageName(PLAYER_IMAGE_FILE);
                invulnerableTime = 100;
                invulnerable = false;
            }
    }
    }

    protected void coinSpawnBuffer(int x, int y){
        if (getTicksElapsed() - savedTicks > 20){
            displayList.add(new Get(x, y, 30, 30, COIN_FILE));
            savedTicks = getTicksElapsed();
        }

    }


    protected void spawnEntities(){
        ran = new Random();
        int type = ran.nextInt(6);
        if (type == 0)
            spawnEnviroment1();
        else if (type == 1)
            spawnEnviroment2();
        else if (type == 2)
            spawnEnviroment3();
        else if (type == 3)
            spawnEnemy1();
        else if (type == 4)
            spawnEnemy2();
        else if (type == 5)
            spawnEnemy3();
        
    }

    protected void spawnEnviroment1(){
        displayList.add(new Enviroment(getWindowWidth() + 88, 55, CHANDELIER_WIDTH, CHANDELIER_HEIGHT, CHANDELIER_FILE));
        displayList.add(new Enviroment(getWindowWidth(), 400, 300, 150, TABLE_FILE));
    }

    protected void spawnEnviroment2(){
        displayList.add(new Enviroment(getWindowWidth(), 450, 236, 132, DEAD_KNIGHT_FILE));
    }

    protected void spawnEnviroment3(){
        displayList.add(new Enviroment(getWindowWidth(), 245, 100, 300, CLOCK_FILE));
    }

    protected void spawnEnemy1(){
        displayList.add(new Avoid(getWindowWidth(), 375, 150, 175, SKELETON_FILE));
    }

    protected void spawnEnemy2(){
        displayList.add(new Avoid(getWindowWidth(), 100, 100, 100, BAT_FILE));
    }

    protected void spawnEnemy3(){
        displayList.add(new FlyingSkeleton(getWindowWidth(), 100));
    }

    protected void postgame(){
        super.postgame();
        if (player.getHP() <= 0){
            player.setHeight(ATTACKING_PLAYER_HEIGHT);
            player.setWidth(ATTACKING_PLAYER_WIDTH);
            player.setY(player.getY() - 50);
            player.setX(player.getX() - 50);
            player.setImageName(PLAYER_DEATH_FILE);
            this.setSplashImage(DEATH_SCREEN_FILE);
        }
        else if (player.getHP() > 0){
            this.setSplashImage(WIN_SCREEN_FILE);

        }
    }

    protected void reactToKey(int key){
        
        //if a splash screen is up, only react to the advance splash key
        if (getSplashImage() != null){
            if (key == ADVANCE_SPLASH_KEY)
                super.setSplashImage(null);
            return;
        }
        if(key == LEFT_KEY){
            if (player.getX() > player.getMovementSpeed()){
                player.setX(player.getX() - player.getMovementSpeed());
            }
            else 
                player.setX(0);
        }
        if(key == RIGHT_KEY){
            if (!isColliding){
                if (player.getX() < getWindowWidth() - player.getMovementSpeed() - player.getWidth()){
                   player.setX(player.getX() + player.getMovementSpeed());
                }
                else 
                    player.setX(getWindowWidth() - player.getWidth());
                }
            }
        if(key == UP_KEY ){
            if(!isJumping && !isAttacking){
                isJumping = true;
                player.setImageName(PLAYER_JUMP_FILE);
            }
            else if(!isDoubleJumping && jumpTime > 10 && !isAttacking){
                isDoubleJumping = true;
                player.setImageName(PLAYER_JUMP_FILE);
            }
        }

        if(key == SPACE_BAR)
        {
            if (!isAttacking && !isColliding)
            {
                isAttacking = true;
                player.setHeight(ATTACKING_PLAYER_HEIGHT);
                player.setWidth(ATTACKING_PLAYER_WIDTH);
                player.setY(player.getY() - 50);
                player.setX(player.getX() - 50);
                player.setImageName(PLAYER_ATTACK_FILE);
                attack();
            }
        }

        if(key == KEY_PAUSE_GAME){
            pauseGame();
        }
  }    

    protected void jump(){
        if(isJumping && !isDoubleJumping){
            if((player.getY() - jumpVelocity) >= STARTING_PLAYER_Y - collidingWithHeight && jumpVelocity < 0 && !isColliding){
                player.setY(STARTING_PLAYER_Y - collidingWithHeight + 30);
            }
            else
                player.setY(player.getY() - jumpVelocity);
            jumpVelocity--;
            if (jumpVelocity < 0 && !isAttacking)
                player.setImageName(PLAYER_FALL_FILE);
            jumpTime++;
            if(player.getY() >= STARTING_PLAYER_Y - collidingWithHeight && jumpVelocity < 0){
                isJumping = false;
                jumpVelocity = 18; 
                jumpTime = 0;
                if (!isAttacking)
                    player.setImageName(PLAYER_IMAGE_FILE);
            }
        }
    }

    protected void doubleJump(){
        if(isDoubleJumping){
            if((player.getY() - doubleJumpVelocity) >= STARTING_PLAYER_Y - collidingWithHeight && doubleJumpVelocity < 0){
                player.setY(STARTING_PLAYER_Y - collidingWithHeight + 30);
            }
            else
                player.setY(player.getY() - doubleJumpVelocity);
            doubleJumpVelocity--;
            if (doubleJumpVelocity < 0 && !isAttacking)
                player.setImageName(PLAYER_FALL_FILE);
            if(player.getY() >= STARTING_PLAYER_Y - collidingWithHeight && doubleJumpVelocity < 0){
                isDoubleJumping = false;
                doubleJumpVelocity = 18; 
                jumpTime = 0;
                if (!isAttacking)
                    player.setImageName(PLAYER_IMAGE_FILE);
            }
        }
    }

    protected void attack(){
        if(isAttacking){
            attackFrames--;
            attackCooldown--;
            if(attackFrames == 0){
                player.setHeight(150);
                player.setWidth(150);
                player.setY(player.getY() + 50);
                player.setX(player.getX() + 50);
                player.setImageName(PLAYER_IMAGE_FILE);
            }
            if(attackCooldown == 0){
                attackFrames = 20;
                attackCooldown = 50;
                isAttacking = false;
            }
        }
    }

    protected void pauseGame(){
        super.pauseGame();
        if(isPaused){
            player.setImageName(PLAYER_IDLE_FILE);
        }
        else{
            player.setImageName(PLAYER_IMAGE_FILE);
        }
      }

    protected void correctPlayerHeight(){
        if(player.getY() > STARTING_PLAYER_Y && player.getImageName() != PLAYER_ATTACK_FILE){
            player.setY(STARTING_PLAYER_Y);
        }
        else if (player.getY() > ATTACKING_PLAYER_Y && player.getImageName() == PLAYER_ATTACK_FILE){
            player.setY(ATTACKING_PLAYER_Y);
        }
        if(player.getY() < STARTING_PLAYER_Y - collidingWithHeight && !isJumping && !isDoubleJumping){
            jumpVelocity = 0;
            jump();
        }

    }

    public static int getPlayerY(){
        return STARTING_PLAYER_Y;
    }
    

}