package content;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import base.Keyboard;
import enums.EnumScreen;

public class GameTitle {

    private boolean isPlayFlg = false;
	private Graphics gra;

	// コンストラクタ
	public GameTitle (Graphics gra) {
		this.gra = gra;
	}
    
    public void show () {
		gra.setColor(Color.BLACK);
		Font font = new Font("SansSerif", Font.PLAIN, 50);
		gra.setFont(font);
		FontMetrics metrics = gra.getFontMetrics(font);
		gra.drawString("ShootingGame", 250 - (metrics.stringWidth("ShootingGame") / 2), 100);
		font = new Font("SansSerif", Font.PLAIN, 20);
		gra.setFont(font);
		metrics = gra.getFontMetrics(font);
		gra.drawString("Press SPACE to Start", 250 - (metrics.stringWidth("Press SPACE to Start") / 2), 150);
        return;
    }

    public void inputKey () {
		if (Keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
            isPlayFlg = true;
		}
    }

    public EnumScreen getScreenState () {
        if (isPlayFlg) {
            isPlayFlg = false;
            return EnumScreen.GAME_PLAY;
        }
        return EnumScreen.GAME_TITLE;
    }
}
