package ld30.com.main;

import java.util.Random;

public class PlanetEnemy extends Enemy {
	public static final int WIDTH = 64, HEIGHT = 64, HEALTH = 450, POINTS = 100;
	public static final double SPEED = 0.7, FOLLOW_SPEED = 1;
	public static final boolean CAN_EXPLODE = true;
	
	private Player m_player;
	private int m_offset;
	
	public PlanetEnemy(int x, int y, int viewportX, Player player) {
		super(x, y, WIDTH, HEIGHT, HEALTH, POINTS, SPEED, CAN_EXPLODE);
		
		m_offset = viewportX;
		m_player = player;
		
		m_img.blit(0, 0, 
				(new Random(System.nanoTime()).nextInt() % 2 == 0) 
				? Sprite.EARTH 
				: Sprite.MARS);
	}
	
	protected void tick()
	{
		final int pX = m_player.getX() + m_offset;
		final int dX = pX - m_x;
		
		if (dX != 0) {
			final int normX = dX / Math.abs(dX);
			m_x += FOLLOW_SPEED * normX;
		}
	}
}
