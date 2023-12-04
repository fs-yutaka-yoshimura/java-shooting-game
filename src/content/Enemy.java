package content;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
    private Graphics gra;
    public int x, y;
    public int width;
    public int height;
    public int hitSize;
    public int moveX, moveY;
        
    public Enemy(Graphics gra) {
        this.gra = gra;
    }

    public Enemy(Graphics gra, int x, int y, int moveX, int moveY) {
        this.gra = gra;
        this.x = x;
        this.y = y;
        this.width = 30;
        this.height = 20;
        this.hitSize = 10;
        this.moveX = moveX;
        this.moveY = moveY;
    }

    public void move() {
        x += moveX;
        y += moveY;
    }

    public void show() {
        // 描画
        gra.setColor(Color.RED);
        gra.fillRect(this.x, this.y, 30, 10);
        gra.fillRect(this.x + 10, this.y + 10, 10, 10);

        // 当たり判定
        gra.setColor(new Color(0, 255, 0, 120));
		gra.fillRect(x + (width / 2) - hitSize, y + (height / 2) - hitSize, hitSize * 2, hitSize * 2);
    }
}
