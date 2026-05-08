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
   
    GamePanel() {
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.fillRect(playerX, playerY, playerWidth, playerHeight);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            playerX -= 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            playerX += 10;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}