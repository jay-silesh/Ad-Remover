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

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
 
import com.googlecode.javacv.cpp.opencv_core.CvArr;
import com.googlecode.javacv.cpp.opencv_core.CvPoint;
import com.googlecode.javacv.cpp.opencv_core.CvPoint2D32f;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.IplImageArray;
import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.*;
import com.googlecode.javacv.cpp.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_calib3d.*;
import static com.googlecode.javacv.cpp.opencv_objdetect.*;
public class test {

	
	
	 public static void main(String atgv[])
	 {
		long start=System.currentTimeMillis();
		 int width = 352;
		int height = 288;
		sum.main1(null);
	
		 FileWriter fstream = null;
			try {
				fstream = new FileWriter("out.txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 BufferedWriter out1 = new BufferedWriter(fstream); 
		
		
		/*BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		JFrame frame = new JFrame();
	    JLabel label = new JLabel(new ImageIcon(result));
	    frame.getContentPane().add(label, BorderLayout.CENTER);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
		*/
	    int counter=0;
	    double temp_data;
		 while(counter<sum.total_frame_size-1)
		 {
			 int temp_data1=(int) cvCompareHist( getHueHistogram(sum.frames.get(counter),0),  getHueHistogram(sum.frames.get(counter+1),0), CV_COMP_CHISQR);
			 
			//			 result.setData((sum.frames.get(counter++)).getBufferedImage().getRaster());
			 //frame.repaint();
			 counter++;
			 
				 try {
					 out1.write("\nframe: "+counter+" Min : "+(counter/24)+"\tData:"+temp_data1);
				 	} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
		 }
		 
		 try {
			out1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println(sum.total_frame_size+"\tdone calculating\nMins: "+(((System.currentTimeMillis()-start)/1000)/60));
			
  }
	 
	 
	 
	 public static CvHistogram getHueHistogram(IplImage image,int ii){
		    if(image==null || image.nChannels()<3) new Exception("Error!");
		    IplImage hsvImage= cvCreateImage(image.cvSize(), image.depth(), 3);
		    cvCvtColor(image, hsvImage, CV_BGR2HSV);
		    
		    // Split the 3 channels into 3 images
		    IplImageArray hsvChannels = splitChannels(hsvImage);
		   
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
		    cvCalcHist(hsvChannels.position(2),hist, accumulate, null);
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
	
}
