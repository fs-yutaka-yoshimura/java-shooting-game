package content;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends Bullet {
    private Graphics gra;
    public int x, y;
    
    public Enemy(Graphics gra) {}

    public Enemy(Graphics gra, int x, int y) {
        this.gra = gra;
        this.x = x;
        this.y = y;
    }

    public void show() {
        gra.setColor(Color.RED);
        gra.fillRect(this.x + 10, this.y, 10, 10);
        gra.fillRect(this.x, this.y - 10, 30, 10);
    }
}
