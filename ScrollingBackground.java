//Gets are entities that the player wants to collide with, as they increase
//their score.
public class ScrollingBackground extends Entity implements Scrollable {
    
    //Location of image file to be drawn for a Get
    public static final String SB_IMAGE_FILE = "game_assets/castle_background.gif";
    //Dimensions of the Get  
    public static final int SB_WIDTH = 900;
    public static final int SB_HEIGHT = 600;
    //Speed that the Get moves (in pixels) each time the game scrolls
    public static final int SB_DEFAULT_SCROLL_SPEED = 5;
    //Amount of points received when player collides with a Get
    public static final int SB_POINT_VALUE = 20;
    
    private int scrollSpeed = SB_DEFAULT_SCROLL_SPEED;
    
    public ScrollingBackground(){
        this(0, 0);        
    }
    
    public ScrollingBackground(int x, int y){
        super(x, y, SB_WIDTH, SB_HEIGHT, SB_IMAGE_FILE);  
    }
    
    public ScrollingBackground(int x, int y, String imageFileName){
        super(x, y, SB_WIDTH, SB_HEIGHT, imageFileName);
    }
    
    public int getScrollSpeed(){
        return this.scrollSpeed;
    }
    
    //Sets the scroll speed to the argument amount
    public void setScrollSpeed(int newSpeed){
       this.scrollSpeed = newSpeed;
    }
    
    //Move the Get left by its scroll speed
    public void scroll(){
       //implement me!
         this.setX(this.getX() - this.scrollSpeed);
    }
    
    //Colliding with a Get increases the player's score by the specified amount
    public int getPoints(){
        return SB_POINT_VALUE;
    }
    
    //Colliding with a Get does not affect the player's HP
    public int getDamage(){
        return 0;
    }
    
}
