public class Heart extends Entity{

    private static int HEART_WIDTH = 40;
    private static int HEART_HEIGHT = 40;
    private static String HEART_IMAGE = "game_assets/full_heart.png";
    private static String HEART_IMAGE_EMPTY = "game_assets/empty_heart.png";
    
    public Heart(int x, int y){
        super(x, y, HEART_WIDTH, HEART_HEIGHT, HEART_IMAGE);
    }

    public void emptyHeart(){
        this.setImageName(HEART_IMAGE_EMPTY);
    }
}
