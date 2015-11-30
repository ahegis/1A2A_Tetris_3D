package Physique;

import com.jme3.system.AppSettings;


public class Main {

	public static void main(String[] args){
	    final Appli app = new Appli();

	    app.setShowSettings(false);

	    AppSettings settings = new AppSettings(true);

	    settings.put("Width", 1280);

	    settings.put("Height", 720);

	    settings.put("Title", "TeTris 3D");

	    settings.put("VSync", true);

	    //Anti-Aliasing

	    settings.put("Samples", 4);

	    app.setSettings(settings);

	    app.start();
	}

}
