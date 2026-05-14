public class MovingPlatform extends Platform {
    int speed;
    int min;
    int max;
    String direction;
    int previousX;

    public MovingPlatform(int x, int y, int width, int height, int speed, int min, int max, String direction) {
        super(x, y, width, height);
        this.speed = speed;
        this.min = min;
        this.max = max;
        this.direction = direction;
    }

    public void update() {
        previousX = x;
        if (direction.equals("horizontal")) {
            x += speed;

            if (x <= min || x >= max - width) {
                speed = -speed;
            }  
        }

        if (direction.equals("vertical")) {
            y += speed;

            if (y <= min || y >= max - height) {
                speed = -speed;    
            }
           
        }
    }
}