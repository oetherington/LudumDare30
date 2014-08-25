package ld30.com.main;

import java.util.ArrayList;

public class Gun {
	private int m_parentX, m_parentY, m_xOffs, m_yOffs, m_bulletSpeed, 
		m_fireRate, m_counter;
	private ArrayList<Bullet> m_bullets;
	
	public Gun(int parentX, int parentY, int xFireOffs, int yFireOffs, 
			int bulletSpeed, int fireRate, ArrayList<Enemy> entities)
	{
		m_parentX = parentX;
		m_parentY = parentY;
		m_xOffs = xFireOffs;
		m_yOffs = yFireOffs;
		m_bulletSpeed = bulletSpeed;
		m_fireRate = fireRate;
		m_counter = 0;
		m_bullets = new ArrayList<Bullet>();
	}
	
	public void updateParentLocation(int x, int y)
	{
		m_parentX = x;
		m_parentY = y;
	}
	
	public void fire()
	{
		Sound.FIRE_GUN.play();
		
		m_bullets.add(new Bullet(m_parentX + m_xOffs, m_parentY + m_yOffs, 
				m_bulletSpeed));
	}
	
	public void stopFiring()
	{
		m_fireRate = Integer.MAX_VALUE;
	}
	
	public ArrayList<Bullet> getBullets()
	{
		return m_bullets;
	}
	
	public void tick()
	{
		m_counter++;
		
		if (m_counter % m_fireRate == 0)
			fire();
		
		for (int i = 0; i < m_bullets.size(); i++) {
			Bullet b = (Bullet) m_bullets.get(i);
			
			if (b.finished())
				m_bullets.remove(i);
			else
				b.tick();
		}
	}
	
	public void render(Bitmap dest)
	{
		for (int i = 0; i < m_bullets.size(); i++)
			m_bullets.get(i).render(dest);
	}
}
