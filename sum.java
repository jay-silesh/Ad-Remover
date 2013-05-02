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
	
	//Contains all the frames which have gone through Histogram process..
	static ArrayList<IplImage> frames = new ArrayList<IplImage>() ;
	
	//Contains all the frames which have gone through the Feature_detection process..
	static ArrayList<IplImage> frames_fd = new ArrayList<IplImage>() ;
	
	public static int width = 352;
	public static int height = 288;
	public static double feature_detection_threshold= 0.35;
	
	
	static ArrayList<int[][]> image_rgb_values = new ArrayList<int[][]>() ;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			String fileName = "C:\\Users\\Jay\\Documents\\project_files\\video1.rgb";
			
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    
		    
		    BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    
		    IplImage iplimg = IplImage.create(width, height, IPL_DEPTH_8U, 3);
		    IplImage iplimg_temp = IplImage.create(width, height, IPL_DEPTH_8U, 3);
			    
		    int offset = 0;
		    try {
			    File file = new File(fileName) ;
			    InputStream is = new FileInputStream(file) ;
			    System.out.println(file.length());
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
		    	
		    		int hist_compared_value=(int) cvCompareHist( getHueHistogram(iplimg_temp),  getHueHistogram(iplimg), CV_COMP_CHISQR);
		    		
		    		if(check_threshold(hist_compared_value))
		    		{
		    			frames.add(iplimg);
		    		}
		    		iplimg_temp.deallocate();
		    		iplimg_temp=IplImage.createFrom(img);
		    		
					offset += numRead;
					
		        }
		        is.close();
		    		
		    	
				
				
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    total_frame_size=frames.size();
		 
		    process_feature_detection(frames,frames_fd);
		    display_frames(frames_fd);
	
}
	
	 public static void process_feature_detection(ArrayList<IplImage> frames_input, ArrayList<IplImage> frames_output) {
		// TODO Auto-generated method stub
		
		 	int counter=0;
		
			while(counter+1 < frames_input.size())
			{
				CvMat d1 = feature_detection.featureDetect(frames_input.get(counter++));
				CvMat d2 = feature_detection.featureDetect(frames_input.get(counter));
				if((feature_detection.match(d1,d2)) >feature_detection_threshold)
				{
					frames_output.add(frames_input.get(counter));
				}

			}			
			
	}

	public static void display_frames(ArrayList<IplImage> frames_temp) {
		// TODO Auto-generated method stub

		  	BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			JFrame frame = new JFrame();
		    JLabel label = new JLabel(new ImageIcon(result));
		    frame.getContentPane().add(label, BorderLayout.CENTER);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.pack();
		    frame.setVisible(true);
			
		    
		    int counter=0;
		     while(frames_temp.size()>counter)
			 {
			 		  	result.setData(frames_temp.get(counter++).getBufferedImage().getRaster());
				 	    frame.repaint();
				 	    
				 	    
				 	   try {
							 Thread.sleep(500);
						 	}  catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
						 	}
			 }
		
	}
	 

	public static CvHistogram getHueHistogram(IplImage image){
		    if(image==null || image.nChannels()<3) new Exception("Error!");
		
		    // Split the 3 channels into 3 images
		    IplImageArray hsvChannels = splitChannels(image);
		   
		    int numberOfBins=255;
		    float minRange= 0f;
		    float maxRange= 180f;
		    
		    int dims = 1;
		    int[]sizes = new int[]{numberOfBins};
		    int histType = CV_HIST_ARRAY;
		    float[] minMax = new  float[]{minRange, maxRange};
		    float[][] ranges = new float[][]{minMax};
		    int uniform = 1;
		    CvHistogram hist = cvCreateHist(dims, sizes, histType, ranges, uniform);
		    
		    int accumulate = 1;
		    IplImage mask = null;
		    cvCalcHist(hsvChannels.position(2),hist, accumulate, mask);
		    cvCalcHist(hsvChannels.position(0),hist, accumulate, mask);
		    cvCalcHist(hsvChannels.position(1),hist, accumulate, mask);
		    return hist;
		}



	public static IplImageArray splitChannels(IplImage hsvImage) {
	    CvSize size = hsvImage.cvSize();
	    int depth=hsvImage.depth();
	    IplImage channel0 = cvCreateImage(size, depth, 1);
	    IplImage channel1 = cvCreateImage(size, depth, 1);
	    IplImage channel2 = cvCreateImage(size, depth, 1);
	    cvSplit(hsvImage, channel0, channel1, channel2, null);
	    return new IplImageArray(channel0, channel1, channel2);
	}
	
	public static boolean check_threshold(double temp_data1)
	{
		double min_threshold=30000;
		double max_threshold=400000000;
		 
		if( (temp_data1>min_threshold)&& (temp_data1< max_threshold) )
			return true;
		return false;
	}
	

}