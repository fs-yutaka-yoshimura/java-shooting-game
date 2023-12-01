package content;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;

import enums.EnumBulletType;
import enums.EnumObjectType;

public class Bullet {
    private Graphics gra;
    
    public int x, y;
    public int moveX, moveY;
    public EnumObjectType objectType;
    public EnumBulletType bulletType;
    public ArrayList<Integer> xLog = new ArrayList<>();
    public ArrayList<Integer> yLog = new ArrayList<>();
    
    public Bullet(Graphics gra) {
        this.gra = gra;
    }

    public Bullet(Graphics gra, int x, int y, int moveX, int moveY, EnumObjectType objectType) {
        this.gra = gra;
        this.x = x;
        this.y = y;
        this.moveX = moveX;
        this.moveY = moveY;
        this.objectType = objectType;
        this.bulletType = EnumBulletType.NORMAL;
        this.xLog = new ArrayList<>();
        this.yLog = new ArrayList<>();
    }

    public Bullet(Graphics gra, int x, int y, int playerX, int playerY, EnumObjectType objectType, EnumBulletType bulletType) {
        this.gra = gra;
        this.x = x;
        this.y = y;
        this.moveX = 0;
        this.moveY = 0;
        this.objectType = objectType;
        this.bulletType = bulletType;
        this.xLog = new ArrayList<>();
        this.yLog = new ArrayList<>();
        
        switch (bulletType) {
            case SHIP_AIM:
                double xv = playerX - x;
                double yv = playerY - y;
                // 直線距離を求める
                double range = Math.sqrt(xv * xv + yv * yv);
                this.moveX = (int) (xv / range * 5);
                this.moveY = (int) (yv / range * 5);
                break;
            default:
                this.moveX = 0;
                this.moveY = 5;
                break;
        }
    }

    public void addLog() {
        if (xLog.size() >= 5) {
            xLog.remove(0);
            yLog.remove(0);
        }
        xLog.add(x);
        yLog.add(y);
    }

    public void remove(int idx) {
        xLog.remove(idx);
        yLog.remove(idx);
    }

    public int xLog(int j) {
        return 0;
    }

    public void move() {
        addLog();
        x += moveX;
        y += moveY;
    }

    public void show() {
        switch (objectType) {
            case OBJ_TYPE_ENEMY:
                gra.setColor(Color.RED);
                break;
            case OBJ_TYPE_PLAYER:
                gra.setColor(Color.BLUE);
                break;
            default:
                break;
        }
	    // 実態表示
	    gra.fillOval(x - 2, y,10, 10);

	    // 残像表示
	    for (int j = 0; j < xLog.size(); j++) {
            switch (objectType) {
                case OBJ_TYPE_ENEMY:
                    gra.setColor(new Color(255, 0, 0, j * 30));
                    break;
                case OBJ_TYPE_PLAYER:
                    gra.setColor(new Color(0, 0, 255, j * 30));
                    break;
                default:
                    break;
            }
	        gra.fillOval(xLog.get(j) - 2, yLog.get(j), 10, 10);
	    }
    }
}