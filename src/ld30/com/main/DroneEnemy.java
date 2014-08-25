package ld30.com.main;

import java.util.ArrayList;

public class DroneEnemy extends Enemy {
	public static final int WIDTH = 64, HEIGHT = 64, HEALTH = 275, POINTS = 60,
			ANIMATION_PERIOD = 60;
	public static final double SPEED = 0.5;
	public static final boolean CAN_EXPLODE = true;
	
	private int m_animation_phase, m_x_direction, m_minWidth, m_maxWidth;
	private Gun m_gun;
	
	public DroneEnemy(int x, int y, int viewportX, int viewportWidth,
			ArrayList<Enemy> entities) {
		super(x, y, WIDTH, HEIGHT, HEALTH, 30, SPEED, CAN_EXPLODE);

		m_animation_phase = -1;
		m_x_direction = 1;
		m_minWidth = viewportX + 20;
		m_maxWidth = viewportWidth - 20;
		m_gun = new Gun(x, y, WIDTH / 2 - Bullet.WIDTH / 2, 
				HEIGHT / 2 - Bullet.HEIGHT / 2, 2, 80, entities);
	}
	
	protected void tick()
	{
		if (m_alive) {
			m_animation_phase++;
			
			if (m_animation_phase >= ANIMATION_PERIOD * 2)
				m_animation_phase = 0;
			
			if (m_animation_phase == 0)
				m_img.blit(0, 0, Sprite.DRONE1);
			else if (m_animation_phase == ANIMATION_PERIOD)
				m_img.blit(0, 0, Sprite.DRONE2);
			
			if (m_x <= m_minWidth || m_x + WIDTH >= m_minWidth + m_maxWidth)
				m_x_direction = -m_x_direction;
			
			m_x += 4 * m_x_direction;
		} else {
			m_gun.stopFiring();
		}
		
		m_gun.updateParentLocation(m_x, m_y + (int) m_scroll);
		if (m_y + m_scroll >= 0)	/* Don't fire when off screen */
			m_gun.tick();
	}
	
	public Gun getGun()
	{
		return m_gun;
	}
	
	protected void prerender(Bitmap dest)
	{
		m_gun.render(dest);
	}
}
