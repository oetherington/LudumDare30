package ld30.com.main;

import java.util.ArrayList;
import java.util.Random;

public class Viewport {	
	private int m_x, m_y, m_width, m_height, m_enemy_phase, m_enemy_density,
		m_next_enemy, m_pickup_phase, m_pickup_density, m_next_pickup;
	private Player m_player;
	private double m_scroll;
	private byte m_bg_r, m_bg_g, m_bg_b;
	private ArrayList<Enemy> m_entities;
	private ArrayList<Pickup> m_pickups;
	private StarField m_stars;
	private Random m_rand;
	
	public Viewport(int x, int y, int width, int height, Player player)
	{
		m_rand = new Random(System.nanoTime());
		
		m_x = x;
		m_y = y;
		m_width = width;
		m_height = height;
		m_player = player;
		m_enemy_phase = -1;
		m_enemy_density = 150;	/* The higher this is, the fewer enemies spawn */
		m_next_enemy = m_rand.nextInt(m_enemy_density);
		m_pickup_phase = -1;
		m_pickup_density = 400;
		m_next_pickup = m_rand.nextInt(m_pickup_density);
		m_bg_r = (byte) 0x01;
		m_bg_g = (byte) 0x01;
		m_bg_b = (byte) 0x01;
		m_scroll = 1.0;
		m_entities = new ArrayList<Enemy>();
		m_pickups = new ArrayList<Pickup>();
		m_stars = new StarField(x, y, width, height, 30);
	}
	
	public ArrayList<Enemy> getEntities()
	{
		return m_entities;
	}
	
	public ArrayList<Pickup> getPickups()
	{
		return m_pickups;
	}
	
	public void tick()
	{
		m_stars.tick();
		
		m_enemy_phase++;
		
		if (m_enemy_phase == m_next_enemy) {
			addRandomEntity(0);
			m_enemy_phase = 0;
			m_next_enemy = m_rand.nextInt(m_enemy_density) + m_enemy_density;
		}
		
		m_pickup_phase++;
		
		if (m_pickup_phase == m_next_pickup) {
			addRandomPickup();
			m_pickup_phase = 0;
			m_next_pickup = m_rand.nextInt(m_pickup_density) + m_pickup_density;
		}
		
		for (int i = 0; i < m_entities.size(); i++) {
			Enemy e = m_entities.get(i);
			
			if (e.getY() > m_height) {
				m_entities.remove(i);
				addRandomEntity(0);
			} else {
				e.tick(m_scroll);
			}
		}
		
		for (int i = 0; i < m_pickups.size(); i++) {
			Pickup p = m_pickups.get(i);
			
			if (p.getY() > m_height) {
				m_pickups.remove(i);
			} else {
				p.tick((int) m_scroll);
			}
		}
	}
	
	public void render(Bitmap dest)
	{
		dest.fillRect(m_x, m_y, m_width, m_height, m_bg_r, m_bg_g, m_bg_b);
		
		m_stars.render(dest);
		
		for (int i = 0; i < m_entities.size(); i++)
			m_entities.get(i).render(dest);
		
		for (int i = 0; i < m_pickups.size(); i++)
			m_pickups.get(i).render(dest);
	}
	
	public void setBackground(int r, int g, int b)
	{
		m_bg_r = (byte) r;
		m_bg_g = (byte) g;
		m_bg_b = (byte) b;
	}
	
	public void addEntity(Enemy e)
	{
		e.setX(e.getX() + m_x);
		m_entities.add(e);
	}
	
	public void addRandomEntity(int yOffs)
	{
		final int num_options = 3;
		final int selector = m_rand.nextInt(num_options);
		final int entity_size = 64;
		final int x = m_rand.nextInt(m_width - entity_size - 15) + 15;
		final int y = (-yOffs) - entity_size;
		
		switch (selector) {
		case 0:
			addEntity(new DebrisEnemy(x, y));
			break;
		case 1:
			addEntity(new DroneEnemy(x, y, m_x, m_width, m_entities));
			break;
		case 2:
			addEntity(new PlanetEnemy(x, y, m_x, m_player));
			break;
		}
	}
	
	public void addPickup(Pickup p)
	{
		p.setX(p.getX() + m_x);
		m_pickups.add(p);
	}
	
	public void addRandomPickup()
	{
		final int num_options = 2;
		final int selector = m_rand.nextInt(num_options);
		final int x = m_rand.nextInt(m_width - Pickup.WIDTH);
		final int y = -Pickup.HEIGHT;
		
		switch (selector) {
		case 0:
			addPickup(new HealthPickup(x, y));
			break;
		case 1:
			addPickup(new PointPickup(x, y));
			break;
		}
	}
}
