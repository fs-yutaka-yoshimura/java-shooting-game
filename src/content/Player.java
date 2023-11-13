package content;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Player {
	private Graphics gra;

	private int playerX;
    private int playerY;
	private int bulletIntraval;
	private ArrayList<Bullet> playerBulletList;
    private boolean isDead;
    private boolean isInit;

	// コンストラクタ
	public Player (Graphics gra) {
		this.gra = gra;
	}

    public void init() {
        playerX = 235;
        playerY = 430;
        bulletIntraval = 0;
        playerBulletList = new ArrayList<>();
        isInit = false;
    }

    public void show () {
        gra.setColor(Color.BLUE);
		gra.fillRect(playerX + 10, playerY , 10, 10);
		gra.fillRect(playerX, playerY + 10, 30, 10);
    }
}