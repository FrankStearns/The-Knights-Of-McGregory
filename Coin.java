public class Coin extends Entity{

    private static int COIN_WIDTH = 40;
    private static int COIN_HEIGHT = 40;
    private static String COIN_IMAGE = "game_assets/coin.png";
    
    public Coin(int x, int y){
        super(x, y, COIN_WIDTH, COIN_HEIGHT, COIN_IMAGE);
    }

}
