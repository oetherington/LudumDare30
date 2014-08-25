package ld30.com.main;

import java.awt.Rectangle;

public class Bullet extends Enemy {
	public static int WIDTH = Sprite.BULLET.getWidth(),
			HEIGHT = Sprite.BULLET.getHeight();
	
	private boolean m_finished;
	private Rectangle m_collider;
	
	public Bullet(int x, int y, double speed)
	{
		super(x, y, WIDTH, HEIGHT, 1, 0, 7.0, false);
		
		m_finished = false;
		m_collider = new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean finished()
	{
		return m_finished;
	}

	public void tick()
	{
		if (!m_finished) {
			m_y += m_speed;
			m_collider.y = m_y;
			
			if (m_y > Main.HEIGHT)
				m_finished = true;
		}
	}

	public boolean checkCollision(Rectangle collider)
	{
		if (m_finished)
			return false;
		
		return m_collider.intersects(collider);
	}
	
	public void die()
	{
		m_finished = true;
	}
	
	public void render(Bitmap dest)
	{
		dest.blit(m_x, m_y, Sprite.BULLET);
	}
}
