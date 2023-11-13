package content;
import java.util.ArrayList;

import enums.EnumBulletType;

public class Bullet {
    public int x, y;
    public int moveX, moveY;
    public EnumBulletType type;
    public ArrayList<Integer> xLog = new ArrayList<>();
    public ArrayList<Integer> yLog = new ArrayList<>();
    
    public Bullet() {}

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.moveX = 0;
        this.moveY = 0;
        this.type = EnumBulletType.NORMAL;
        this.xLog = new ArrayList<>();
        this.yLog = new ArrayList<>();
    }

    public Bullet(int x, int y, int playerX, int playerY, EnumBulletType type) {
        this.x = x;
        this.y = y;
        this.moveX = 0;
        this.moveY = 0;
        this.type = type;
        this.xLog = new ArrayList<>();
        this.yLog = new ArrayList<>();

        if (type == EnumBulletType.SHIP_AIM) {
            double xv = playerX - x;
            double yv = playerY - y;
            // 直線距離を求める
            double range = Math.sqrt(xv * xv + yv * yv);
            this.moveX = (int) (xv / range * 8);
            this.moveY = (int) (yv / range * 8);
        }
    }

    public void addLog(int x, int y) {
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
}