package content;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.KeyEvent;

import base.Keyboard;
import enums.EnumBulletType;
import enums.EnumScreen;

public class GamePlay {

    private Graphics gra;
	private int playerX;
    private int playerY;
	private int bulletIntraval;
	private int score;
	private int level;
	private int rateEnemyGenerate;
	private int rateEnemyBulletGenerate;
	private long levelTimer;
	private ArrayList<Bullet> playerBulletList;
	private ArrayList<Enemy> enemyList;
	private ArrayList<Bullet> enemyBulletList;
    public boolean isInit = true;
    private boolean isGameOverFlg = false;

	// コンストラクタ
	public GamePlay (Graphics gra) {
		this.gra = gra;
	}

    public void init() {
        playerX = 235;
        playerY = 430;
        bulletIntraval = 0;
        score = 0;
        level = 1;
        rateEnemyGenerate = 90;
        rateEnemyBulletGenerate = 90;
        levelTimer = System.currentTimeMillis();
        playerBulletList = new ArrayList<>();
        enemyList = new ArrayList<>();
        enemyBulletList = new ArrayList<>();
        isInit = false;
    }

    public void show () {
        Random random = new Random();

		// プレイヤーの弾の表示
		for (int i = 0; i < playerBulletList.size(); i++) {
		    gra.setColor(new Color(0, 0, 255));
		    Bullet bullet = playerBulletList.get(i);

		    // ログの追加
		    bullet.addLog(bullet.x, bullet.y);

		    // 実態表示
		    gra.fillOval(bullet.x - 2, bullet.y,10, 10);

		    // 残像表示
		    for (int j = 0; j < bullet.xLog.size(); j++) {
		        gra.setColor(new Color(0, 0, 255, j * 30));
		        gra.fillOval(bullet.xLog.get(j) - 2, bullet.yLog.get(j), 10, 10);
		    }

		    // 弾の移動
		    bullet.y -= 10;

		    // 弾の削除
		    if (bullet.y < 0) {
		        playerBulletList.remove(i);
		    }

		    // 敵に弾が当たった時の処理
		    for (int j = 0; j < enemyList.size(); j++) {
		        Enemy enemy = enemyList.get(j);
		        if (bullet.x >= enemy.x && bullet.x <= enemy.x + 30
		            && bullet.y >= enemy.y && bullet.y <= enemy.y + 20) {
		                enemyList.remove(j);
		                score += 10;
		        }
		    }
		}

        // 敵の処理
		gra.setColor(Color.RED);
		for (int i = 0; i < enemyList.size(); i++) {
		    Enemy enemy = enemyList.get(i);

            // 敵の表示
            enemy.show();

		    enemy.y += 3;
		    if (enemy.y > 500) {
		        enemyList.remove(i);
		    }

		    // 敵の弾発射
		    if (random.nextInt(rateEnemyBulletGenerate) == 1) {
		        enemyBulletList.add(new Bullet(enemy.x, enemy.y, playerX, playerY, EnumBulletType.SHIP_AIM));
		    }

		    // 自機と敵が衝突した時の処理
		    if (enemy.x >= playerX && enemy.x <= playerX + 30
		        && enemy.y >= playerY && enemy.y <= playerY + 20) {
		        isGameOverFlg = true;
		        score += level * 100;
		    }
		}

		// 敵の追加
		if (random.nextInt(rateEnemyGenerate) == 1) {
		    enemyList.add(new Enemy(gra, random.nextInt(470), 0));
		} 

		// 敵の弾の表示
		for (int i = 0; i < enemyBulletList.size(); i++) {
		    Bullet enemyBullet = enemyBulletList.get(i);
		    gra.fillRect(enemyBullet.x + 12, enemyBullet.y, 5, 5);

		    // 敵の弾移動
		    switch (enemyBullet.type) {
		        case SHIP_AIM:
		            enemyBullet.x += enemyBullet.moveX;
		            enemyBullet.y += enemyBullet.moveY;
		            break;
		        case TRACKING:
		            break;
		        default:
		            enemyBullet.y += 10;
		            break;
		    }

		    if (enemyBullet.y > 500) {
		        enemyBulletList.remove(i);
		    }

		    // 自機に弾が当たった時の処理
		    if (enemyBullet.x >= playerX && enemyBullet.x <= playerX + 30
		        && enemyBullet.y >= playerY && enemyBullet.y <= playerY + 20) {
		        isGameOverFlg = true;
		    }
		}

		// プレイヤーショット
		if (Keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
		    if (bulletIntraval == 0) {
		        playerBulletList.add(new Bullet(playerX + 12, playerY));
		        bulletIntraval = 5;
		    }
		}
		if (bulletIntraval > 0) {
		    bulletIntraval--;
		}

        return;
	}

    public void showPlayer() {
        // プレイヤー初期表示
		gra.setColor(Color.BLUE);
		gra.fillRect(playerX + 10, playerY , 10, 10);
		gra.fillRect(playerX, playerY + 10, 30, 10);
    }
    
    public void showUserIntercase () {
		Font font = new Font("SansSerif", Font.PLAIN, 50);
		FontMetrics metrics = gra.getFontMetrics(font);

		// スコアの表示
		gra.setColor(Color.BLACK);
		font = new Font("SansSerif", Font.PLAIN, 20);
		metrics = gra.getFontMetrics(font);
		gra.setFont(font);
		gra.drawString("SCORE:" + score, 480 - metrics.stringWidth("SCORE:" + score), 20);

		// レベルの表示
		gra.drawString("LEVEL:" + level, 480 - metrics.stringWidth("LEVEL:" + level), 40);
		gra.drawString("弾の発射レート:" + rateEnemyBulletGenerate, 480 - metrics.stringWidth("弾の発射レート:" + rateEnemyBulletGenerate), 60);
		gra.drawString("敵の出現率:" + rateEnemyGenerate, 480 - metrics.stringWidth("敵の出現率:" + rateEnemyGenerate), 80);   

        return;
    }

    public void lavelUp() {
        // レベルアップ処理
		if (System.currentTimeMillis() - levelTimer > 3 * 1000) {
		    levelTimer = System.currentTimeMillis();
		    level++;
		    rateEnemyBulletGenerate = level < 10 ? 90 - (level * 4): 50;
		    rateEnemyGenerate = level < 10 ? 90 - (level * 8): 10;
		}
    }

    public void inputKey () {
		// プレイヤー移動
		if (Keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
		    if (playerX > 0) {
		        playerX -= 5;
		    }
		}
		if (Keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
		    if (playerX < 460) {
		        playerX += 5;
		    }
		}
		if (Keyboard.isKeyPressed(KeyEvent.VK_UP)) {
		    if (playerY > 0) {
		        playerY -= 5;
		    }
		}
		if (Keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
		    if (playerY < 445) {
		        playerY += 5;
		    }
		}
    }

    public int getScore () {
        return score;
    }

    public EnumScreen getScreenState () {
        if (isGameOverFlg) {
            isInit = true;
            isGameOverFlg = false;
            return EnumScreen.GAME_OVER;
        }
        return EnumScreen.GAME_PLAY;
    }
}
