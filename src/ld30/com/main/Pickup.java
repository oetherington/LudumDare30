package ld30.com.main;

import java.awt.Rectangle;

public class Pickup extends Entity {
	public static final int WIDTH = 32, HEIGHT = 32;
	
	protected Sprite m_sprite;
	protected Rectangle m_collider;
	protected boolean m_finished;
	
	public Pickup(int x, int y)
	{
		m_x = x;
		m_y = y;
		m_w = WIDTH;
		m_h = HEIGHT;
		m_collider = new Rectangle(m_x, m_y, m_w, m_h);
		m_finished = false;
	}
	
	public boolean checkCollision(Rectangle collider)
	{
		if (m_finished)
			return false;

		return m_collider.intersects(collider);
	}
	
	public boolean isFinished()
	{
		return m_finished;
	}
	
	public void pickup(Player p)
	{
		m_finished = true;
		act(p);
		Sound.PICKUP.play();
	}
	
	/* To be overridden by subclasses */
	protected void act(Player p) {}
	
	public void tick(int scrollAmt)
	{
		m_y += scrollAmt;
		m_collider.y = m_y;
	}
	
	public void render(Bitmap dest)
	{
		if (!m_finished)
			dest.blit(m_x, m_y, m_sprite);
	}
}
