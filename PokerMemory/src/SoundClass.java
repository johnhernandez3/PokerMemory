/*The sound class was made taking as reference the video from the link: https://www.youtube.com/watch?v=ivCS9GrFpFA.
 * Audio clips used for this project are from http://soundbible.com/tags-game.html, a free sound fx website. We do not own
 * any of the copyrighted material.*/

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundClass {
	public static void Flip(){
		try{
			Clip clip = AudioSystem.getClip();
			AudioInputStream flip = AudioSystem.getAudioInputStream(SoundClass.class.getResource("Flip.wav"));
			clip.open(flip);
			clip.loop(0);
			Thread.sleep(150);
			clip.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void win(){
		try{
			Clip clip = AudioSystem.getClip();
			AudioInputStream win = AudioSystem.getAudioInputStream(SoundClass.class.getResource("JebIsAMess.wav"));
			clip.open(win);
			clip.loop(0);
			Thread.sleep(3500);
			clip.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}


}