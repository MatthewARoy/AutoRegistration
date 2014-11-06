package Test;

import java.applet.Applet;
import java.applet.AudioClip;


/**
 * This class handles playing sounds and 
 * adds some utility functions to the AudioClip class.
 * 
 * Note, Java only supports the formats: wav, aiff, au, mid, rmf.
 * 
 * @author Robert C. Duvall
 */
public class Sound {
    // OS-independent relative resource locations (like URLs)
    private static final String RESOURCE_LOCATION = "/sounds/";
    // underlying implementation
    private AudioClip myClip;


    /**
     * Construct a sound with the data referred to by the given filename.
     */
    public Sound (String filename) {
        setSound(filename);
    }

    /**
     * Set this sound to the data referred to by the given filename.
     */
    public void setSound (String filename) {
        myClip = Applet.newAudioClip(getClass().getResource(RESOURCE_LOCATION + filename));
    }

    /**
     * Play the given sound.
     */
    public void play () {
        myClip.play();
    }
    
    public void playLooped() {
        new Thread(
                  new Runnable() {
                      public void run() {
                          try {
                              myClip.play();
                          } catch (Exception e) {
                              e.printStackTrace();
                          }
                      }
                  }).start();
        
    }

    /**
     * Stop playing the given sound.
     */
    public void stop () {
        myClip.stop();
    }
}
