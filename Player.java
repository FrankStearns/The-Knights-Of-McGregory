//The entity that the human player controls in the game window
//The player moves in reaction to player input
public class Player extends Entity {
   
   //Location of image file to be drawn for a Player
   public static final String PLAYER_IMAGE_FILE = "game_assets/player.gif";
   //Dimensions of the Player
   public static final int PLAYER_WIDTH = 75;
   public static final int PLAYER_HEIGHT = 75;
   //Default speed that the Player moves (in pixels) each time the user moves it
   public static final int DEFAULT_MOVEMENT_SPEED = 7;
   //Starting hit points
   public static final int STARTING_HP = 3;
   
   //Current movement speed
   private int movementSpeed;
   //Remaining Hit Points (HP) -- indicates the number of "hits" (ie collisions
   //with Avoids) that the player can take before the game is over
   private int hp;
   
   
   
   public Player(){
      this(0, 0);        
   }
   
   public Player(int x, int y){
      super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_IMAGE_FILE);  
      this.hp = STARTING_HP;
      this.movementSpeed = DEFAULT_MOVEMENT_SPEED;
   }

   public Player(int x, int y, String imageFileName){
      super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT, imageFileName);
      this.hp = STARTING_HP;
      this.movementSpeed = DEFAULT_MOVEMENT_SPEED;
   }

   public Player(int x, int y, int width, int height, String imageFileName){
      super(x, y, width, height, imageFileName);
      this.hp = STARTING_HP;
      this.movementSpeed = DEFAULT_MOVEMENT_SPEED;
   }
   
   
   //Retrieve and set the Player's current movement speed 
   //movement speed is the numebr of pixels the Player Entity
   //moves when an arrow key is pressed
   public int getMovementSpeed(){
       //implement me!
       return movementSpeed;
   }
   
   public void setMovementSpeed(int newSpeed){
      this.movementSpeed = newSpeed;
   }  
   
   
   //Retrieve the Player's current HP
   public int getHP(){
      return hp;
   }
   
   
   //Set the player's HP to a specific value.
   //Returns an boolean indicating if Player still has HP remaining
   public boolean setHP(int newHP){
      this.hp = newHP;
      return (this.hp > 0);
   }
   
   //Set the player's HP to a specific value.
   //Returns an boolean indicating if Player still has HP remaining
   public boolean modifyHP(int delta){
      this.hp += delta;
      return (this.hp > 0);
   }    
   
   // Returns the players score
   public int getScore(){
      return 0;
   }

   public void setImageName(String imageFileName){
      super.setImageName(imageFileName);
   }

   public String getImagename(){
      return super.getImageName();
   }
   
   
   
   
   
   
}
