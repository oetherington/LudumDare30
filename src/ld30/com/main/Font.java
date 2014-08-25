package ld30.com.main;

public class Font {
	public static final int LENGTH = 36, CHAR_WIDTH = 20, 
			CHAR_HEIGHT = Sprite.FONT.getHeight();
	
	public static Sprite[] FONT;
	
	static {
		FONT = new Sprite[LENGTH];
		init((byte) 0xfe, (byte) 0x00, (byte) 0xff);
	}
	
	public static void init(byte r, byte g, byte b)
	{
		int w = Sprite.FONT.getWidth(), h = Sprite.FONT.getHeight();
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int pixel = Sprite.FONT.getPixelInt(x + y * w);
				
				if (pixel != 0x000000 && pixel != 0xff00ff)
					Sprite.FONT.setPixel(x, y, r, g, b);
			}
		}
		
		for (int i = 0; i < LENGTH; i++)
			FONT[i] = new Sprite(Sprite.FONT, CHAR_WIDTH * i, 0,
					CHAR_WIDTH, CHAR_HEIGHT, 1);
	}
	
	private static int getCharIndex(char c)
	{
		if (c >= 'a' && c <= 'z')
			return (int) c - 'a';
		
		if (c >= 'A' && c <= 'Z')
			return (int) c - 'A';
		
		if (c >= '0' && c <= '9')
			return (int) c - '0' + 26;
		
		return 0;
	}
	
	public static void render(String text, int x, int y, Bitmap dest)
	{
		for (int i = 0; i < text.length(); i++) {
			switch (text.charAt(i)) {
			case ' ':
				x += CHAR_WIDTH;
				break;
			case '\n':
				y += CHAR_HEIGHT;
				break;
			default:
				dest.blit(x, y, FONT[getCharIndex(text.charAt(i))]);
				x += CHAR_WIDTH;
			}
		}
	}
	
	public static void render(String text, int x, int y, Bitmap dest, 
			byte r, byte g, byte b)
	{
		init(r, g, b);
		
		for (int i = 0; i < text.length(); i++) {
			switch (text.charAt(i)) {
			case ' ':
				x += CHAR_WIDTH;
				break;
			case '\n':
				y += CHAR_HEIGHT;
				break;
			default:
				dest.blit(x, y, FONT[getCharIndex(text.charAt(i))]);
				x += CHAR_WIDTH;
			}
		}
	}
}
