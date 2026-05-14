import javax.swing.JFrame;
public class GameFrame extends JFrame
{
    GameFrame()
    {
        this.setTitle("Gravebound");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(1280, 720);

        this.setResizable(false);

        this.add(new GamePanel());

        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }
}
