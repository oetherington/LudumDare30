package ld30.com.main;

public class HealthPickup extends Pickup {
	public HealthPickup(int x, int y) 
	{
		super(x, y);
		m_sprite = Sprite.HEALTH;
	}
	
	protected void act(Player p) 
	{
		p.giveLife();
	}
}
