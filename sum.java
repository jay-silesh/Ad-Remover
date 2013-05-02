import java.awt.BorderLayout;
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
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
import static com.googlecode.javacv.cpp.opencv_core.cvSplit;
import static com.googlecode.javacv.cpp.opencv_core.cvTermCriteria;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_LOAD_IMAGE_UNCHANGED;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvNamedWindow;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvShowImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_COMP_CHISQR;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_HIST_ARRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCalcHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCompareHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCreateHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvFindCornerSubPix;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvGoodFeaturesToTrack;
import static com.googlecode.javacv.cpp.opencv_video.cvCalcOpticalFlowPyrLK;


import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.IplImageArray;
import com.googlecode.javacv.cpp.opencv_imgproc.CvHistogram;

import static com.googlecode.javacv.cpp.opencv_core.*;


public class sum {

	static int total_frame_size;
	
	public static int width = 352;
	public static int height = 288;
	public static double feature_detection_threshold= 0.35;
	
	
	static ArrayList<int[][]> image_rgb_values = new ArrayList<int[][]>() ;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//Contains all the frames which have gone through Histogram process..
		ArrayList<shots_structure> ss_hist_frames=new ArrayList<shots_structure>();
		
		//Contains all the frames which have gone through the Feature_detection process..
		ArrayList<shots_structure> ss_fd_frames=new ArrayList<shots_structure>();
		ArrayList<shots_structure> ss_fd_frames_convert=new ArrayList<shots_structure>();
		
		long start=System.currentTimeMillis();
		String fileName = "C:\\Users\\Jay\\Documents\\project_files\\video1.rgb";
			
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    
		    
		    BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    
		    IplImage iplimg = IplImage.create(width, height, IPL_DEPTH_8U, 3);
		    IplImage iplimg_temp = IplImage.create(width, height, IPL_DEPTH_8U, 3);
		    int counter_access_frames=0;
			    
		    int offset = 0;
		    try {
			    File file = new File(fileName) ;
			    InputStream is = new FileInputStream(file) ;
			    System.out.println("Starting.....");
			    long len = file.length();
			    byte[] bytes = new byte[width * height * 3];
			    
			    int numRead = 0;
				
				
				
				/****************************************************************/
				//Reading the image just once
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
			    		
			    	
			    			
			    		iplimg_temp=IplImage.createFrom(img);
			    		offset += numRead;
						
				/*****************************************************************/
		        
		        while (offset < file.length() ) {
		        	
		        	
		        	ind = 0;
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
		    				img.setRGB(x,y,pix);
		    			
		    				ind++;
		    			}
		    		}
		    		
		    	
		    			
		    		iplimg=IplImage.createFrom(img);
		    	
		    		int hist_compared_value=(int) cvCompareHist(histogram_color_difference.getHueHistogram(iplimg_temp), histogram_color_difference.getHueHistogram(iplimg), CV_COMP_CHISQR);
		    		
		    		if(histogram_color_difference.check_threshold(hist_compared_value))
		    		{
		    			shots_structure temp_ss=new shots_structure();
		    			temp_ss.frame_number=counter_access_frames;
		    			temp_ss.frame=iplimg;
		    			temp_ss.value=hist_compared_value;
		    			ss_hist_frames.add(temp_ss);
		       		}
		    		iplimg_temp.deallocate();
		    		iplimg_temp=IplImage.createFrom(img);
		    		
					offset += numRead;
					counter_access_frames++;
					
		        }
		        is.close();
		    		
		    	
				
				
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	
		    
		    
		    process_feature_detection(ss_hist_frames,ss_fd_frames);
		    System.out.println("Mins taken: "+(((System.currentTimeMillis()-start)/1000)/60));
		    
		    
		   //Custom class for converting the frames contained in the Shot_structure
		    convert_color_space.convert_color(ss_fd_frames,ss_fd_frames_convert,CV_RGB2YCrCb);
		 
	
		  //  extra_functions.write_data(ss_fd_frames);
		    extra_functions.display_frames(ss_fd_frames,250);
			
		    System.out.println("Complete!!\n");
}
	
	 	public static void process_feature_detection(ArrayList<shots_structure> ss_hist_frames2, ArrayList<shots_structure> ss_fd_frames2) {
		// TODO Auto-generated method stub
		
		 	int counter=0;
		
			while(counter+1 < ss_hist_frames2.size())
			{
				CvMat d1 = feature_detection.featureDetect(ss_hist_frames2.get(counter++).frame);
				CvMat d2 = feature_detection.featureDetect(ss_hist_frames2.get(counter).frame);
				
				
				if((feature_detection.match(d1,d2)) >feature_detection_threshold)
				{
					  ss_fd_frames2.add(ss_hist_frames2.get(counter));
				}

			}			
			
	}
	
}