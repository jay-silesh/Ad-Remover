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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
 
import com.googlecode.javacv.cpp.opencv_core.CvArr;
import com.googlecode.javacv.cpp.opencv_core.CvPoint;
import com.googlecode.javacv.cpp.opencv_core.CvPoint2D32f;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
 
 public class optical_flow {
     private static final int MAX_CORNERS = 500;
 
     public static void main3(String[] args) {
         
    	 FileWriter fstream = null;
		try {
			fstream = new FileWriter("out.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 BufferedWriter out1 = new BufferedWriter(fstream); 
   //   sum video_file=new sum();
        sum.main1(null);
        
        int array_counter=0;
        while(array_counter<sum.total_frame_size-1)
        {
            IplImage tempimgA=sum.frames.get(array_counter);
            IplImage tempimgB=sum.frames.get(array_counter+1);
            
            
         CvSize img_sz = cvGetSize(tempimgA);
         IplImage imgA = cvCreateImage(img_sz, IPL_DEPTH_8U, 1);
         IplImage imgB = cvCreateImage(img_sz, IPL_DEPTH_8U, 1);
         int win_size = 15;
         
         cvConvertImage(tempimgA,imgA, CV_CVTIMG_FLIP);
         cvConvertImage(tempimgB,imgB, CV_CVTIMG_FLIP);
         IplImage imgC = cvLoadImage(
                 "image0.png",
                 CV_LOAD_IMAGE_UNCHANGED);
         // Get the features for tracking
         IplImage eig_image = cvCreateImage(img_sz, IPL_DEPTH_32F, 1);
         IplImage tmp_image = cvCreateImage(img_sz, IPL_DEPTH_32F, 1);
 
         int[] corner_count = { MAX_CORNERS };
         CvPoint2D32f cornersA = new CvPoint2D32f(MAX_CORNERS);
 
         CvArr mask = null;
         cvGoodFeaturesToTrack(imgA, eig_image, tmp_image, cornersA,
                 corner_count, 0.05, 5.0, mask, 3, 0, 0.04);
 
         cvFindCornerSubPix(imgA, cornersA, corner_count[0],
                 cvSize(win_size, win_size), cvSize(-1, -1),
                 cvTermCriteria(CV_TERMCRIT_ITER | CV_TERMCRIT_EPS, 20, 0.03));
 
         // Call Lucas Kanade algorithm
         byte[] features_found = new byte[MAX_CORNERS];
         float[] feature_errors = new float[MAX_CORNERS];
 
         CvSize pyr_sz = cvSize(imgA.width() + 8, imgB.height() / 3);
 
         IplImage pyrA = cvCreateImage(pyr_sz, IPL_DEPTH_32F, 1);
         IplImage pyrB = cvCreateImage(pyr_sz, IPL_DEPTH_32F, 1);
 
         CvPoint2D32f cornersB = new CvPoint2D32f(MAX_CORNERS);
         cvCalcOpticalFlowPyrLK(imgA, imgB, pyrA, pyrB, cornersA, cornersB,
                 corner_count[0], cvSize(win_size, win_size), 5,
                 features_found, feature_errors,
                 cvTermCriteria(CV_TERMCRIT_ITER | CV_TERMCRIT_EPS, 20, 0.3), 0);
 
         // Make an image of the results
         for (int i = 0; i < corner_count[0]; i++) {
             if (features_found[i] == 0 || feature_errors[i] > 550) { //400=550
                 try {
					out1.write("\nError Detected " + array_counter);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	 continue;
             }
   //          System.out.println("Got it/n");
         }
 
        // cvNamedWindow( "LKpyr_OpticalFlow", 0 );
        // cvShowImage( "LKpyr_OpticalFlow", imgC );
         
         array_counter++;
      }
        System.out.print("\nDone!");
        try {
			out1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
     
}