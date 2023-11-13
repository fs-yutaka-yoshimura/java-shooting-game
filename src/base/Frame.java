package base;
import javax.swing.*;

public class Frame extends JFrame {

    public Panel panel;

    public Frame () {
        panel = new Panel();
        this.add(panel);

        // this.addWindowListener(new WindowAdapter() {
        //     @Override
        //     public void windowClosed(WindowEvent e) {
        //         super.windowClosed(e);
        //         Shooting.loop = true;
        //     }
        // });

        this.addKeyListener(new Keyboard());

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("シューティングゲーム");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
}