package ld30.com.main;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Menu extends Bitmap {
	public static int KEY_PRESS_INTERVAL = 20, STAR_DENSITY = 60;
	
	protected boolean m_running;
	protected ArrayList<String> m_options;
	protected int m_selected, m_keyPressCounter, m_optionX, m_optionY;
	protected Display m_display;
	protected InputHandler m_input;
	protected StarField m_stars;
	
	public Menu()
	{
		super(Main.WIDTH, Main.HEIGHT);
		m_running = false;
		m_options = new ArrayList<String>();
		m_selected = 0;
		m_keyPressCounter = 0;
	}
	
	public void run(Display display, InputHandler input)
	{
		m_running = true;
		m_display = display;
		m_input = input;
		
		m_stars = new StarField(0, 0, m_display.getWidth(), m_display.getHeight(), STAR_DENSITY);
		
		final double fps = 60.0;
		final double secsPerTick = 1 / fps;
		double unprocessed = 0;
		int tickCount = 0, frameCount = 0;
		long preTime = System.nanoTime(), curTime, deltaTime;
		boolean ticked;
		
		while (m_running) {
			curTime = System.nanoTime();
			deltaTime = curTime - preTime;
			preTime = curTime;
			
			if (deltaTime < 0) 
				deltaTime = 0;
			if (deltaTime > 100000000) 
				deltaTime = 100000000;
			
			unprocessed += deltaTime / 1000000000.0;
			
			ticked = false;
			
			while (unprocessed > secsPerTick) {
				tick(input);
				tickCount++;
				ticked = true;
				unprocessed -= secsPerTick;
				
				if (tickCount % fps == 0) {
					if (Main.PRINT_FPS)
						System.out.println(frameCount + " fps");
					
					frameCount = 0;
					preTime -= 1000;
				}
			}
			
			if (ticked) {
				render(display);
				frameCount++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	protected void tick(InputHandler input)
	{
		m_keyPressCounter++;
		
		m_stars.tick();
		
		boolean up = input.keys[KeyEvent.VK_UP] || input.keys[KeyEvent.VK_W] || input.keys[KeyEvent.VK_NUMPAD8];
		boolean down = input.keys[KeyEvent.VK_DOWN] || input.keys[KeyEvent.VK_S] || input.keys[KeyEvent.VK_NUMPAD2];
		//boolean left = input.keys[KeyEvent.VK_LEFT] || input.keys[KeyEvent.VK_A] || input.keys[KeyEvent.VK_NUMPAD4];
		//boolean right = input.keys[KeyEvent.VK_RIGHT] || input.keys[KeyEvent.VK_D] || input.keys[KeyEvent.VK_NUMPAD6];
		boolean enter = input.keys[KeyEvent.VK_ENTER] || input.keys[KeyEvent.VK_SPACE];
		
		if (m_keyPressCounter > KEY_PRESS_INTERVAL) {
			if (up) {
				m_keyPressCounter = 0;
				
				if (m_selected > 0)
					m_selected--;
				else
					m_selected = m_options.size() - 1;
			}
			
			if (down) {
				m_keyPressCounter = 0;
				
				if (m_selected < m_options.size() - 1)
					m_selected++;
				else
					m_selected = 0;
			}
			
			if (enter) {
				m_keyPressCounter = 0;
				actOnSelected();
			}
		}
		
		postTick();
	}
	
	/* For overriding */
	protected void postTick() {}
	protected void actOnSelected() {}
	
	protected void render(Display display)
	{
		display.clear();
		
		m_stars.render(display.getFrameBuffer());
		
		for (int i = 0; i < m_options.size(); i++) {
			int x = m_optionX, y = m_optionY + 30 * i;
			
			if (m_selected == i) {
				display.blit(x - 40, y, Sprite.ARROW);
				display.renderText(m_options.get(i), x, y);
			} else {
				display.renderText(m_options.get(i), x, y);
			}
		}
		
		postRender(display);
		
		display.flip();
	}
	
	/* For overriding */
	protected void postRender(Display display) {}
}
