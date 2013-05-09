import static com.googlecode.javacv.cpp.opencv_core.CV_32F;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateMat;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_core.cvSplit;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_COMP_INTERSECT;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_HIST_ARRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCalcHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCompareHist;
import static com.googlecode.javacv.cpp.opencv_core.cvReleaseImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCreateHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvReleaseHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvNormalizeHist;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_features2d.BFMatcher;
import com.googlecode.javacv.cpp.opencv_features2d.DMatchVectorVector;
import com.googlecode.javacv.cpp.opencv_features2d.DescriptorExtractor;
import com.googlecode.javacv.cpp.opencv_features2d.DescriptorMatcher;
import com.googlecode.javacv.cpp.opencv_features2d.FeatureDetector;
import com.googlecode.javacv.cpp.opencv_features2d.KeyPoint;
import com.googlecode.javacv.cpp.opencv_imgproc.CvHistogram;
import com.googlecode.javacv.cpp.opencv_nonfree.SIFT;

public class hist_check {
	public CvMat FeatureDetect(IplImage image) {

		SIFT sift = new SIFT(); 
		FeatureDetector featureDetector  = sift.getFeatureDetector();
		KeyPoint kpt = new KeyPoint(); 
		featureDetector.detect(image, kpt, null); 
		 
		DescriptorExtractor extractor  = sift.getDescriptorExtractor(); 		
        CvMat dp = cvCreateMat(kpt.capacity(), extractor.descriptorSize(), CV_32F);         
        extractor.compute(image, kpt, dp); 
        
        if(kpt.isNull() || dp.isNull())
        {
        	return null;
        }
        return dp;
	}
	public DMatchVectorVector Init_Match(CvMat d1,CvMat d2){
		DescriptorMatcher matcher = new BFMatcher();
		DMatchVectorVector matches = new DMatchVectorVector();
		matcher.knnMatch(d1, d2, matches, 2, null, true);
		return matches;
	}
	public double RatioTest(DMatchVectorVector matches){
		int count = 0;
		long total =  matches.size();
		
		if(total < 1)
			return 0.0;
		else{
			//ratio_Test
			for(int i=0; i<total-1; i++) {
				if(matches.size(i) > 1) {
					if(matches.get(i, 0).distance()/matches.get(i, 1).distance() < 0.9)
						count++;
			}
		}
		return (double)count/(double)matches.size();
		}
	}
	//!----------------------------------!//
	// Feature detection, just use this method. 
	// The input parameters are two images, the types are IplImage.
	//return value is double
	public double SymmetricTest(IplImage d1, IplImage d2){
		double similarity = 0.0;
		double i1=0;
		double i2=0;
		i1 = RatioTest(Init_Match(FeatureDetect(d1),FeatureDetect(d2)));
		i2 = RatioTest(Init_Match(FeatureDetect(d2),FeatureDetect(d1)));
		similarity = i1>i2? i1:i2;
		return similarity;
		
	}
	
	public CvHistogram cal_histro(IplImage image){
		
		int size[] = {50,50,50}; 
		float ranges[][] = {{0,50},{0,50},{0,50}};
		int dims=3;
		CvSize size1 = image.cvSize();
		int depth=image.depth();
		  
		 IplImage channel1 = cvCreateImage(size1, depth, 1);
		 IplImage channel2 = cvCreateImage(size1, depth, 1);
		 IplImage channel3 = cvCreateImage(size1, depth, 1);
		  
		cvSplit(image, channel1,channel2,channel3,null);
		CvHistogram histro = cvCreateHist( dims, size, CV_HIST_ARRAY, ranges, 1 );
		IplImage array[] = {channel1,channel2,channel3};
		cvCalcHist(array, histro, 0, null);
		cvNormalizeHist(histro,1.0);
		cvReleaseImage(channel1);
		cvReleaseImage(channel2);
		cvReleaseImage(channel3);
		
		return histro;
	}
	
	public double HisDiff(IplImage image1, IplImage image2){
		CvHistogram c1 = cal_histro(image1);
		CvHistogram c2 = cal_histro(image2);
		double s =cvCompareHist(c1,c2, CV_COMP_INTERSECT);
		cvReleaseHist(c1);
		cvReleaseHist(c2);
		return s;
	}
	
}