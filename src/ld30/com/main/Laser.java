package ld30.com.main;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Laser {
	public static int WIDTH = 4, MAX_POWER = 1000, POWER_DRAIN = 10, 
			POWER_REFILL = 5, RESTART_POWER = 200, BASE_DAMAGE = 10;
	public static byte R = (byte) 0xff, G = (byte) 0xff, B = (byte) 0x00;
	
	private int m_x, m_y, m_w, m_h, m_offset, m_power, m_damage, m_pointStore;
	private boolean m_on, m_previous;
	private Rectangle m_collider;
	
	public Laser(int offset)
	{
		m_x = 0;
		m_y = 0;
		m_w = WIDTH;
		m_h = 0;
		m_on = false;
		m_offset = offset;
		m_power = MAX_POWER;
		m_damage = BASE_DAMAGE;
		m_pointStore = 0;
		m_collider = new Rectangle(m_x, m_y, m_w, m_h);
	}
	
	public int getPower()
	{
		return m_power;
	}
	
	public void turnOff()
	{
		m_on = false;
	}
	
	public void tick(boolean on, int x, int h)
	{
		m_on = on;
		m_x = x;
		m_h = h;
		m_collider.x = x;
		m_collider.height = h;
		
		if (on && m_power > 0 && (m_previous || m_power > RESTART_POWER)) {
			m_previous = true;
			m_power -= POWER_DRAIN;
		} else {
			m_power += POWER_REFILL;
			
			if (m_power > MAX_POWER)
				m_power = MAX_POWER;
			
			m_on = false;
			m_previous = false;
		}
	}
	
	public void checkCollisions(ArrayList<Enemy> entities, boolean right)
	{
		if (m_on) {
			if (right)
				m_collider.x += m_offset;
			
			for (int i = 0; i < entities.size(); i++) {
				Enemy e = entities.get(i);
				
				if (e.checkCollision(m_collider)) {
					boolean killed = e.damage(m_damage);
					
					if (killed)
						m_pointStore += e.getPoints();
				}
			}
			
			if (right)
				m_collider.x -= m_offset;
		}
	}
	
	public int emptyPointStore()
	{
		int p = m_pointStore;
		m_pointStore = 0;
		return p;
	}
	
	public void render(Bitmap dest)
	{
		if (m_on) {
			dest.fillRect(m_x, m_y, m_w, m_h, R, G, B);
			dest.fillRect(m_x + m_offset, m_y, m_w, m_h, R, G, B);
		}
	}
}
