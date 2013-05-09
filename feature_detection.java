import static com.googlecode.javacv.cpp.opencv_core.CV_32F;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateMat;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_features2d.BFMatcher;
import com.googlecode.javacv.cpp.opencv_features2d.DMatchVectorVector;
import com.googlecode.javacv.cpp.opencv_features2d.DescriptorMatcher;
import com.googlecode.javacv.cpp.opencv_features2d.FlannBasedMatcher;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_features2d.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_nonfree.*;
import static com.googlecode.javacv.cpp.opencv_nonfree.SIFT;

public class feature_detection {
	public static CvMat featureDetect(IplImage image) {

		SIFT sift = new SIFT(); 
		FeatureDetector featureDetector  = sift.getFeatureDetector();
		KeyPoint keypoints = new KeyPoint(); 
		featureDetector.detect(image, keypoints, null); 
		 
		DescriptorExtractor extractor  = sift.getDescriptorExtractor(); 		
        CvMat descriptors = cvCreateMat(keypoints.capacity(), extractor.descriptorSize(), CV_32F);         
        extractor.compute(image, keypoints, descriptors); 
        
        if(keypoints.isNull() || descriptors.isNull()) {
        	//System.out.println("feature is Null");
        	return null;
        }      
        
   
        return descriptors;
		
	}
	public static double match(CvMat d1,CvMat d2){
		DescriptorMatcher matcher = new BFMatcher();
		DMatchVectorVector matches = new DMatchVectorVector();
		if(d2 != null && d1 != null) {
			matcher.knnMatch(d1, d2, matches, 2, null, true);	
			int count = 0;
			for(int i=0; i<matches.size()-1; i++) {
				if(matches.size(i) > 1) {
						if(matches.get(i, 0).distance()/matches.get(i, 1).distance() < 0.9)
						count++;
				}
			}
			
			double first_result=((double)count/(double)matches.size());
			
			DescriptorMatcher matcher2 = new BFMatcher();
			DMatchVectorVector matches2 = new DMatchVectorVector();
			
			matcher2.knnMatch(d2, d1, matches2, 2, null, true);	
			count = 0;
			for(int i=0; i<matches2.size()-1; i++) {
				if(matches2.size(i) > 1) {
						if(matches2.get(i, 0).distance()/matches2.get(i, 1).distance() < 0.9)
						count++;
				}
			}
			double second_result= ((double)count/(double)matches2.size());
			return (Math.max(first_result,second_result));
		}
		
		return 1.0;

	}
}	

