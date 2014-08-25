package ld30.com.main;

public class HelpMenu extends Menu {

	public HelpMenu()
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
		display.renderText("In Seeing Double you play as two space ships", 70, 40);
		display.renderText("inexplicably linked in time and space", 70, 70);
		display.renderText("Move about with WSAD or the arrow keys and ", 70, 130);
		display.renderText("fire your laser with enter or space", 70, 160);
		
		display.renderText("Destroy your enemies", 70, 230);
		display.blit(500, 220, Sprite.DEBRIS1);
		display.blit(600, 220, Sprite.DRONE1);
		display.blit(700, 220, Sprite.EARTH);
		
		display.renderText("And collect points and extra lives", 280, 310);
		display.blit(70, 310, Sprite.POINTS10);
		display.blit(120, 310, Sprite.POINTS50);
		display.blit(170, 310, Sprite.POINTS100);
		display.blit(220, 310, Sprite.HEALTH);
		
		display.renderText("Be aware of your lasers power bar at the", 70, 380);
		display.renderText("bottom of the screen", 70, 410);
		display.renderText("When it runs too low it will turn red and", 70, 440);
		display.renderText("you wont be able to repower it until its", 70, 470);
		display.renderText("charged up a little", 70, 500);
	}
}
