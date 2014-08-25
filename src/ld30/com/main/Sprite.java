package ld30.com.main;

public class Sprite extends Bitmap {
	public static String IMG_PATH = "img/";
	
	public static Bitmap PLANETS;
	public static Sprite EARTH, MARS, SHIP1, SHIP2;
	
	public static Bitmap EXPLOSION;
	public static Sprite EXP_SMALL, EXP_BIG;
	
	public static Bitmap ENEMIES;
	public static Sprite DEBRIS1, DEBRIS2, DRONE1, DRONE2;
	
	public static Bitmap PICKUPS;
	public static Sprite HEALTH, POINTS10, POINTS50, POINTS100;
	
	public static Bitmap HEART;
	
	public static Bitmap BULLET;
	
	public static Bitmap ARROW;
	
	public static Bitmap FONT;
	
	public static void init() {
		PLANETS = new Bitmap(IMG_PATH + "planets.png");
		EARTH = new Sprite(PLANETS, 0, 0, 64, 64, 1);
		MARS = new Sprite(PLANETS, 64 * 1 + 2, 0, 64, 64, 1);
		SHIP1 = new Sprite(PLANETS, 132, 0, 64, 64, 1);
		SHIP2 = new Sprite(PLANETS, 198, 0, 64, 64, 1);
		
		EXPLOSION = new Bitmap(IMG_PATH + "explosion.png");
		EXP_SMALL = new Sprite(EXPLOSION, 0, 0, 64, 64, 1);
		EXP_BIG = new Sprite(EXPLOSION, 65, 0, 64, 64, 1);
		
		ENEMIES = new Bitmap(IMG_PATH + "enemies.png");
		DEBRIS1 = new Sprite(ENEMIES, 0, 0, 64, 64, 1);
		DEBRIS2 = new Sprite(ENEMIES, 65, 0, 64, 64, 1);
		DRONE1 = new Sprite(ENEMIES, 131, 0, 64, 64, 1);
		DRONE2 = new Sprite(ENEMIES, 196, 0, 64, 64, 1);
		
		PICKUPS = new Bitmap(IMG_PATH + "pickups.png");
		HEALTH = new Sprite(PICKUPS, 0, 0, 32, 32, 1);
		POINTS10 = new Sprite(PICKUPS, 34, 0, 32, 32, 1);
		POINTS50 = new Sprite(PICKUPS, 67, 0, 32, 32, 1);
		POINTS100 = new Sprite(PICKUPS, 100, 0, 32, 32, 1);
		
		HEART = new Bitmap(IMG_PATH + "heart.png");
		
		BULLET = new Bitmap(IMG_PATH + "bullet.png");
		
		ARROW = new Bitmap(IMG_PATH + "arrow.png");
		
		FONT = new Bitmap(IMG_PATH + "font.png");
	}
	
	public Sprite(Bitmap sheet, int xOffs, int yOffs, int w, int h, int scale)
	{
		super(w, h);
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				setPixel(x, y, 
						sheet.getR(x + xOffs, y + yOffs),
						sheet.getG(x + xOffs, y + yOffs),
						sheet.getB(x + xOffs, y + yOffs));
			}
		}
	}
}
