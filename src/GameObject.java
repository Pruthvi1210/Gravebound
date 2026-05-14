import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject
{
protected int x;
protected int y;
protected int width;
protected int height;

public GameObject(int x, int y, int width, int height)
{
this.x = x;
this.y = y;
this.width = width;
this.height = height;
}
public Rectangle getBounds()
{
    return new Rectangle(x, y, width, height);
}
public void draw(Graphics g)
{

}
public void update()
{
   
}

}