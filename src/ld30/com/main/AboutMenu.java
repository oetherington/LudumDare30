package ld30.com.main;

public class AboutMenu extends Menu {

	public AboutMenu()
	{
		m_optionX = Main.WIDTH - 150;
		m_optionY = Main.HEIGHT - 50;
		m_options.add("Return");
	}
	
	protected void actOnSelected()
	{
		m_running = false;
	}
	
	protected void postRender(Display display) 
	{
		display.renderText("Made in 72 hours for the Ludum Dare 30 game jam", 50, 100);
		display.renderText("by Ollie Etherington", 50, 130);
		
		display.renderText("All code and assets are free software under the", 50, 180);
		display.renderText("terms of the GNU GPLv3 but the code is REALLY", 50, 210);
		display.renderText("messy and you probably dont want it", 50, 240);
	}
}
