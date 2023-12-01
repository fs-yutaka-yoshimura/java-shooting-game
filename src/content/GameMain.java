package content;
import java.awt.*;

import base.Frame;
import enums.EnumScreen;

public class GameMain {
    public static Frame frame;
    public static boolean loop;
    
    public static void  gameMain() {
        // フレーム生成
        frame = new Frame();
        Graphics gra = frame.panel.image.getGraphics();

        FpsManager fpsManager = new FpsManager(gra);
        GameTitle title = new GameTitle(gra);
        GamePlay gamePlay = new GamePlay(gra);
        GameOver gameOver = new GameOver(gra);
        
        EnumScreen screen = EnumScreen.GAME_TITLE;
        loop = true;
        while (loop) {
            fpsManager.resetFpsInfo();
            fpsManager.upCount();
            fpsManager.setStartTime();

            // スクリーンを白塗り
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
                    gamePlay.main();
                    gamePlay.show();
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
            fpsManager.showFpsValue();

            // スクリーンに反映
            frame.panel.draw();

            // スリープ処理
            fpsManager.sleep();
        }
    }
}
