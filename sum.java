import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import static com.googlecode.javacv.cpp.opencv_core.CV_RGB;

import static com.googlecode.javacv.cpp.opencv_core.CV_TERMCRIT_EPS;
import static com.googlecode.javacv.cpp.opencv_core.CV_TERMCRIT_ITER;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_32F;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;

import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvLine;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_core.cvConvert;
import static com.googlecode.javacv.cpp.opencv_core.cvTermCriteria;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_LOAD_IMAGE_UNCHANGED;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvNamedWindow;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvShowImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvFindCornerSubPix;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvGoodFeaturesToTrack;
import static com.googlecode.javacv.cpp.opencv_video.cvCalcOpticalFlowPyrLK;


import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_core.*;


public class sum {

	static int total_frame_size;
	static ArrayList<IplImage> frames = new ArrayList<IplImage>() ;
	
	static ArrayList<int[][]> image_rgb_values = new ArrayList<int[][]>() ;
	
	
	public static void main1(String[] args) {
		// TODO Auto-generated method stub
			String fileName = "C:\\Users\\Jay\\Documents\\project_files\\video1.rgb";
			int width = 352;
			int height = 288;
			
//		    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		    
		    
		    BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    
		    IplImage iplimg = IplImage.create(width, height, IPL_DEPTH_8U, 3);
		    IplImage iplimg1 = IplImage.create(width, height, IPL_DEPTH_8U, 3);
			    
		    int offset = 0;
		    try {
			    File file = new File(fileName) ;
			    InputStream is = new FileInputStream(file) ;
			    System.out.println(file.length());
			    long len = file.length();
			    byte[] bytes = new byte[width * height * 3];
			    
			    int numRead = 0;
				int write_count=0;
		        
		        while (offset < file.length() ) {
		     //   	int[][] bins = new int[3][256];
		        	
		        //	write_count++;
		        	//if(write_count>2000)
		        		//break;
		        	
		        	
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
		    				
		    			/*	bins[0][r]++; 	//Red
			                bins[1][g]++;	//green
			                bins[2][b]++;	//blue
		    				*/
		    				img.setRGB(x,y,pix);
		    			
		    				ind++;
		    			}
		    		}
		    		
		    	
			//		image_rgb_values.add(bins);
		    			
		    		iplimg=IplImage.createFrom(img);
		    		result.setData(iplimg.getBufferedImage().getRaster());
		    		//cvCvtColor(iplimg, iplimg1, CV_RGB2YUV);
		    		//cvCvtColor(iplimg, iplimg1, CV_BGR2YUV);
		    		frames.add(iplimg);
					offset += numRead;
					
		        }
		        is.close();
		    		
		    	
				
				
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    total_frame_size=frames.size();
		
   }

}