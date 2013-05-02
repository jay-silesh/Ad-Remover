import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvSplit;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_HIST_ARRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCalcHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCreateHist;

import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.IplImageArray;
import com.googlecode.javacv.cpp.opencv_imgproc.CvHistogram;


public class histogram_color_difference {

	
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
	double min_threshold=35000;
	double max_threshold=400000000;
	 
	if( (temp_data1>min_threshold)&& (temp_data1< max_threshold) )
		return true;
	return false;
}

	
}
