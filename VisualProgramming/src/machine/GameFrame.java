package machine;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GameFrame extends JFrame {
    private static final long serialVersionUID = -7562412320398374620L;

    public static void main(final String[] args) {
        setPLAF("Nimbus");
        new GameFrame();
    }

    public GameFrame() {
        super();
        final GamePanel panel = new GamePanel();
        this.add(panel);
        panel.startThread();

        setPreferredSize(panel.getPreferredSize());
        setSize(getPreferredSize());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private static void setPLAF(final String string) {
        try {
            for (final UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
