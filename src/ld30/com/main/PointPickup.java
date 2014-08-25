package ld30.com.main;

import java.util.Random;

public class PointPickup extends Pickup {
	private int m_value;
	
	public PointPickup(int x, int y) 
	{
		super(x, y);
		
		Random r = new Random(System.nanoTime());
		int selector = r.nextInt(100);
		
		if (selector > 90) {
			m_value = 100;
			m_sprite = Sprite.POINTS100;
		} else if (selector > 60) {
			m_value = 50;
			m_sprite = Sprite.POINTS50;
		} else {
			m_value = 10;
			m_sprite = Sprite.POINTS10;
		}
	}
	
	protected void act(Player p) 
	{
		p.givePoints(m_value);
	}
}
