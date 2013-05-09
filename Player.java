import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.googlecode.javacv.*;
import com.googlecode.javacv.cpp.opencv_core.CvNArrayIterator;
import com.googlecode.javacv.cpp.opencv_core.CvSet;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;


public class Player {

	private int WIDTH;
	private int HEIGHT;
	private static File file ;
	public Player(String filepath) {
		// TODO Auto-generated constructor stub
		this.file = new File(filepath);
		this.WIDTH = 352;
		this.HEIGHT = 288;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Player player = new Player("N:\\Courses\\CS-576\\project\\video2-messi-full.rgb");
		
		playfile(player);
		
	}
	
	private static void playfile(Player player) {
		// TODO Auto-generated method stub
		CanvasFrame canvas = new CanvasFrame("Player");
		
		FileInputStream input= null;
		try {
			input = new FileInputStream(player.file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DataInputStream data = new DataInputStream(input);
		BufferedImage bufimg = new BufferedImage(player.WIDTH, player.HEIGHT, BufferedImage.TYPE_INT_RGB);
		while(true){
			
			IplImage img = IplImage.create(player.WIDTH, player.HEIGHT, IPL_DEPTH_8U, 1);
			readframe(player,bufimg);
			
			img.createFrom(bufimg);
			canvas.showImage(img);
			
			KeyEvent key=null;
			try {
				key = canvas.waitKey(33);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(key!=null)
				break;
			img.release();
			
		}
	}
	private static void readframe(Player player, BufferedImage bufimg) {
		// TODO Auto-generated method stub
		
		InputStream is = null;
		try {
			is = new FileInputStream(player.file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long len = player.file.length();
	    byte[] bytes = new byte[(int)len];
	    
	    int offset = 0;
        int numRead = 0;
        try {
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			    offset += numRead;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int height = player.HEIGHT;
        int width = player.WIDTH;
        int ind = 0;
		for(int y = 0; y < height; y++){
	
			for(int x = 0; x < width; x++){
		 
				byte a = 0;
				byte r = bytes[ind];
				byte g = bytes[ind+height*width];
				byte b = bytes[ind+height*width*2]; 
				
				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
				//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
				bufimg.setRGB(x,y,pix);
				ind++;
			}
		}
		
		
	}

}
