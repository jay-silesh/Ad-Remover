import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvSplit;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_HIST_ARRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCalcHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCreateHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;

import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.IplImageArray;
import com.googlecode.javacv.cpp.opencv_imgproc.CvHistogram;


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

import org.ietf.jgss.Oid;

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



public class histogram_color_difference {

	
	public static CvHistogram getHueHistogram(IplImage image1)
	{
	   
		if(image1==null || image1.nChannels()<3) new Exception("Error!");
		
	//	if(image1==null || image1.nChannels()<3) new Exception("Error!");
	
	    // Split the 3 channels into 3 images
	    CvSize size = image1.cvSize();
	    int depth=image1.depth();
	    IplImage image = cvCreateImage(size, depth, 3);
	    cvCvtColor(image1,image, CV_RGB2YUV );
	    
	  
	    
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
	    
	    int accumulate = 0;
	    IplImage mask = null;
	//    cvCalcHist(hsvChannels.position(1),hist, accumulate, mask);
	 //   cvCalcHist(hsvChannels.position(2),hist, accumulate, mask);
	    cvCalcHist(hsvChannels.position(0),hist, accumulate, mask);
		 
	 
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
	/*double min_threshold=35000;
	double max_threshold=400000000;*/
	
	double min_threshold=10000;
	double max_threshold=400000000;
	 
	if( (temp_data1>min_threshold)&& (temp_data1< max_threshold) )
		return true;
	return false;
}

	
}
