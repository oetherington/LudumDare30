package ld30.com.main;

import java.util.Random;

public class DebrisEnemy extends Enemy {
	public static final int WIDTH = 64, HEIGHT = 64, HEALTH = 100;
	
	public DebrisEnemy(int x, int y) {
		super(x, y, WIDTH, HEIGHT, HEALTH, 10, 1.0, true);
		
		Random r = new Random(System.nanoTime());
		
		if (r.nextInt() % 2 == 0)
			m_img.blit(0, 0, Sprite.DEBRIS1);
		else
			m_img.blit(0, 0, Sprite.DEBRIS2);
	}
}
