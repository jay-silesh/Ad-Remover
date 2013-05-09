


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.DataLine.Info;


/**
 * 
 * <Replace this with a short description of the class.>
 * 
 * @author Giulio
 */
public class PlaySound implements Runnable{

    private InputStream waveStream;

    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb

    /**
     * CONSTRUCTOR
     */
    public PlaySound(InputStream waveStream) {
    	this.waveStream = waveStream;
    	Thread t = new Thread(this, "sound thread");
    	t.start();
    }

    public void play() throws PlayWaveException {

	AudioInputStream audioInputStream = null;
	InputStream temp = new BufferedInputStream(this.waveStream);
	try {
	    audioInputStream = AudioSystem.getAudioInputStream(temp);
	} catch (UnsupportedAudioFileException e1) {
	    throw new PlayWaveException(e1);
	} catch (IOException e1) {
	    throw new PlayWaveException(e1);
	}

	// Obtain the information about the AudioInputStream
	AudioFormat audioFormat = audioInputStream.getFormat();
	Info info = new Info(SourceDataLine.class, audioFormat);
	AudioFileFormat aff = null ;
	
	try {
		aff = AudioSystem.getAudioFileFormat(audioInputStream);
	} catch (UnsupportedAudioFileException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	/*System.out.println("frame rate "+audioFormat.getFrameRate());
	System.out.println("frame size "+audioFormat.getFrameSize());
	System.out.println("sample rate "+audioFormat.getSampleRate());
	System.out.println("sameple size "+audioFormat.getSampleSizeInBits());
	System.out.println("channel "+audioFormat.getChannels());*/
	
	AudioFormat.Encoding pcm = AudioFormat.Encoding.PCM_SIGNED ;
	
	
	// opens the audio channel
	SourceDataLine dataLine = null;
	try {
	    dataLine = (SourceDataLine) AudioSystem.getLine(info);
	   
	    dataLine.open(audioFormat, this.EXTERNAL_BUFFER_SIZE);
	} catch (LineUnavailableException e1) {
	    throw new PlayWaveException(e1);
	}

	
	// Starts the music :P
	dataLine.start();

	int readBytes = 0;
	byte[] audioBuffer = new byte[this.EXTERNAL_BUFFER_SIZE];
	//byte[] audioBuffer = new byte[48000];
	
	try {
	    while (readBytes != -1) {
		readBytes = audioInputStream.read(audioBuffer, 0,
			audioBuffer.length);
		if (readBytes >= 0){
		    dataLine.write(audioBuffer, 0, readBytes);
		 //   System.out.println("audio buffer size "+audioBuffer.length);
		    Control[] control = dataLine.getControls();
			 
			}
	    }
	} catch (IOException e1) {
	    throw new PlayWaveException(e1);
	} finally {
	    // plays what's left and and closes the audioChannel
	    dataLine.drain();
	    dataLine.close();
	}

    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			this.play();
		} catch (PlayWaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
