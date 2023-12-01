package content;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
    private Graphics gra;
    public int x, y;
    public int moveX, moveY;
    
    public Enemy(Graphics gra) {
        this.gra = gra;
    }

    public Enemy(Graphics gra, int x, int y, int moveX, int moveY) {
        this.gra = gra;
        this.x = x;
        this.y = y;
        this.moveX = moveX;
        this.moveY = moveY;
    }

    public void move() {
        x += moveX;
        y += moveY;
    }

    public void show() {
        gra.setColor(Color.RED);
        gra.fillRect(this.x + 10, this.y, 10, 10);
        gra.fillRect(this.x, this.y - 10, 30, 10);
    }
}
