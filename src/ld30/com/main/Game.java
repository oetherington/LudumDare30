package ld30.com.main;

import java.awt.event.KeyEvent;

public class Game {
	public static final int BORDER_WIDTH = 6;
	
	public Player m_player;
	public Viewport m_vp_left, m_vp_right;
	private int m_width, m_height,  m_border_phase, m_border_r_dir, 
		m_border_g_dir, m_border_b_dir;
	private byte m_border_r, m_border_g, m_border_b;
	
	public Game(int width, int height)
	{
		m_player = new Player(width / 2, height);
		m_vp_left = new Viewport(0, 0, width / 2 - BORDER_WIDTH / 2, height, m_player);
		m_vp_right = new Viewport(width / 2 + BORDER_WIDTH / 2, 0, width / 2, height, m_player);
		m_width = width;
		m_height = height;
		m_border_phase = -1;
		m_border_r_dir = 0;
		m_border_g_dir = 0;
		m_border_b_dir = 0;
		m_border_r = (byte) 0x00;
		m_border_g = (byte) 0x00;
		m_border_b = (byte) 0x00;
	}

	public void cycleBorderColor()
	{
		if (m_border_phase == 10)
			m_border_phase = 0;
		else
			m_border_phase++;
		
		if (m_border_r == 0x00)
			m_border_r_dir = 0;
		else if (m_border_r == 0xff)
			m_border_r_dir = 1;
		
		if (m_border_r_dir == 0)
			m_border_r++;
		else
			m_border_r--;
		
		if (m_border_g == 0x00)
			m_border_g_dir = 0;
		else if (m_border_g == 0xff)
			m_border_g_dir = 1;
		
		if (m_border_phase % 2 == 0)
			if (m_border_g_dir == 0)
				m_border_g++;
			else
				m_border_g--;
		
		if (m_border_b == 0x00)
			m_border_b_dir = 0;
		else if (m_border_b == 0xff)
			m_border_b_dir = 1;
		
		if (m_border_phase % 3 == 0)
			if (m_border_b_dir == 0)
				m_border_b++;
			else
				m_border_b--;
	}
	
	public boolean isOver()
	{
		return m_player.isDead();
	}
	
	public int getPoints()
	{
		return m_player.getPoints();
	}
	
	public void tick(boolean[] keys)
	{		
		boolean up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W] || keys[KeyEvent.VK_NUMPAD8];
		boolean down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S] || keys[KeyEvent.VK_NUMPAD2];
		boolean left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A] || keys[KeyEvent.VK_NUMPAD4];
		boolean right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D] || keys[KeyEvent.VK_NUMPAD6];
		boolean fire = keys[KeyEvent.VK_ENTER] || keys[KeyEvent.VK_SPACE];
		
		m_player.checkCollisions(m_vp_left.getEntities(), false);
		m_player.checkCollisions(m_vp_right.getEntities(), true);
		
		m_player.checkPickupCollisions(m_vp_left.getPickups());
		m_player.checkPickupCollisions(m_vp_right.getPickups());
		
		m_vp_left.tick();
		m_vp_right.tick();
		m_player.tick(up, down, left, right, fire);
		
		cycleBorderColor();
	}
	
	public void renderBorder(Bitmap dest)
	{
		dest.fillRect(m_width / 2 - BORDER_WIDTH / 2, 0, 
				BORDER_WIDTH, m_height, m_border_r, m_border_g, m_border_b);
	}
}
