
import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.swing.*;


public class NormalPlayer {

  
   public static void main(String[] args) {
   	

	/*String fileName = "N:\\Courses\\CS-576\\project\\video4.rgb";
	String soundfile = "N:\\Courses\\CS-576\\project\\audio4.wav";*/
	
	String fileName = args[0];
	String soundfile = args[1];
	
   	int width = 352;
	int height = 288;
	
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    
    // Use a label to display the image
    JFrame frame = new JFrame();
    JLabel label = new JLabel(new ImageIcon(img));
    frame.getContentPane().add(label, BorderLayout.CENTER);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
    
    int offset = 0;
    try {
	    File file = new File(fileName);
	    InputStream is = new FileInputStream(file);
	    InputStream soundis = new FileInputStream(soundfile);
	    //System.out.println(file.length());
	    //System.out.println("sound file length "+new File(soundfile).length());
	    long len = file.length();
	    byte[] bytes = new byte[width * height * 3];
	    
	    PlaySound playSound = new PlaySound(soundis);
        int numRead = 0;
        
        
        while (offset < file.length() ) {
        	long start = System.nanoTime();
        	int ind = 0;
        	numRead=is.read(bytes, 0,width*height*3);
        	if(numRead < 0)
        		break;
        	
    		for(int y = 0; y < height; y++){
    	
    			for(int x = 0; x < width; x++){
    		 
    				byte a = 0;
    				byte r = bytes[ind+height*width*2];
    				byte g = bytes[ind+height*width];
    				byte b = bytes[ind]; 
    				
    				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
    				//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
    				img.setRGB(x,y,pix);
    				ind++;
    			}
    		}
    		
    		long end = System.nanoTime();
    //		System.out.println("time "+(end-start));
    		long sleeptime = (long) (42.5-((end-start) / Math.pow(10, 6)));
    		if(sleeptime<0)
    			sleeptime=0;
    		try {
				Thread.sleep(sleeptime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
        	frame.repaint();
        	offset += numRead;
        	
        }
    
    		
    	
		is.close();
		
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    

   }
  
}