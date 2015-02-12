package MoveTest;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Player {
	private Rectangle bounding;
	private float f_posx;
	private float f_posy;
	private int worldsize_x;
	private int worldsize_y;
	private BufferedImage look;
	private BufferedImage look_dead;
	private List<Bullet> bullets;
	private List<Enemy> enemys;
	private float timeSinceLastShot = 0;
	private final float SHOTFREQUENZY = 0.1f; 
	private boolean alive = true;
	
	public Player(int x, int y, int worldsize_x, int worldsize_y, List<Bullet> bullets, List<Enemy> enemys) {
		try {
			look = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/raumschiffchen.png"));
			look_dead = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/raumschiff_kaputt.png"));
		} catch (IOException e) {e.printStackTrace();}
		bounding = new Rectangle(x, y, look.getWidth(), look.getHeight());
		f_posx = x;
		f_posy = y;
		this.worldsize_x = worldsize_x;
		this.worldsize_y = worldsize_y;
		this.bullets = bullets;
		this.enemys = enemys;
	}
	
	public void update(float timeSinceLastFrame) {
	    if (!alive) return;
	    
		timeSinceLastShot += timeSinceLastFrame;
		if (Keyboard.isKeyDown(KeyEvent.VK_W)) f_posy -= 300 * timeSinceLastFrame;
		if (Keyboard.isKeyDown(KeyEvent.VK_S)) f_posy += 300 * timeSinceLastFrame;
		if (Keyboard.isKeyDown(KeyEvent.VK_D)) f_posx += 300 * timeSinceLastFrame;
		if (Keyboard.isKeyDown(KeyEvent.VK_A)) f_posx -= 300 * timeSinceLastFrame;
		
		if(timeSinceLastShot > SHOTFREQUENZY && Keyboard.isKeyDown(KeyEvent.VK_SPACE)) {
			timeSinceLastShot = 0;
			bullets.add(new Bullet(f_posx+look.getWidth()/2-Bullet.getLook().getWidth()/2, f_posy+look.getHeight()/2-Bullet.getLook().getHeight()/2, 500, 0, bullets));
		}
		
		if (f_posx < 0) f_posx = 0;
		if (f_posy < 0) f_posy = 0;
		if (f_posx > worldsize_x - bounding.width) f_posx = worldsize_x - bounding.width;
		if (f_posy > worldsize_y - bounding.height) f_posy = worldsize_y - bounding.height;
		
		bounding.x = (int)f_posx;
		bounding.y = (int)f_posy;
		
		for (int i = 0; i < enemys.size(); i++) {
            Enemy e = enemys.get(i);
            if (e.isAlive() && bounding.intersects(e.getBounding())) {
                alive = false;
            }
        }
	}
	
	public BufferedImage getLook() {
	    if (alive) return look;
	    else return look_dead;
	}
	
	public Rectangle getBounding() {
		return bounding;
	}
}
