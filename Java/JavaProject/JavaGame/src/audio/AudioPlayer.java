package audio;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

	public static int MENU = 0;
	public static int MAP = 1;
	public static int WIN = 2;
	public static int GAMEOVER = 3;
	public static int BONUS = 4;
	
	public static int JUMP = 0;
	public static int ATTACK = 1;


	private Clip[] songs, effects;
	private int currentSongId;


	public AudioPlayer() {
		loadSongs();
		loadEffects();
		playSong(MENU);
	}

	private void loadSongs() {
		String[] names = { "menu", "map","win","gameover","bonus" };
		songs = new Clip[names.length];
		for (int i = 0; i < songs.length; i++)
			songs[i] = getClip(names[i]);
	}

	private void loadEffects() {
		String[] effectNames = {"jump", "attack" };
		effects = new Clip[effectNames.length];
		for (int i = 0; i < effects.length; i++)
			effects[i] = getClip(effectNames[i]);
		
	}

	private Clip getClip(String name) {
		URL url = getClass().getResource("/audio/" + name + ".wav");
		AudioInputStream audio;

		try {
			audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

			e.printStackTrace();
		}

		return null;

	}



	public void stopSong() {
		if (songs[currentSongId].isActive())
			songs[currentSongId].stop();
	}



	public void playEffect(int effect) {
		effects[effect].setMicrosecondPosition(0);
		effects[effect].start();
	}

	public void playSong(int song) {
		stopSong();

		currentSongId = song;
		
		songs[currentSongId].setMicrosecondPosition(0);
		songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
	}




}
