import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends GameObject
{
    private double speed;
    private int leftLimit;
    private int rightLimit;
   
    public Enemy(int x, int y, int width, int height, double speed, int leftLimit, int rightLimit)
    {
        super(x, y, width, height);
        this.speed = speed;
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }
    @Override
    public void update()
    {
        x += speed;
   
        if (x <= leftLimit || x >= rightLimit - width)
        {
            speed = -speed;
        }
    }
    @Override
    public void draw(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

}
