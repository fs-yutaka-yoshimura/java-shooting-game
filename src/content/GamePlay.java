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
import enums.EnumObjectType;
import enums.EnumScreen;

public class GamePlay {

    private Graphics gra;
	private Random random;

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
		this.random = new Random();
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
		// プレイヤーの弾の表示
		for (int i = 0; i < playerBulletList.size(); i++) {
		    Bullet bullet = playerBulletList.get(i);
			bullet.move();

		    // 弾の削除
		    if (isOutsideDisplayArea(bullet.x, bullet.y)) {
		        playerBulletList.remove(i);
		    }

		    // 敵に弾が当たった時の処理
		    for (int j = 0; j < enemyList.size(); j++) {
		        Enemy enemy = enemyList.get(j);
		        if (isHitPlayerBullet(bullet, enemy)) {
		                enemyList.remove(j);
						addScore();
		        }
		    }
		}

        // 敵の処理
		for (int i = 0; i < enemyList.size(); i++) {
		    Enemy enemy = enemyList.get(i);
			enemy.move();

			// 敵の削除
		    if (isOutsideDisplayArea(enemy.x, enemy.y)) {
		        enemyList.remove(i);
		    }

		    if (isAddEnemyBullet()) {
				addEnemyBullet(enemy);
		    }

		    if (isHitEnemeyToPlayer(enemy)) {
				setStateGameOver();
		    }
		}

		if (isAddEnemy()) {
		    enemyList.add(new Enemy(gra, random.nextInt(470), 0, 0, 3));
		} 

		for (int i = 0; i < enemyBulletList.size(); i++) {
		    Bullet enemyBullet = enemyBulletList.get(i);
			enemyBullet.move();
		    if (isOutsideDisplayArea(enemyBullet.x, enemyBullet.y)) {
		        enemyBulletList.remove(i);
		    }

		    // 自機に弾が当たった時の処理
		    if (isHitEnemeyBulletToPlayer(enemyBullet)) {
				setStateGameOver();
		    }
		}
        return;
	}

    public void show() {
        // プレイヤー表示
		player.show();

		// プレイヤーの弾の表示
		for (int i = 0; i < playerBulletList.size(); i++) {
		    Bullet bullet = playerBulletList.get(i);
			bullet.show();
		}

        // 敵の表示
		for (int i = 0; i < enemyList.size(); i++) {
		    Enemy enemy = enemyList.get(i);
            enemy.show();
		}

		// 敵の弾の表示
		for (int i = 0; i < enemyBulletList.size(); i++) {
		    Bullet enemyBullet = enemyBulletList.get(i);
			enemyBullet.show();
		}

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
		        playerBulletList.add(
					new Bullet(
						gra, player.x + (player.width / 2) - 5,
						player.y, 0, -5, EnumObjectType.OBJ_TYPE_PLAYER));
		        bulletIntraval = 10;
		    }
		}
		if (bulletIntraval > 0) {
		    bulletIntraval--;
		}

		player.inputKey();
    }

	public boolean isAddEnemy() {
		return random.nextInt(rateEnemyGenerate) == 1;
	}

	public boolean isAddEnemyBullet() {
		return random.nextInt(rateEnemyBulletGenerate) == 1;
	}

	public boolean isHitPlayerBullet(Bullet bullet, Enemy enemy) {
		return bullet.x >= enemy.x && bullet.x <= enemy.x + 30
			 && bullet.y >= enemy.y && bullet.y <= enemy.y + 20;
	}

	private void addScore() {
		score += 10;
	}

	private void addEnemyBullet(Enemy enemy) {
		EnumBulletType bulletType = EnumBulletType.values()[new Random().nextInt(EnumBulletType.values().length)];
		enemyBulletList.add(new Bullet(gra, enemy.x + (enemy.width / 2) - 5, enemy.y + enemy.height, player.x, player.y, EnumObjectType.OBJ_TYPE_ENEMY, bulletType));	
	}

	public boolean isHitEnemeyToPlayer(Enemy enemy) {
        // gra.setColor(Color.BLUE);
		// gra.fillRect(x + 10, y , 10, 10);
		// gra.fillRect(x, y + 10, 30, 10);
		
		return enemy.x >= player.x && enemy.x <= player.x + 30
			 && enemy.y >= player.y && enemy.y <= player.y + 20;
	}

	public boolean isHitEnemeyBulletToPlayer(Bullet bullet) {
		return isHit(player.x, player.y, player.width, player.height, player.hitSize,
			 bullet.x, bullet.y, bullet.width, bullet.height, bullet.hitSize);
	}

	private void setStateGameOver() {
		isGameOverFlg = true;
		score += level * 100;
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

	public boolean isOutsideDisplayArea(int x, int y) {
		return x < -50 || x > 550 || y < -50 || y > 550; 
	}

	public boolean isHit(int obj1_x, int obj1_y, int obj1_width, int obj1_height, int obj1_hitSize,
		int obj2_x, int obj2_y, int obj2_width, int obj2_height, int obj2_hitSize) {
		int hitBox1_x = obj1_x + (obj1_width / 2) - obj1_hitSize;
		int hitBox1_y = obj1_y + (obj1_height / 2) - obj1_hitSize;
		int hitBox2_x = obj2_x + (obj2_width / 2) - obj2_hitSize;
		int hitBox2_y = obj2_y + (obj2_height / 2) - obj2_hitSize;
		if (hitBox1_x >= hitBox2_x && hitBox1_x <= hitBox2_x + (obj2_hitSize * 2) && hitBox1_y >= hitBox2_y && hitBox1_y <= hitBox2_y + (obj2_hitSize * 2)) {
			return true;
		}
		return false;
	}
}
