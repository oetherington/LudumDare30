package ld30.com.main;

public class MainMenu extends Menu {
	private Entity m_earth, m_mars;
	
	public MainMenu()
	{
		m_optionX = 100;
		m_optionY = 40;
		
		m_options.add("Play");
		m_options.add("Help");
		m_options.add("About");
		
		m_earth = new Entity(200, 200, 64, 64);
		m_mars = new Entity(200, 200, 64, 64);
	}
	
	protected void postTick() 
	{
		m_earth.setX((int) 
				(Math.sin(System.currentTimeMillis() % 10000 / 10000.0 * Math.PI * 2) * 350) + 750);
		m_earth.setY((int) 
				(Math.cos(System.currentTimeMillis() % 10000 / 10000.0 * Math.PI * 2) * 350) + 500);
		m_mars.setX((int) 
				(Math.sin(5000 + (System.currentTimeMillis()) % 7000 / 7000.0 * Math.PI * 2) * 400) + 750);
		m_mars.setY((int) 
				(Math.cos(5000 + (System.currentTimeMillis()) % 7000 / 7000.0 * Math.PI * 2) * 400) + 500);
	}
	
	protected void actOnSelected() 
	{	
		switch (m_selected) {
		case 0:	/* Play */
			m_running = false;
			break;
		case 1:	/* Help */
			new HelpMenu().run(m_display, m_input);
			break;
		case 2:	/* About */
			new AboutMenu().run(m_display, m_input);
			break;
		}
	}
	
	protected void postRender(Display display) 
	{
		display.renderText(Main.TITLE, 640, 340);
		
		display.blit(m_earth.getX(), m_earth.getY(), Sprite.EARTH);
		display.blit(m_mars.getX(), m_mars.getY(), Sprite.MARS);
		
		if (Main.previousScore > -1) {
			display.renderText("Game Over", 590, 470);
			display.renderText("You scored " + Main.previousScore, 590, 500);
		}
	}
}
