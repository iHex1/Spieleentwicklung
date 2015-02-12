package MoveTest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Enemy {

    private static BufferedImage[] look = new BufferedImage[4];
    private static BufferedImage look_dead;
    private final static float NEEDEDANITIME = 1;
    private final static Random r = new Random();
    private float aniTime = 0;
    private float posx;
    private float posy;
    private Rectangle bounding;
    private List<Bullet> bullets;
    private boolean alive = true;
    
    static {
        try {
            look[0] = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("gfx/enemy1.png"));
            look[1] = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("gfx/enemy2.png"));
            look[2] = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("gfx/enemy3.png"));
            look[3] = look[1];
            look_dead = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("gfx/enemy_kaputt.png"));
        } catch (IOException e) {e.printStackTrace();}
    }
    
    public Enemy(float x, float y, List<Bullet> bullets) {
        this.posx = x;
        this.posy = y;
        bounding = new Rectangle((int)x, (int)y, look[0].getWidth(), look[0].getHeight());
        this.bullets = bullets;
    }
    
    public void update(float timeSinceLastFrame) {
        aniTime += timeSinceLastFrame;
        if (aniTime > NEEDEDANITIME) aniTime = 0;
        
        posx -= 100 * timeSinceLastFrame; 
        
        if (posx <= -getLook().getWidth()) {
            posx = 800;
            posy = r.nextInt(600 - getLook().getHeight());
            alive = true;
        }
        
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if (alive && bounding.intersects(b.getBounding())) {
                alive = false;
                bullets.remove(b);
            }
        }
        
        bounding.x = (int) posx;
        bounding.y = (int) posy;
    }
    
    public Rectangle getBounding() {
        return bounding;
    }
    
    public BufferedImage getLook() {
        
        if (!alive) return look_dead;
        else {
            if (look.length == 0) return look_dead;
            for (int i = 0; i < look.length; i++) {
                if(aniTime < (float)(NEEDEDANITIME / look.length * (i+1))) {
                    return look[i];
                }
            }
            return look[look.length - 1]; 
        }
    }
    
    public boolean isAlive() {
        return alive;
    }
    
    public static int getWidth() {
        return look[0].getWidth();
    }
    
    public static int getHeight() {
        return look[0].getHeight();
    }
}
