import static com.googlecode.javacv.cpp.opencv_imgproc.CV_COMP_CHISQR;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCompareHist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.text.StyledEditorKit.BoldAction;
import javax.swing.text.html.MinimalHTMLWriter;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.IplImage;


public class DifferentiatingVideos {

	public static double fd_threshold = 0.35;
	public static LinkedList<shots_structure>maxstructure;
	public static LinkedList<shots_structure>smallstructure;
	
	public static ArrayList<shots_structure> video_shots;
	public static ArrayList<shots_structure> ad_shots;
	
	
	public static void differ_shots(ArrayList<shots_structure> input_shots)
	{
		
		video_shots=new ArrayList<shots_structure>();
		ad_shots=new ArrayList<shots_structure>();
		int max=0,curmax=0;
		
		Iterator<shots_structure > it = input_shots.iterator();
		int it_counter=0;
		int it_max=0;
		while(it.hasNext())
		{
			shots_structure temp = it.next();
			curmax =  temp.end_frame - temp.start_frame+1 ;
			if(curmax > max)
			{
				it_max=it_counter;
				max = curmax;
			}
			it_counter++;
		}		
		video_shots.add(input_shots.get(it_max));
		input_shots.remove(it_max);
		
				
		for(int j=0;j<input_shots.size()-1;j++)
		{
			int []mid_value_in=new int[5];
			
			get_mid_value(input_shots.get(j).end_frame,input_shots.get(j).start_frame,mid_value_in,1);
			Boolean var=false;
			int temp_i=0;
			for(int i=0;i< (video_shots.size()-1) ;i++)
			{
				temp_i=i;
				int []mid_value_out=new int[5];
				
				get_mid_value(video_shots.get(i).end_frame,video_shots.get(i).start_frame,mid_value_out,1);
				 var =checksimilarity(mid_value_in,mid_value_out);
				if(var)
				{
					video_shots.add(input_shots.get(j));
					break;
				}
				
			}
			if(!var)
			{
			ad_shots.add(input_shots.get(temp_i));
			
			}
		}
		
		//Doing this for all ad_shots now..
		for(int j=0;j<ad_shots.size()-1;j++)
		{
			int []mid_value_in=new int[5];
			
			get_mid_value(ad_shots.get(j).end_frame,ad_shots.get(j).start_frame,mid_value_in,2);
			Boolean var=false;
			for(int i=0;i< (video_shots.size()-1) ;i++)
			{
				int []mid_value_out=new int[5];
				
				get_mid_value(video_shots.get(i).end_frame,video_shots.get(i).start_frame,mid_value_out,2);
				var =checksimilarity(mid_value_in,mid_value_out);
				if(var)
				{
					video_shots.add(ad_shots.get(j));
					break;
				}
				
			}
			
		}
}

	public static void get_mid_value(int end_frame, int start_frame, int[] mid_value_in,int i) {
		// TODO Auto-generated method stub
		mid_value_in[0]=start_frame;
		mid_value_in[4]=end_frame;				
		int tempmid=(end_frame + start_frame+i)/2 ;
		mid_value_in[1] = tempmid;
		mid_value_in[2] = (tempmid + start_frame)/2 ;
		mid_value_in[3] = (end_frame + tempmid)/2;
	}


	public static boolean checksimilarity(int[] maximages,int[] tempimages) {
		// TODO Auto-generated method stub
		
		
		
		for(int i=0; i<maximages.length-1;i++){
			
			IplImage first_image=sum.complete_video.get(maximages[i]);			
			CvMat d1 = feature_detection.featureDetect(first_image);
			for(int j=0; j<tempimages.length-1;j++){
				
				IplImage second_image= sum.complete_video.get(tempimages[j]);
				CvMat d2 = feature_detection.featureDetect(second_image);
				
				int hist_compared_value=(int) cvCompareHist(histogram_color_difference.getHueHistogram(first_image), histogram_color_difference.getHueHistogram(second_image), CV_COMP_CHISQR);
		    	
				if((feature_detection.match(d1,d2) > fd_threshold) || (hist_compared_value<histogram_color_difference.min_threshold) )
				{
					
					return true;
				}
				
			}
			
		}
		
		return false;
	}
	
	
}
