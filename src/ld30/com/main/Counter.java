package ld30.com.main;

public class Counter extends Bitmap {
	public static final int CELL_WIDTH = 32, CELL_HEIGHT = 32, CELL_PADDING = 1,
			DIGITS = 6;
	
	private int m_x, m_y, m_points;
	
	public Counter(int x, int y)
	{
		super(CELL_WIDTH * DIGITS, CELL_HEIGHT);
		
		m_x = x;
		m_y = y;
		m_points = 0;
		updateImage();
	}
	
	public int getPoints()
	{
		return m_points;
	}
	
	public void addPoints(int points)
	{
		m_points += points;
		updateImage();
	}
	
	private void putDigitInPosition(char digit, int position)
	{
		Font.render("" + digit, position * CELL_WIDTH, 0, this);
	}
	
	private void updateImage()
	{
		clear((byte) 0);
		
		String str = String.valueOf(m_points);
		
		while (str.length() < DIGITS)
			str = "0" + str;
		
		for (int i = 0; i < DIGITS; i++)
			putDigitInPosition(str.charAt(i), i);
	}
	
	public void render(Bitmap dest)
	{
		dest.blit(m_x, m_y, this);
	}
}
