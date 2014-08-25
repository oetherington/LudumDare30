package ld30.com.main;

public class Main {
	public static final int WIDTH = 1024, HEIGHT = 700;
	public static final boolean PRINT_FPS = false;
	public static final String TITLE = "Seeing Double";
	
	private static Display display;
	private static InputHandler inputHandler;
	private static Game game;
	private static boolean running;
	public static int previousScore;
	
	public static void main(String[] args)
	{	
		init();
		start();
	}
	
	public static void init()
	{
		Sprite.init();
		Sound.init();
		inputHandler = new InputHandler();
		display = new Display(WIDTH, HEIGHT, TITLE, inputHandler);
		running = false;
		previousScore = -1;
	}
	
	public static void start()
	{
		while (true) {
			new MainMenu().run(display, inputHandler);
			newGame();
		}
	}
	
	public static void stop()
	{
		running = false;
	}
	
	private static void newGame()
	{
		running = true;
		game = new Game(WIDTH, HEIGHT);
		Sound.START.play();
		run();
	}
	
	private static void run()
	{
		final double fps = 60.0;
		final double secsPerTick = 1 / fps;
		double unprocessed = 0;
		int tickCount = 0, frameCount = 0;
		long preTime = System.nanoTime(), curTime, deltaTime;
		boolean ticked;
		
		while (running) {
			//System.out.println
			curTime = System.nanoTime();
			deltaTime = curTime - preTime;
			preTime = curTime;
			
			if (deltaTime < 0) 
				deltaTime = 0;
			if (deltaTime > 100000000) 
				deltaTime = 100000000;
			
			unprocessed += deltaTime / 1000000000.0;
			
			ticked = false;
			
			while (unprocessed > secsPerTick) {
				tick();
				tickCount++;
				ticked = true;
				unprocessed -= secsPerTick;
				
				if (tickCount % fps == 0) {
					if (PRINT_FPS)
						System.out.println(frameCount + " fps");
					
					frameCount = 0;
					preTime -= 1000;
				}
			}
			
			if (ticked) {
				render();
				frameCount++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void tick()
	{
		if (game.isOver()) {
			running = false;
			previousScore = game.getPoints();
			
			try {
				Thread.sleep(650);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			game.tick(inputHandler.keys);
		}
	}
	
	private static void render()
	{
		display.render(game);
	}
	
	public static Display getDisplay()
	{
		return display;
	}
}