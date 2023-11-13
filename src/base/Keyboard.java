package base;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Keyboard extends KeyAdapter {

    private static ArrayList<Integer> pressedButtonList = new ArrayList<>();

    public static boolean isKeyPressed(int keyCode) {
        return pressedButtonList.contains(keyCode);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if (! pressedButtonList.contains(e.getKeyCode())){
            pressedButtonList.add(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        pressedButtonList.remove((Integer) e.getKeyCode());
    }
}
