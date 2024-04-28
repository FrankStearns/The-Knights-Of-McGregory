//Avoids are entities the player needs to avoid colliding with.
//If a player collides with an avoid, it reduces the players Hit Points (HP).
public class Enviroment extends Entity implements Scrollable, Collidable{
    
    //Location of image file to be drawn for an Avoid
    public static final String AVOID_IMAGE_FILE = "game_assets/avoid.gif";
    //Dimensions of the Avoid    
    public static final int AVOID_WIDTH = 75;
    public static final int AVOID_HEIGHT = 75;
    //Speed that the avoid moves each time the game scrolls
    public static final int AVOID_DEFAULT_SCROLL_SPEED = 5;

    private int scrollSpeed = AVOID_DEFAULT_SCROLL_SPEED;
        
    public Enviroment(){
        this(0, 0);        
    }
    
    public Enviroment(int x, int y){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, AVOID_IMAGE_FILE);  
    }

    public Enviroment(int x, int y, String imageFileName){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, imageFileName);
    }

    public Enviroment(int x, int y, int width, int height, String imageFileName){
        super(x, y, width, height, imageFileName);
    }
    
    
    public int getScrollSpeed(){
        return this.scrollSpeed;
    }
    
    //Sets the scroll speed to the argument amount
    public void setScrollSpeed(int newSpeed){
       this.scrollSpeed = newSpeed;
    }
    
    //Move the avoid left by the scroll speed
    public void scroll(){
        setX(getX() - this.scrollSpeed);
    }
    
    //Colliding with an Avoid does not affect the player's score
    public int getPoints(){
       //implement me!
        return 0;
    }
    
    //Colliding with an Avoid Reduces players HP by 1
    public int getDamage(){
        return -1;
    }

    public int getY(){
        return super.getY();
    }

    public int getX(){
        return super.getX();
    }

    public int getWidth(){
        return super.getWidth();
    }

    public int getHeight(){
        return super.getHeight();
    }

    
}
