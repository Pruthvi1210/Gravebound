import javax.swing.JFrame;

public class GameFrame extends JFrame {
    GameFrame() {
        this.setTitle("Gravebound");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.add(new GamePanel());

        this.setVisible(true);
    }
}
