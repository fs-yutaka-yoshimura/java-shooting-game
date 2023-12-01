package content;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import base.Keyboard;

public class Player implements ICharacter, IAttackable {
	private Graphics gra;

	public int x;
    public int y;
	public int bulletIntraval;
	public ArrayList<Bullet> playerBulletList;
    public boolean isDead;
    public boolean isInit;

	// コンストラクタ
	public Player (Graphics gra) {
		this.gra = gra;
	}

    public void init() {
        x = 235;
        y = 430;
        bulletIntraval = 0;
        playerBulletList = new ArrayList<>();
        isInit = false;
    }

    public void show () {
        gra.setColor(Color.BLUE);
		gra.fillRect(x + 10, y , 10, 10);
		gra.fillRect(x, y + 10, 30, 10);
    }

    public void inputKey() {
        // プレイヤー移動
		if (Keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
		    if (x > 0) {
		        x -= 5;
		    }
		}
		if (Keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
		    if (x < 460) {
		        x += 5;
		    }
		}
		if (Keyboard.isKeyPressed(KeyEvent.VK_UP)) {
		    if (y > 0) {
		        y -= 5;
		    }
		}
		if (Keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
		    if (y < 445) {
		        y += 5;
		    }
		}
    }

    @Override
    public void shoot() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shoot'");
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }

    @Override
    public void remove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }
}