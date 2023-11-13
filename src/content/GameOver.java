package content;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import base.Keyboard;
import enums.EnumScreen;

public class GameOver {

    private Graphics gra;
	private boolean isGameTitleFlg = false;
    
	// コンストラクタ
	public GameOver (Graphics gra) {
		this.gra = gra;
	}

    public void show (int score) {
		gra.setColor(Color.BLACK);
		Font font = new Font("SansSerif", Font.PLAIN, 50);
		FontMetrics metrics = gra.getFontMetrics(font);
		font = new Font("SansSerif", Font.PLAIN, 50);
		gra.setFont(font);
		metrics = gra.getFontMetrics(font);
		gra.drawString("Game Over", 250 - (metrics.stringWidth("Game Over") / 2), 100);
		font = new Font("SansSerif", Font.PLAIN, 20);
		gra.setFont(font);
		metrics = gra.getFontMetrics(font);
		gra.drawString("Score:" + score, 250 - (metrics.stringWidth("Score:" + score) / 2), 150);
		gra.drawString("Press ESC to Return Start Screen", 250 - (metrics.stringWidth("Press ESC to Return Start Screen") / 2), 200);
		return;
	}

    public void inputKey () {
		if (Keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            isGameTitleFlg = true;
		}
    }

    public EnumScreen getScreenState () {
        if (isGameTitleFlg) {
            isGameTitleFlg = false;
            return EnumScreen.GAME_TITLE;
        }
        return EnumScreen.GAME_OVER;
    }
}
