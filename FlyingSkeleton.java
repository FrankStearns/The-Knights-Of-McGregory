public class FlyingSkeleton extends Avoid{

    public static final String FLYING_SKELETON_FILE = "game_assets/flying_skeleton.gif";

    public static final int FLYING_SKELETON_WIDTH = 300;
    public static final int FLYING_SKELETON_HEIGHT = 300;

    public static final int FLYING_SKELETON_DEFAULT_SCROLL_SPEED = 3;

    private int scrollSpeed = FLYING_SKELETON_DEFAULT_SCROLL_SPEED;

    public FlyingSkeleton(){
        this(0, 0);        
    }

    public FlyingSkeleton(int x, int y){
        super(x, y, FLYING_SKELETON_WIDTH, FLYING_SKELETON_HEIGHT, FLYING_SKELETON_FILE);  
    }

    public void scroll(){
        setX(getX() - this.scrollSpeed);
        if(StearnsGame.getPlayerY() < getY() + FLYING_SKELETON_HEIGHT/2)
            setY(getY() - 1);
        else if(StearnsGame.getPlayerY() > getY() + FLYING_SKELETON_HEIGHT/2)
            setY(getY() + 1);
    }

}