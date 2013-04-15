//"C:\\Users\\Jay\\Documents\\project_files\\video1.rgb";

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import com.googlecode.*;
import com.googlecode.javacv.cpp.opencv_core.IplImage;


import com.googlecode.javacv.cpp.opencv_imgproc.CvSubdiv2D;

import static com.googlecode.javacv.cpp.opencv_core.*;

public class sum {

  
   public static void main(String[] args) {
   	

	String fileName = "C:\\Users\\Jay\\Documents\\project_files\\video1.rgb";

	//String soundfile = "N:\\Courses\\CS-576\\project\\video1-jobsgates-full.wav";
   	int width = 352;
	int height = 288;
	ArrayList<IplImage> frames = new ArrayList<IplImage>() ;
	
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    
    IplImage iplimg = IplImage.create(width, height, IPL_DEPTH_8U, 3);
 
    IplImage previmg = IplImage.create(width, height, IPL_DEPTH_8U, 3);
    IplImage diffimg = IplImage.create(width, height, IPL_DEPTH_8U, 4);
    
    
    JFrame frame = new JFrame();
    JLabel label = new JLabel(new ImageIcon(result));
    frame.getContentPane().add(label, BorderLayout.CENTER);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
    
    int offset = 0;
    try {
	    File file = new File(fileName) ;
	    InputStream is = new FileInputStream(file) ;
	   /* InputStream soundis = new FileInputStream(soundfile);*/
	    System.out.println(file.length());
	    long len = file.length();
	    byte[] bytes = new byte[width * height * 3];
	    
	  /*  PlaySound playSound = new PlaySound(soundis);*/
        int numRead = 0;
        
        
        while (offset < file.length() ) {
        	
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
    		System.out.println("adding frames ");
    		
    		iplimg=IplImage.createFrom(img);
    		//result = iplimg.getBufferedImage();
    		result.setData(iplimg.getBufferedImage().getRaster());
			/*if(frames.isEmpty()){
				previmg = iplimg;
				
			}*/
			frames.add(iplimg);
			//cvSub(iplimg, previmg, diffimg, null);
			
			
		//	previmg = iplimg;
			frame.repaint();
			offset += numRead;
			
        }
        is.close();
    		
    	
		
		
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    System.out.println("no of frames"+frames.size());

   }
  
}