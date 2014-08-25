package ld30.com.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JFrame;

public class Display extends Canvas {
	private static final long serialVersionUID = 1L;
	
	private final JFrame m_frame;
	private final Bitmap m_frameBuffer;
	private final BufferedImage m_displayImage;
	private final byte[] m_displayPixels;
	private final BufferStrategy m_bufferStrategy;
	private final Graphics m_graphics;
	
	public Display(int width, int height, String title, InputHandler listener)
	{
		Dimension s = new Dimension(width, height);
		setPreferredSize(s);
		setMinimumSize(s);
		setMaximumSize(s);
		
		addKeyListener(listener);
		addFocusListener(listener);
		
		m_frameBuffer = new Bitmap(width, height);
		m_displayImage = new BufferedImage(width, height, 
				BufferedImage.TYPE_3BYTE_BGR);
		m_displayPixels = 
				((DataBufferByte) m_displayImage.getRaster().getDataBuffer())
					.getData();
		
		m_frame = new JFrame(title); 
		m_frame.add(this);
		m_frame.pack();
		m_frame.setLocationRelativeTo(null);
		m_frame.setResizable(false);
		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.setVisible(true);
		
		createBufferStrategy(1);
		m_bufferStrategy = getBufferStrategy();
		m_graphics = m_bufferStrategy.getDrawGraphics();
	}
	
	public JFrame getFrame()
	{
		return m_frame;
	}
	
	public Bitmap getFrameBuffer()
	{
		return m_frameBuffer;
	}
	
	public void clear()
	{
		m_frameBuffer.clear((byte) 0x00);
	}
	
	public void flip()
	{
		m_frameBuffer.toByteArray(m_displayPixels);
		m_graphics.drawImage(m_displayImage, 0, 0, 
				m_frameBuffer.getWidth(), m_frameBuffer.getHeight(), null);
		m_bufferStrategy.show();
	}
	
	public void renderText(String text, int x, int y)
	{
		Font.render(text, x, y, m_frameBuffer);
	}
	
	public void blit(int xOffs, int yOffs, Bitmap src)
	{
		m_frameBuffer.blit(xOffs, yOffs, src);
	}
	
	public void gameOver(Game game)
	{
		clear();
		
		renderText("Game Over", 100, 100);
		renderText("You scored " + game.getPoints() + " points", 100, 130);
		renderText("Press enter to play again", 100, 160);
		
		flip();
	}
	
	public void render(Game game)
	{
		clear();
		
		game.renderBorder(m_frameBuffer);
		
		game.m_vp_left.render(m_frameBuffer);
		game.m_vp_right.render(m_frameBuffer);
		game.m_player.render(m_frameBuffer);
		
		drawHUD(game.m_player);
		
		flip();
	}
	
	public void drawHUD(Player player)
	{
		/* Lives */
		final int width = m_frameBuffer.getWidth();
		final int heartWidth = Sprite.HEART.getWidth();
		final int heartPadding = 10;
		final int lives = player.getLives();

		for (int i = 0; i < lives; i++)
			m_frameBuffer.blit(width - ((heartWidth + heartPadding) * (i + 1)), 
					heartPadding, Sprite.HEART);
		
		/* Score */
		player.renderScore(m_frameBuffer);
		
		/* Power bar */
		final int PB_SCALE = 5;
		final int PB_W = Laser.MAX_POWER / PB_SCALE;
		final int PB_H = 10;
		final int PB_PADDING = 5;
		final int PB_FILL_WIDTH = player.getLaser().getPower() / PB_SCALE;
		
		m_frameBuffer.fillRect(m_frameBuffer.getWidth() / 2 - PB_W / 2, 
				m_frameBuffer.getHeight() - PB_H - PB_PADDING, PB_W, PB_H, 
				(byte) 0x80, (byte) 0x80, (byte) 0x80);
		
		final boolean canPower = (Laser.RESTART_POWER / PB_SCALE) < PB_FILL_WIDTH;

		m_frameBuffer.fillRect(m_frameBuffer.getWidth() / 2 - PB_W / 2, 
				m_frameBuffer.getHeight() - PB_H - PB_PADDING, PB_FILL_WIDTH, PB_H, 
				(byte) 0xff, canPower ? (byte) 0xff : (byte) 0x00, (byte) 0x00);
	}
}
