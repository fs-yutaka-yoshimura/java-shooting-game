import java.awt.*;

import base.Frame;
import content.GameOver;
import content.GamePlay;
import content.GameTitle;
import enums.EnumScreen;

public class GameMain {
    public static Frame frame;
    public static boolean loop;
    
    public static void  gameMain() {
        // フレーム生成
        frame = new Frame();
        loop = true;

        Graphics gra = frame.panel.image.getGraphics();

        // FPS
        long startTime;
        long fpsTime = 0;
        int fpsRate = 30;
        int fpsValue = 0;
        int fpsCount = 0;
        
        EnumScreen screen = EnumScreen.GAME_TITLE;

        GameTitle title = new GameTitle(gra);
        GamePlay gamePlay = new GamePlay(gra);
        GameOver gameOver = new GameOver(gra);
        
        while (loop) {
            if ((System.currentTimeMillis() - fpsTime) >= 1000) {
                fpsTime = System.currentTimeMillis();
                fpsValue = fpsCount;
                fpsCount = 0;
            }
            fpsCount++;

            startTime = System.currentTimeMillis();

            gra.setColor(Color.WHITE);
            gra.fillRect(0, 0, 500, 500);

            switch (screen) {
                case GAME_TITLE:
                    title.show();
                    title.inputKey();
                    screen = title.getScreenState();
                    break;
                case GAME_PLAY:
                    if (gamePlay.isInit) {
                        gamePlay.init();
                    }
                    gamePlay.show();
                    gamePlay.showPlayer();
                    gamePlay.showUserIntercase();
                    gamePlay.lavelUp();
                    gamePlay.inputKey();
                    screen = gamePlay.getScreenState();
                    break;
                case GAME_OVER:
                    gameOver.show(gamePlay.getScore());
                    gameOver.inputKey();
                    screen = gameOver.getScreenState();
                    break;
            }

            // FSPの表示
            gra.setColor(Color.BLACK);
            gra.setFont(new Font("SansSerif", Font.PLAIN, 15));
            gra.drawString("FPS:" + fpsValue, 5, 20);

            // スクリーンに反映
            frame.panel.draw();

            try {
                long runTime = System.currentTimeMillis() - startTime;
                if (runTime < (1000 / fpsRate)) {
                    Thread.sleep((1000 / fpsRate) - (runTime));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
