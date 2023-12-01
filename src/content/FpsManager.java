package content;
import java.awt.*;

public class FpsManager {
    // FPS
    public int fpsCount = 0;
    public long startTime;
    public long fpsTime = 0;
    public int fpsRate = 60;
    public int fpsValue = 0;

	private Graphics gra;

	// コンストラクタ
	public FpsManager (Graphics gra) {
		this.gra = gra;
	}
    
    public void resetFpsInfo() {
        if ((System.currentTimeMillis() - fpsTime) >= 1000) {
            fpsTime = System.currentTimeMillis();
            this.fpsValue = fpsCount;
            fpsCount = 0;
        }
    }

    public void setStartTime() {
        startTime = System.currentTimeMillis();
    }

    public void upCount() {
        fpsCount++;
    }

    public void sleep() {
        try {
            long runTime = System.currentTimeMillis() - startTime;
            if (runTime < (1000 / fpsRate)) {
                Thread.sleep((1000 / fpsRate) - (runTime));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showFpsValue() {
        gra.setColor(Color.BLACK);
        gra.setFont(new Font("SansSerif", Font.PLAIN, 15));
        gra.drawString("FPS:" + fpsValue, 5, 20);
    }
}
