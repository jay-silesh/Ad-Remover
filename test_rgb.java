import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_COMP_CHISQR;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCompareHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.util.ArrayList;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvSplit;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;


import com.googlecode.javacv.cpp.opencv_core.IplImage;


public class test_rgb {

static int total_frame_size;
	
	public static int width = 352;
	public static int height = 288;
	public static double feature_detection_threshold= 0.35;
	
	public static void main4(String[] args) {
		// TODO Auto-generated method stub
		
	//Contains all the frames which have gone through Histogram process..
			ArrayList<shots_structure> ss_hist_frames=new ArrayList<shots_structure>();
			
			//Contains all the frames which have gone through the Feature_detection process..
			ArrayList<shots_structure> ss_fd_frames=new ArrayList<shots_structure>();
			ArrayList<shots_structure> ss_fd_frames_convert=new ArrayList<shots_structure>();
			
			long start=System.currentTimeMillis();
			String fileName = "C:\\Users\\Jay\\Documents\\project_files\\video3.rgb";
	
			
		  	BufferedImage result = new BufferedImage(sum.width, sum.height, BufferedImage.TYPE_BYTE_INDEXED);
		  	//BufferedImage result = new BufferedImage(sum.width, sum.height, BufferedImage.TYPE_4BYTE_ABGR);
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
			int channels=3;
			
			
		  	JFrame frame = new JFrame();
		    JLabel label = new JLabel(new ImageIcon(result));
		    frame.getContentPane().add(label, BorderLayout.CENTER);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.pack();
		    frame.setVisible(true);
		
    
    
		    
		    IplImage iplimg = IplImage.create(width, height, IPL_DEPTH_8U, channels);
		    IplImage test_image = IplImage.create(width, height, IPL_DEPTH_8U, channels); 
		    
		    int offset = 0;
		    try {
			    File file = new File(fileName) ;
			    InputStream is = new FileInputStream(file) ;
			    System.out.println("Starting.....");
			    long len = file.length();
			    byte[] bytes = new byte[width * height * 3];
			    
			    int numRead = 0;
			    int limit=0;
		        while (offset < file.length() ) {
		        	limit++;
		        	int ind = 0;
		        	numRead=is.read(bytes, 0,width*height*3);
		        	
		        	
		        	
		    		for(int y = 0; y < height; y++){
		    	
		    			for(int x = 0; x < width; x++){
		    		 
		    				byte a = 0;
		    				byte r = bytes[ind+height*width*2];
		    				byte g = bytes[ind+height*width];
		    				byte b = bytes[ind]; 
		    				
		    				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
		    				img.setRGB(x,y,pix);
		    			
		    				ind++;
		    			}
		    		}
		    		
		    	
		    			
		    		iplimg=IplImage.createFrom(img);
		    		
		    		cvCvtColor(iplimg,test_image, CV_BGRA2YUV_IYUV);
		    		
		    		result.setData(iplimg.getBufferedImage().getRaster());
		    	
		    		frame.revalidate();
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
