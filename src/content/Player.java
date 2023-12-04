package content;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import base.Keyboard;

public class Player implements ICharacter, IAttackable {
	private Graphics gra;

	public int x;
    public int y;
    public int width;
    public int height;
    public int hitSize;
	public int bulletIntraval;
	public ArrayList<Bullet> playerBulletList;
    public boolean isDead;
    public boolean isInit;
    public BufferedImage bufferedImage;

	// コンストラクタ
	public Player (Graphics gra) {
		this.gra = gra;
	}

    public void init() {
        x = 235;
        y = 430;
        hitSize = 10;
        bulletIntraval = 0;
        playerBulletList = new ArrayList<>();
        isInit = false;
        try {
            bufferedImage = ImageIO.read(new File("src\\image\\player_small.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
    }

    public void show () {
        // 描画
		// gra.fillRect(x, y + 10, 30, 10);
        gra.drawImage(bufferedImage, x, y, null);

        // 当たり判定
        // gra.setColor(Color.GREEN);
        gra.setColor(new Color(0, 255, 0, 120));
		gra.fillRect(x + (width / 2) - hitSize, y + (height / 2) - hitSize, hitSize * 2, hitSize * 2);
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