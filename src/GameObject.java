
import java.awt.Rectangle; 

public class GameObject
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


}