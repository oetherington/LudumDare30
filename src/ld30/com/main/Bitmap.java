package ld30.com.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Bitmap {
	public static final int BPP = 4;
	
	protected int m_width, m_height;
	protected byte[] m_pixels;
	
	public Bitmap(int width, int height)
	{
		m_width = width;
		m_height = height;
		m_pixels = new byte[width * height * BPP];
	}
	
	public Bitmap(int width, int height, int[] pixels)
	{
		this(width, height);
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int i = x + y * width;
				setPixel(x, y, 
						(byte) ((pixels[i] >> 16) & 0xff),
						(byte) ((pixels[i] >> 8) & 0xff),
						(byte) (pixels[i] & 0xff));
			}
		}
	}
	
	public Bitmap(String file)
	{
		try {
			BufferedImage img = ImageIO.read(new File(file));
			
			int w = img.getWidth();
			int h = img.getHeight();
			
			int[] pixels = new int[w * h];
			
			img.getRGB(0, 0, w, h, pixels, 0, w);
			
			m_width = w;
			m_height = h;
			m_pixels = new byte[w * h * BPP];
			
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					int i = x + y * w;
					setPixel(x, y, 
							(byte) ((pixels[i] >> 16) & 0xff),
							(byte) ((pixels[i] >> 8) & 0xff),
							(byte) (pixels[i] & 0xff));
				}
			}
		} catch(IOException e) {
			String canonicalPath = file;
			
			try {
				canonicalPath = new File(".").getCanonicalPath() + file;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			System.out.println("Couldn't load: " + canonicalPath);
			e.printStackTrace();
		}
	}
	
	public int getWidth()
	{
		return m_width;
	}
	
	public int getHeight()
	{
		return m_height;
	}
	
	public byte getR(int x, int y)
	{
		int index = (x + y * m_width) * BPP + 3;
		return (index < m_pixels.length) ? m_pixels[index] : -1;
	}
	
	public byte getG(int x, int y)
	{
		int index = (x + y * m_width) * BPP + 2;
		return (index < m_pixels.length) ? m_pixels[index] : -1;
	}
	
	public byte getB(int x, int y)
	{
		int index = (x + y * m_width) * BPP + 1;
		return (index < m_pixels.length) ? m_pixels[index] : -1;
	}
	
	public byte[] getPixels()
	{
		return m_pixels;
	}
	
	public void clear(byte shade)
	{
		Arrays.fill(m_pixels, shade);
	}

	public void setPixel(int x, int y, byte r, byte g, byte b)
	{
		/* Check we're in range */
		if (x < 0 || x >= m_width || y < 0 || y >= m_height)
			return;
		
		/* Check if it's the ignore color */
		if (r == (byte) 0xff && g == (byte) 0x00 && b == (byte) 0xff)
			return;
		
		/* Check if it's a null pixel */
		if ((r | g | b) == 0)
			return;
		
		/* ABGR because Windows and Java or something... */
		final int i = (x + y * m_width) * BPP;
		m_pixels[i    ] = (byte) 0xff;
		m_pixels[i + 1] = b;
		m_pixels[i + 2] = g;
		m_pixels[i + 3] = r;
	}
	
	public int getPixelInt(int i)
	{
		final int a = ((int) m_pixels[i * BPP    ]) << 24;
		final int r = ((int) m_pixels[i * BPP + 1]) << 16;
		final int g = ((int) m_pixels[i * BPP + 2]) << 8;
		final int b = ((int) m_pixels[i * BPP + 3]);
			
		return a | r | g | b;
	}
	
	public void fillRect(int x, int y, int w, int h, int r, int g, int b)
	{
		for (int x0 = x; x0 < x + w; x0++)
			for (int y0 = y; y0 < y + h; y0++)
				setPixel(x0, y0, (byte) r, (byte) g, (byte) b);
	}
	
	public void blit(int xOffs, int yOffs, Bitmap src)
	{
		byte r, g, b;
		
		for (int x = 0; x < src.getWidth(); x++) {
			for (int y = 0; y < src.getHeight(); y++) {
				r = src.getR(x, y);
				g = src.getG(x, y);
				b = src.getB(x, y);
				
				setPixel(x + xOffs, y + yOffs, r, g, b);
			}
		}
	}
	
	public void toByteArray(byte[] dest)
	{
		for (int i = 0; i < m_width * m_height; i++) {
			dest[i * 3    ] = m_pixels[i * 4 + 1];
			dest[i * 3 + 1] = m_pixels[i * 4 + 2];
			dest[i * 3 + 2] = m_pixels[i * 4 + 3];
		}
	}
	
	public void toIntArray(int[] dest)
	{
		int a, r, g, b;
		
		for (int i = 0; i < m_width * m_height; i++) {
			a = ((int) m_pixels[i * BPP    ]) << 24;
			r = ((int) m_pixels[i * BPP + 1]) << 16;
			g = ((int) m_pixels[i * BPP + 2]) << 8;
			b = ((int) m_pixels[i * BPP + 3]);
			
			dest[i] = a | r | g | b;
		}
	}
}
