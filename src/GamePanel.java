import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

public class GamePanel extends JPanel implements KeyListener {
    boolean titleScreen = true;
    JButton retryButton = new JButton("Retry");
    int spawnX = 40;
    int spawnY = 500;
    int playerX = spawnX;
    int playerY = spawnY;
    int playerWidth = 30;
    int playerHeight = 30;
    int velocityY = 0;
    int gravity = 1;
    boolean onGround = false;

    boolean leftPressed = false;
    boolean rightPressed = false;
    boolean paused = false;
    boolean jumpPressed = false;

    int score = 0;
    int highScore = 0;

    int playerSpeed = 6;
    int jumpStrength = -18;

    int spawnBlockWidth = 100;
    int spawnBlockHeight = 20;
    int spawnBlockCount = 5;

    int portalX = 0;
    int portalWidth = 30;
    int portalHeight = 100;

    int goalX = 1170;
    int goalY = 100;
    int goalWidth = 50;
    int goalHeight = 50;
    boolean gameWon = false;
    boolean gameLost = false;
   
    ArrayList<Platform> platforms = new ArrayList<>();
    ArrayList<MovingPlatform> movingPlatforms = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();

    GamePanel() {
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        platforms.add(new Platform(650, 70, 100, 20)); // p1
        platforms.add(new Platform(230, 150, 790, 20));
        platforms.add(new Platform(1080, 150, 140, 20));
        platforms.add(new Platform(180, 320, 700, 20));   // p4
        platforms.add(new Platform(1100, 450, 180, 20));   // p5
        platforms.add(new Platform(820, 540, 180, 20));   // p6


        movingPlatforms.add(new MovingPlatform(880, 320, 100, 20, 2, 880, 1165, "horizontal"));
        movingPlatforms.add(new MovingPlatform(50, 320, 100, 20, 2, 150, 360, "vertical"));        
       
        enemies.add(new Enemy(700, 105, 30, 30, 2, 600, 800)); //e1
        enemies.add(new Enemy(650, 315, 25, 10, 0, 200, 400)); //e2
        enemies.add(new Enemy(1000, 650, 80, 40, 0, 1000, 1000)); //e4
        enemies.add(new Enemy(300, 625, 40, 40, 1, 200, 400)); //e5

        setLayout(null);
        add(retryButton);

        retryButton.addActionListener(e -> {
            playerX = spawnX;
            playerY = spawnY;
            velocityY = 0;
            gameLost = false;
            gameWon = false;
            if (score > highScore) {
                highScore = score;
            }
            score = 0;  
            retryButton.setVisible(false);
        });

       
        Timer timer = new Timer(16, e -> {

            if (paused || gameWon || gameLost) {
                repaint();
                return;
            }

            if (leftPressed || rightPressed) {
                score++;
            }

            playerY += velocityY;
            velocityY += gravity;
            onGround = false;

            if (leftPressed) {
                playerX -= playerSpeed;     
            }
            if (rightPressed) {
                playerX += playerSpeed;
            }

            for ( MovingPlatform mp : movingPlatforms) {
                mp.update();
            }
           
            Rectangle playerRect = new Rectangle(playerX, playerY, playerWidth, playerHeight);

            for (Platform p : platforms) {
                Rectangle platformRect = new Rectangle(p.x, p.y, p.width, p.height);

                if (playerRect.intersects(platformRect)) {
                    
                    int playerBottom = playerY + playerHeight;
                    int playerRight = playerX + playerWidth;

                    int platformBottom = p.y + p.height;
                    int platformRight = p.x + p.width;

                    int overlapLeft = playerRight - p.x;
                    int overlapRight = platformRight - playerX;
                    int overlapTop = playerBottom - p.y;
                    int overlapBottom = platformBottom - playerY;

                    int smallestOverlap = Math.min(
                        Math.min(overlapLeft, overlapRight),
                        Math.min(overlapTop, overlapBottom)
                    );

                    if (smallestOverlap == overlapTop) {
                        playerY = p.y - playerHeight;
                        velocityY = 0;
                        onGround = true;
                    } else if (smallestOverlap == overlapBottom) {
                        playerY = p.y + p.height;
                        velocityY = 0;
                    } else if (smallestOverlap == overlapLeft) {
                        playerX = p.x - playerWidth;
                    } else if (smallestOverlap == overlapRight) {
                        playerX = p.x + p.width;
                    }

                    playerRect = new Rectangle(playerX, playerY, playerWidth, playerHeight);
                }
            }
                for (Enemy enemy : enemies) {   
                    enemy.update();

                    if (playerRect.intersects(enemy.getBounds())) {
                        gameLost = true;
                        retryButton.setBounds(getWidth()/2 - 60, getHeight()/2, 120, 40);
                        retryButton.setVisible(true);
                    }
                }
            

            for (MovingPlatform mp : movingPlatforms) {
            Rectangle platformRect = new Rectangle(mp.x, mp.y, mp.width, mp.height);

                if (playerRect.intersects(platformRect)) {
                    
                    int playerBottom = playerY + playerHeight;
                    int playerRight = playerX + playerWidth;

                    int platformBottom = mp.y + mp.height;
                    int platformRight = mp.x + mp.width;

                    int overlapLeft = playerRight - mp.x;
                    int overlapRight = platformRight - playerX;
                    int overlapTop = playerBottom - mp.y;
                    int overlapBottom = platformBottom - playerY;

                    int smallestOverlap = Math.min(
                        Math.min(overlapLeft, overlapRight),
                        Math.min(overlapTop, overlapBottom)
                    );

                    if (smallestOverlap == overlapTop) {
                        playerY = mp.y - playerHeight;
                        velocityY = 0;
                        onGround = true;
                        playerX += mp.x- mp.previousX;
                    }
                    else if (smallestOverlap == overlapBottom) {
                        playerY = mp.y + mp.height;
                        velocityY = 0;
                    } else if (smallestOverlap == overlapLeft) {
                        playerX = mp.x - playerWidth;
                    } else if (smallestOverlap == overlapRight) {
                        playerX = mp.x + mp.width;
                    }

                    playerRect = new Rectangle(playerX, playerY, playerWidth, playerHeight);
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

                if (score > highScore) {
                    highScore = score;  
                }
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
            if (jumpPressed && onGround) {
                velocityY = jumpStrength;
                onGround = false;
            }
            jumpPressed = false;
           
            repaint();
        });

        timer.start();
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);

        if (titleScreen) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.drawString("GRAVEBOUND", getWidth() / 2 - 220, getHeight() / 2 - 50);

            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Press ENTER to Start", getWidth() / 2 - 150, getHeight() / 2 + 30);
            g.setFont(new Font("Arial", Font.PLAIN, 22));
            g.drawString("SPACE= Jump  LEFT/RIGHT= Move", getWidth() / 2 - 190, getHeight() / 2 + 80);
            g.drawString("P = Pause  R = Restart", getWidth() / 2 - 120, getHeight() / 2 + 115);

            return;
        }

        g.setColor(Color.GRAY);
        for (Platform p : platforms) {
            g.fillRect(p.x, p.y, p.width, p.height);
        }

        for (MovingPlatform mp : movingPlatforms) {
            g.fillRect(mp.x, mp.y, mp.width, mp.height);
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
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("YOU WON!", getWidth()/2 - 230, getHeight()/2 - 80);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("High Score: " + highScore, getWidth()/2 - 120, getHeight()/2);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to play again", getWidth()/2 - 110, getHeight()/2 + 50);
        }
        if (paused) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("PAUSED", getWidth()/2 - 100, getHeight()/2);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);
        g.drawString("High Score: " + highScore, 20, 60);
        
        if(gameLost) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("YOU DIED!", getWidth()/2 - 150, getHeight()/2 - 80);
        }
    }
        
    

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
        }
        if (titleScreen && e.getKeyCode() == KeyEvent.VK_ENTER) {
            titleScreen = false;
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            playerX = spawnX;
            playerY = spawnY;
            velocityY = 0;
            gameWon = false;
            gameLost = false;
            if (score > highScore) {
                highScore = score;
            }
            score = 0;
            retryButton.setVisible(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jumpPressed = true;
        }
        repaint();
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
