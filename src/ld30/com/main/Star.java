package ld30.com.main;

public class Star {
	public static double VELOCITY = 100;
	public static byte R = (byte) 0xff, G = (byte) 0xff, B = (byte) 0xff;
	
	private int m_x, m_y, m_z;
	
	public Star(int x, int y, int z)
	{
		m_x = x;
		m_y = y;
		m_z = z;
	}
	
	public int getX()
	{
		return m_x;
	}
	
	public int getY()
	{
		return m_y;
	}
	
	public void tick()
	{
		m_y += (int) (VELOCITY / (double) (m_z));
	}
	
	public void render(Bitmap dest)
	{
		dest.setPixel(m_x, m_y, R, G, B);
	}
}
