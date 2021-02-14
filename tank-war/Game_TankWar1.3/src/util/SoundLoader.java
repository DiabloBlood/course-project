package util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.InputStream;


public class SoundLoader {
		
	public static InputStream getAudioAsStream( String _file ) {
		return SoundLoader.class.getResourceAsStream("/sounds/"+_file);
	}
	
	public static AudioClip loadAudio( String _file ) {
		return Applet.newAudioClip(SoundLoader.class.getResource("/sound/"+_file));
	}
}
