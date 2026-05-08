import javax.swing.Timer;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class GamePanel extends JPanel implements KeyListener {
    int playerX = 350;
    int playerY = 250;
    int playerWidth = 80;
    int playerHeight = 80;
    int velocityY = 0;
    int gravity = 1;
    boolean onGround = false;
   
    GamePanel() {
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        Timer timer = new Timer(16, e -> {
            playerY += velocityY;
            velocityY += gravity;

            if (playerY >= 420) {
                playerY = 420;
                velocityY = 0;
                onGround = true;
            }
            
            repaint();
        });

        timer.start();
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.fillRect(playerX, playerY, playerWidth, playerHeight);
        g.setColor(Color.GRAY);
        g.fillRect(200, 450, 200, 20);
        g.fillRect(500, 350, 200, 20);
        g.fillRect(100, 250, 200, 20);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            playerX -= 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            playerX += 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE && onGround) {
            velocityY = -15;
            onGround = false;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}