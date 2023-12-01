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
	private Player player;

	// コンストラクタ
	public GamePlay (Graphics gra) {
		this.gra = gra;
	}

    public void init() {
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

		player = new Player(gra);
		player.init();
    }

    public void main () {
        Random random = new Random();


		// プレイヤーの弾の処理
		// 敵の弾の処理
		// プレイヤーの処理
		// 敵の処理

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
		        enemyBulletList.add(new Bullet(enemy.x, enemy.y, player.x, player.y, EnumBulletType.SHIP_AIM));
		    }

		    // 自機と敵が衝突した時の処理
		    if (enemy.x >= player.x && enemy.x <= player.x + 30
		        && enemy.y >= player.y && enemy.y <= player.y + 20) {
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
		    if (enemyBullet.x >= player.x && enemyBullet.x <= player.x + 30
		        && enemyBullet.y >= player.y && enemyBullet.y <= player.y + 20) {
		        isGameOverFlg = true;
		    }
		}

        return;
	}

    public void show() {
        // プレイヤー表示
		player.show();

		// 敵の表示
		// TODO:敵の表示

		// UI表示
		this.showUserIntercase();
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
		// プレイヤーショット
		if (Keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
		    if (bulletIntraval == 0) {
		        playerBulletList.add(new Bullet(player.x + 12, player.y));
		        bulletIntraval = 5;
		    }
		}
		if (bulletIntraval > 0) {
		    bulletIntraval--;
		}

		player.inputKey();
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
