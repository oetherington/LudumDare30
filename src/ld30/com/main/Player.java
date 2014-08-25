package ld30.com.main;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Player extends Entity {
	public static final int WIDTH = Sprite.EARTH.getWidth(), 
			HEIGHT = Sprite.EARTH.getHeight(), POINT_REGULARITY = 80,
			EXPLOSION_PERIOD = 15, LIVES = 5, ANIMATION_PERIOD = 15;
	public static final double VELOCITY_MAX = 3.4, VELOCITY_INC = 0.3;
	
	private double m_x_vel, m_y_vel;
	private int m_boundingWidth, m_boundingHeight, m_lives, 
		m_point_phase, m_explosion_tick, m_animation_tick;
	private boolean m_exploding, m_alive;
	private Rectangle m_collider;
	private Counter m_score;
	private Laser m_laser;
	
	public Player(int boundingWidth, int boundingHeight)
	{
		super(boundingWidth / 2 - WIDTH / 2, boundingHeight / 2 - HEIGHT / 2,
				WIDTH, HEIGHT);
		m_x_vel = 0.0;
		m_y_vel = 0.0;
		m_boundingWidth = boundingWidth;
		m_boundingHeight = boundingHeight;
		m_lives = LIVES;
		m_animation_tick = -1;
		m_point_phase = 0;
		m_explosion_tick = 0;
		m_exploding = false;
		m_alive = true;
		m_collider = new Rectangle(m_x, m_y, WIDTH, HEIGHT);
		m_score = new Counter(10, 10);
		m_laser = new Laser(boundingWidth);
	}
	
	public int getLives()
	{
		return m_lives;
	}
	
	public void giveLife()
	{
		m_lives++;
	}
	
	public boolean isDead()
	{
		return m_lives <= 0;
	}
	
	public Laser getLaser()
	{
		return m_laser;
	}
	
	public void givePoints(int points)
	{
		m_score.addPoints(points);
	}
	
	public int getPoints()
	{
		return m_score.getPoints();
	}
	
	public void updateColliderPosition()
	{
		m_collider.x = m_x;
		m_collider.y = m_y;
	}
	
	public void checkCollisions(ArrayList<Enemy> entities, boolean right)
	{
		if (!m_alive)
			return;
		
		if (right)
			m_collider.x += m_boundingWidth;
		
		for (int i = 0; i < entities.size(); i++) {
			Enemy e = entities.get(i);
			Gun g = e.getGun();
			
			if (g != null) {
				ArrayList<Bullet> bullets = g.getBullets();
				
				for (int j = 0; j < bullets.size(); j++) {
					if (bullets.get(j).checkCollision(m_collider)) {
						hit(bullets.get(j));
					}
				}
			}
			
			if (e.checkCollision(m_collider)) {
				hit(e);
			}
		}
		
		if (right)
			m_collider.x -= m_boundingWidth;
		
		m_laser.checkCollisions(entities, right);
	}
	
	public void checkPickupCollisions(ArrayList<Pickup> pickups)
	{
		if (!m_alive)
			return;

		for (int i = 0; i < pickups.size(); i++) 
			if (pickups.get(i).checkCollision(m_collider))
				pickup(pickups.get(i));
	}
	
	public void tick(boolean up, boolean down, boolean left, 
			boolean right, boolean fire)
	{
		givePoints(m_laser.emptyPointStore());
	
		m_animation_tick++;
		
		if (!m_alive)
			return;
		
		if (m_exploding) {
			m_explosion_tick++;
			
			if (m_explosion_tick > EXPLOSION_PERIOD * 2)
				m_alive = false;
			
			return;
		}
		
		if (up)
			m_y_vel -= (m_y_vel <= (-VELOCITY_MAX)) ? 0 : VELOCITY_INC;

		if (down)
			m_y_vel += (m_y_vel >= VELOCITY_MAX) ? 0 : VELOCITY_INC;
		
		if (left)
			m_x_vel -= (m_x_vel <= (-VELOCITY_MAX)) ? 0 : VELOCITY_INC;
		
		if (right)
			m_x_vel += (m_x_vel >= VELOCITY_MAX) ? 0 : VELOCITY_INC;

		m_x += m_x_vel;
		m_y += m_y_vel;
		
		if (m_y < 0) {
			m_y_vel = -m_y_vel / 2;
			m_y = 0;
		}
		
		if (m_y + HEIGHT > m_boundingHeight) {
			m_y_vel = -m_y_vel / 2;
			m_y = m_boundingHeight - HEIGHT;
		}
		
		if (m_x < 0) {
			m_x_vel = -m_x_vel / 2;
			m_x = 0;
		}
		
		if (m_x + WIDTH > m_boundingWidth) {
			m_x_vel = -m_x_vel / 2;
			m_x = m_boundingWidth - WIDTH;
		}
		
		m_laser.tick(fire, m_x + WIDTH / 2 - Laser.WIDTH / 2, m_y);
		
		m_point_phase++;
		if (m_point_phase % POINT_REGULARITY == 0)
			givePoints(1);
		
		updateColliderPosition();
	}
	
	private void explode()
	{
		m_laser.turnOff();
		m_exploding = true;
		Sound.DEATH.play();
	}
	
	private void hit(Enemy collided)
	{
		collided.die();
		
		m_lives--;
		
		Sound.HIT.play();
		
		if (m_lives <= 0)
			explode();
	}
	
	private void hit(Bullet collided)
	{
		collided.die();
		
		m_lives--;
		
		Sound.HIT.play();
		
		if (m_lives <= 0)
			explode();
	}
	
	public void pickup(Pickup p)
	{
		p.pickup(this);
	}
	
	public void render(Bitmap dest)
	{
		if (!m_alive)
			return;
		
		final int sprite = (m_animation_tick % ANIMATION_PERIOD < ANIMATION_PERIOD / 2)
				? 1 : 2;
		
		dest.blit(m_x, m_y, (sprite == 1) ? Sprite.SHIP1 : Sprite.SHIP2);
		dest.blit(m_x + m_boundingWidth, m_y, (sprite == 2) ? Sprite.SHIP1 : Sprite.SHIP2);
		m_laser.render(dest);
		
		if (m_exploding) {
			Sprite e = (m_explosion_tick < EXPLOSION_PERIOD) 
					? Sprite.EXP_SMALL
					: Sprite.EXP_BIG;
			
			dest.blit(m_x, m_y, e);
			dest.blit(m_x + m_boundingWidth, m_y, e);
		}
	}
	
	public void renderScore(Bitmap dest)
	{
		m_score.render(dest);
	}
}
