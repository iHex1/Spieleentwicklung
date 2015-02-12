package MoveTest;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class MoveTest {
    static List<Bullet> bullets = new LinkedList<Bullet>();
    static List<Enemy> enemys = new LinkedList<Enemy>();
    static Player player = new Player(300, 300, 800, 600, bullets, enemys);
    static Background bg = new Background(50);
    static Random r = new Random();

    public static void main(String[] args) {
        spawnEnemy();
    	
        Frame f = new Frame(player, bg, bullets, enemys);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 600);
        f.setUndecorated(true);
        f.setVisible(true);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        
       /* DisplayMode displayMode = new DisplayMode(800, 600, 16, 60);
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = environment.getDefaultScreenDevice();
        device.setFullScreenWindow(f);
        device.setDisplayMode(displayMode);*/
        
        f.makeStrat();
        
        final float ENEMYSPAWNTIME = 1f;
        float timeSinceLastEnemySpawn = 0;
        
        long lastFrame = System.currentTimeMillis();
        
        while (true) {
        	if(Keyboard.isKeyDown(KeyEvent.VK_ESCAPE)) System.exit(0);
        	
        	long thisFrame = System.currentTimeMillis();
        	float timeSinceLastFrame = ((float)(thisFrame - lastFrame))/1000f;
        	lastFrame = thisFrame;
        	
        	timeSinceLastEnemySpawn += timeSinceLastFrame;
        	if (timeSinceLastEnemySpawn >= ENEMYSPAWNTIME) {
        	    timeSinceLastEnemySpawn -= ENEMYSPAWNTIME;
        	    spawnEnemy();
        	}
        	
            player.update(timeSinceLastFrame);
            bg.update(timeSinceLastFrame);
            
            for (int i = 0; i < enemys.size(); i++) {
                enemys.get(i).update(timeSinceLastFrame);
            }
            
            for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).update(timeSinceLastFrame);
			}
            
            f.repaintScreen();
            
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public static void spawnEnemy() {
        enemys.add(new Enemy(800, r.nextInt(600-Enemy.getHeight()), bullets));
    }
}
