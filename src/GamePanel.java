import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class GamePanel extends JPanel implements KeyListener {
    int playerX = 40;
    int playerY = 500;
    int playerWidth = 50;
    int playerHeight = 50;
    int velocityY = 0;
    int gravity = 1;
    boolean onGround = false;

    int spawnBlockWidth = 100;
    int spawnBlockHeight = 20;
    int spawnBlockCount = 9;

    int portalX = 0;
    int portalWidth = 30;
    int portalHeight = 100;

    int goalX = 900;
    int goalY = 250;
    int goalWidth = 50;
    int goalHeight = 50;
    boolean gameWon = false;
    boolean gameLost = false;
   
    ArrayList<Platform> platforms = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();

    GamePanel() {
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        platforms.add(new Platform(500, 450, 200, 20));
        platforms.add(new Platform(500, 350, 200, 20));
        platforms.add(new Platform(500, 250, 200, 20));
        
        enemies.add(new Enemy(300, 400, 40, 40, 2, 200, 400)); 
        
        Timer timer = new Timer(16, e -> {

            if (gameWon || gameLost) {
                repaint();
                return;
            }

            playerY += velocityY;
            velocityY += gravity;
            onGround = false;
            
            Rectangle playerRect = new Rectangle(playerX, playerY, playerWidth, playerHeight);

            for (Platform p : platforms) {
                Rectangle platformRect = new Rectangle(p.x, p.y, p.width, p.height);

                if (playerRect.intersects(platformRect)) {
                    if (velocityY > 0 && playerY + playerHeight - velocityY <= p.y) {
                        playerY = p.y - playerHeight;
                        velocityY= 0;
                        onGround= true;     
                    }
                    
                }

                for (Enemy enemy : enemies) {
                    enemy.update();

                    if (playerRect.intersects(enemy.getBounds())) {
                        gameLost = true;  
                    }
                }
            }
            if (playerX < 0) {
                playerX = 0;  
            }
            if (playerX + playerWidth > getWidth()) {
                playerX = getWidth() - playerWidth;  
            }
            if (playerY < 0) {
                playerY = 0;
            }
            if (playerY + playerHeight > getHeight()) {
                playerY = getHeight() - playerHeight;
            }

            Rectangle goalRect = new Rectangle(goalX, goalY, goalWidth, goalHeight);

            if (playerRect.intersects(goalRect)) {
                gameWon = true;
            }
            if (playerY + playerHeight >= getHeight() - 20) {
                playerY = getHeight() - 20 - playerHeight;
                velocityY = 0;
                onGround = true;  
            }
            int spawnPlatformTop = getHeight() - 20 - (spawnBlockCount * spawnBlockHeight);

            if (playerX + playerWidth > 0 &&
                playerX < spawnBlockWidth &&
                playerY + playerHeight >= spawnPlatformTop &&
                playerY + playerHeight <= spawnPlatformTop + 20 &&
                velocityY >= 0) {

                playerY = spawnPlatformTop - playerHeight;
                velocityY = 0;
                onGround = true;
            }

            int spawnTop = getHeight() - 20 - (spawnBlockCount * spawnBlockHeight);

            Rectangle playerRect2 = new Rectangle(playerX, playerY, playerWidth, playerHeight);
            Rectangle spawnBlockRect = new Rectangle(0, spawnTop, spawnBlockWidth, spawnBlockCount * spawnBlockHeight);

            if (playerRect2.intersects(spawnBlockRect)) {
                playerY= spawnTop - playerHeight;
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

        g.setColor(Color.GRAY);
        for (Platform p : platforms) {
            g.fillRect(p.x, p.y, p.width, p.height);
        }
        
        g.setColor(Color.GRAY);
        g.fillRect(0, getHeight() - 20, getWidth(), 20);

        for (int i = 0; i < spawnBlockCount; i++) {
            g.fillRect(0, getHeight() - 20 - ((i + 1) * spawnBlockHeight),
                spawnBlockWidth, spawnBlockHeight);
        }

        int spawnTop = getHeight() - 20 - (spawnBlockCount * spawnBlockHeight);
        g.setColor(Color.MAGENTA);
        g.fillRect(portalX, spawnTop - portalHeight, portalWidth, portalHeight);

        g.setColor(Color.BLUE);
        g.fillRect(playerX, playerY, playerWidth, playerHeight);

         for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        g.setColor(Color.GREEN);
        g.fillRect(goalX, goalY, goalWidth, goalHeight);

        g.setColor(Color.WHITE);
        if (gameWon) {
            g.drawString("YOU WIN!", 50, 50);
        }
        if (gameLost) {
            g.drawString("YOU LOSE! Press R to restart", 50, 50);
        }
        }
    

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_R) {
            playerX = 350;
            playerY = 250;
            velocityY = 0;
            gameWon = false;
            gameLost = false;
        }
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