package ld30.com.main;

import java.util.ArrayList;
import java.util.Random;

public class StarField {
	public static int MIN_Z = 15, MAX_Z = 50;
	
	private int m_x, m_y, m_w, m_h;
	private ArrayList<Star> m_stars;
	private Random m_rand;
	
	public StarField(int x, int y, int w, int h, int density)
	{
		m_x = x;
		m_y = y;
		m_w = w;
		m_h = h;
		m_stars = new ArrayList<Star>();
		m_rand = new Random(System.nanoTime());

		for (int i = 0; i < density; i++)
			m_stars.add(new Star(m_rand.nextInt(m_w) + m_x, 
					m_rand.nextInt(m_h) + m_y, 
					m_rand.nextInt(MAX_Z - MIN_Z) + MIN_Z));
	}
	
	private void addStarAtTop()
	{
		m_stars.add(new Star(m_rand.nextInt(m_w) + m_x, m_y, 
				m_rand.nextInt(MAX_Z - MIN_Z) + MIN_Z));
	}
	
	public void tick()
	{
		Star s;
		for (int i = 0; i < m_stars.size(); i++) {
			s = m_stars.get(i);
			s.tick();
			
			if (s.getY() >= m_h) {
				m_stars.remove(i);
				addStarAtTop();
			}
		}
	}
	
	public void render(Bitmap dest)
	{
		for (int i = 0; i < m_stars.size(); i++)
			m_stars.get(i).render(dest);
	}
}
