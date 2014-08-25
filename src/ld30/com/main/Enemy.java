package ld30.com.main;

import java.awt.Rectangle;

public class Enemy extends Entity {
	public static final int EXPLOSION_PERIOD = 5;
	
	protected int m_health, m_explosion_tick, m_points;
	protected double m_scroll, m_speed;
	protected boolean m_alive, m_exploding, m_canExplode;
	protected Bitmap m_img;
	protected Rectangle m_collider;
	
	public Enemy(int x, int y, int w, int h, int health, int points, 
			double speed, boolean canExplode)
	{
		m_x = x;
		m_y = y;
		m_w = w;
		m_h = h;
		m_health = health;
		m_points = points;
		m_speed = speed;
		m_canExplode = canExplode;
		m_scroll = 0.0;
		m_alive = true;
		m_exploding = false;
		m_explosion_tick = 0;
		m_img = new Bitmap(w, h);
		m_collider = new Rectangle(m_x, m_y, m_w, m_h);
	}
	
	public void updateColliderPosition()
	{
		m_collider.x = m_x;
		m_collider.y = (int) (m_y + m_scroll);
	}
	
	public void setX(int x)
	{
		m_x = x;
		updateColliderPosition();
	}
	
	public void setY(int y)
	{
		m_y = y;
		updateColliderPosition();
	}
	
	public int getPoints()
	{
		return m_points;
	}
	
	public void tick(double scrollOffs)
	{
		if (m_exploding) {
			m_explosion_tick++;
		
			if (m_explosion_tick > EXPLOSION_PERIOD * 2)
				m_alive = false;
		} else if (m_alive) {
			m_scroll += scrollOffs + m_speed;
			updateColliderPosition();
		}
		
		tick();
	}
	
	/* For overriding in subclasses */
	protected void tick() {}
	
	public void explode()
	{
		m_exploding = true;
	}
	
	public void die()
	{
		if (m_canExplode)
			explode();
		else
			m_alive = false;
		
		Sound.ENEMY_DEATH.play();
	}
	
	public boolean damage(int amount)
	{
		m_health -= amount;
		
		if (m_health < 0) {
			die();
			return true;
		}
		
		return false;
	}
	
	public void render(Bitmap dest)
	{
		prerender(dest);
		
		if (m_alive) {
			dest.blit(m_x, (int) (m_y + m_scroll), m_img);
		
			if (m_exploding) {
				Sprite e = (m_explosion_tick < EXPLOSION_PERIOD) 
						? Sprite.EXP_SMALL
						: Sprite.EXP_BIG;
				
				dest.blit(m_x, (int) (m_y + m_scroll), e);
			}
		}
		
		postrender(dest);
	}
	
	/* For overriding in subclasses */
	protected void prerender(Bitmap dest) {}
	protected void postrender(Bitmap dest) {}
	
	/* For overriding in subclasses */
	public Gun getGun()
	{
		return null;
	}
	
	public boolean checkCollision(Rectangle collider)
	{
		if (!m_alive || m_exploding)
			return false;
		
		return m_collider.intersects(collider);
	}
}
