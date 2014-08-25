package ld30.com.main;

public class Entity {
	protected int m_x, m_y, m_w, m_h;
	
	public Entity()
	{
		
	}
	
	public Entity(int x, int y, int w, int h)
	{
		m_x = x;
		m_y = y;
		m_w = w;
		m_h = h;
	}
	
	public int getX()
	{
		return m_x;
	}
	
	public int getY()
	{
		return m_y;
	}
	
	public void setX(int x)
	{
		m_x = x;
	}
	
	public void setY(int y)
	{
		m_y = y;
	}
}
