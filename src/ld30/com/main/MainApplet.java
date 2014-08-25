package ld30.com.main;

import java.applet.Applet;

public class MainApplet extends Applet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public void init() {
		Sprite.IMG_PATH = "../img/";
		Sound.SND_PATH = "../snd/";
		
		Main.init();
		Main.getDisplay().getFrame().show();
	}

	public void start() {
		Main.start();
	}

	public void stop() {
		Main.stop();
	}
}
