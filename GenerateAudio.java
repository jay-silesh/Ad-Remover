import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


public class GenerateAudio {

	public static int BUFFER_SIZE = 524288;
	public static ArrayList<Byte> soundfiles = new ArrayList<Byte>();
	//public static byte[] soundfiles;
	
	
	public static void getaudio(InputStream soundstream) {
		// TODO Auto-generated method stub
		
		InputStream inputstream = new BufferedInputStream(soundstream);
		/*AudioInputStream audiostream = null;
		
		try {
			audiostream = AudioSystem.getAudioInputStream(inputstream);
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		generateaudiobytes(inputstream);
		
		System.out.println("sound files size "+soundfiles.size());
		
		System.out.println("Writing wav file");
		
	}

	

	

	private static void generateaudiobytes(InputStream inputstream) {
		// TODO Auto-generated method stub
		
		
		int prevframe=0,buffersize=4000;
		int prevend ;
		System.out.println(DifferentiatingVideos.maxstructure.size());
		byte[] audiobytes = null;
		
		File rawSound = new File(sum.audio_output_file);
		
		AudioFileFormat otfileformat=null;
		try {
			otfileformat = AudioSystem.getAudioFileFormat(sum.soundfile);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		AudioFileFormat.Type ottype = otfileformat.getType();
		AudioFormat af = otfileformat.getFormat();
		
		AudioInputStream audiostream = null;
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		try {
			
			audiostream = AudioSystem.getAudioInputStream(inputstream);
			
			
			byte[] framebuffer = new byte[buffersize];
			boolean write = true;
			
			/*System.out.println("max 0 "+DifferentiatingVideos.maxstructure.get(0).start_frame);
			System.out.println("max 1 "+DifferentiatingVideos.maxstructure.get(1).start_frame);*/
			
			System.out.println("no of shots "+DifferentiatingVideos.maxstructure.size());
			for(int i=0; i< sum.complete_video.size();i++){
				
				int bytesread = inputstream.read(framebuffer);
				write=true;
				if(bytesread == -1)
					break;
				
				for(int j=0; j<DifferentiatingVideos.maxstructure.size(); j++){
					
					int time = DifferentiatingVideos.maxstructure.get(j).end_frame - DifferentiatingVideos.maxstructure.get(j).similar_frames;
					 time = time/24;
					 System.out.println("shot "+time);
					
					if( (i >= DifferentiatingVideos.maxstructure.get(j).start_frame) && (i <= DifferentiatingVideos.maxstructure.get(j).end_frame)){
						
						write = false;
						outputstream.write(framebuffer, 0, bytesread);
					}
					
				}
				
				/*if(write){
					
					outputstream.write(framebuffer, 0, bytesread);
				}*/
				
			}
			
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		byte[] result = outputstream.toByteArray();
		
		ByteArrayInputStream inps = new ByteArrayInputStream(result);
		AudioInputStream ais = new AudioInputStream(inps, af, result.length/af.getFrameSize());
	
		try {
			int bytewrite = AudioSystem.write(ais, ottype, rawSound);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	

}
