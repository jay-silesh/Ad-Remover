import static com.googlecode.javacv.cpp.opencv_imgproc.CV_COMP_CHISQR;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCompareHist;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.IplImage;


public class DifferentiatingVideos  {

	public static double fd_threshold = 0.285;
	public static LinkedList<shots_structure>maxstructure;
	public static LinkedList<shots_structure>smallstructure;
	
	public static void differ_shots(ArrayList<shots_structure> input_shots){
		
		maxstructure = new LinkedList<shots_structure>();
		smallstructure = new LinkedList<shots_structure>();
		
		Iterator<shots_structure> it = input_shots.iterator();
		int max=0,curmax=0;
		int mid=0,mid1=0,mid2=0,tempmid1=0,tempmid2=0,tempmid=0;;
		int te=0;
		shots_structure maxshot;
		if(te==0)
		{
			maxshot = new shots_structure();
			while(it.hasNext()){
				shots_structure temp = it.next();
				curmax =  temp.end_frame - temp.start_frame+1 ;
				
				if(curmax > max){
					
					
					maxshot.copy(temp);
					max = curmax;
				}
			}
		}
		else
		{
			int hn=merge_consecutive.get_highest_shots(input_shots);
			maxshot = new shots_structure();
			maxshot.copy(input_shots.get(hn));
		}
		
		
		
		ArrayList<IplImage> maximages = new ArrayList<IplImage>();
		mid = (maxshot.end_frame + maxshot.start_frame)/2;
		
	
		mid1 = (mid + maxshot.start_frame)/2 ;
		mid2 = (maxshot.end_frame + mid)/2;
		
		/*System.out.println("mid1 "+mid1);
		System.out.println("mid1 "+mid1);*/
		
		
		maximages.add(sum.complete_video.get(maxshot.start_frame));
		maximages.add(sum.complete_video.get(mid1));
		maximages.add(sum.complete_video.get(mid));
		maximages.add(sum.complete_video.get(mid2));
		maximages.add(sum.complete_video.get(maxshot.end_frame));
		
		Iterator<shots_structure > shots = input_shots.iterator();
		
	//	maxstructure.add(maxshot);
		
		while(shots.hasNext()){
			ArrayList<IplImage> tempimages = new ArrayList<IplImage>();
			shots_structure temp = shots.next();
			if((temp.end_frame-temp.start_frame+1) > 5)
			{	
				
				tempmid = (temp.end_frame + temp.start_frame)/2 ;
				tempmid1 = (tempmid + temp.start_frame)/2 ;
				tempmid2 = (temp.end_frame + tempmid)/2;
				
				tempimages.add(sum.complete_video.get(temp.start_frame));
				tempimages.add(sum.complete_video.get(tempmid1));
				tempimages.add(sum.complete_video.get(tempmid));
				tempimages.add(sum.complete_video.get(tempmid2));
				tempimages.add(sum.complete_video.get(temp.end_frame));
				
				}
			else{
				
				/*ArrayList<IplImage> tempimages = new ArrayList<IplImage>();*/
				
				for(int i =temp.start_frame;i<=temp.end_frame;i++)
					tempimages.add(sum.complete_video.get(i));
				}
			
			boolean var =checksimilarity(maximages,tempimages);
			
			if(var){
				maxstructure.add(temp);
			}
			else{
				smallstructure.add(temp);
			}
			
		}
		
	}

	
	private static boolean checksimilarity(ArrayList<IplImage> maximages,
			ArrayList<IplImage> tempimages) {
		// TODO Auto-generated method stub
		
		/*System.out.println("max array "+maximages.size());
		System.out.println("small array "+tempimages.size());*/
		
		for(int i=1; i<maximages.size();i++){
			
			for(int j=1; j<tempimages.size();j++){
				
				int hist_compared_value=(int) cvCompareHist(histogram_color_difference.getHueHistogram(maximages.get(i)), histogram_color_difference.getHueHistogram(tempimages.get(j)), CV_COMP_CHISQR);
				boolean hist = histogram_color_difference.check_threshold(hist_compared_value);
				
				CvMat d1 = feature_detection.featureDetect(maximages.get(i));
				CvMat d2 = feature_detection.featureDetect(tempimages.get(j));
				
				double fd_value=(feature_detection.match(d1,d2));
				
				
				
				if((fd_value > fd_threshold)  ){
					
					return true;
				}
				
			}
			
		}
		
		return false;
	}
	
	
	public void sortarraylist(ArrayList<shots_structure>input,ArrayList<shots_structure>output){
		
		shots_structure temp = new shots_structure();
		for(int j=0; j<input.size()-1;j++){
			for(int i=0;i<input.size()-1;i++){
			
				if( input.get(i).start_frame > input.get(i+1).start_frame){
					temp.copy(input.get(i+1));
					input.get(i+1).copy(input.get(i));
					input.get(i).copy(temp);
				}
			}
		}
		
		for(int k=0;k<input.size();k++)
			output.add(input.get(k));
	}
	
	public static int get_highest_shots2(ArrayList<shots_structure> in_ss) {
		// TODO Auto-generated method stub
		
		for(int i=0;i<in_ss.size();i++)
		{
			int[] temp_int1=new int[3];
			DifferentiatingVideos.get_mid_value_three(in_ss.get(i).end_frame,in_ss.get(i).start_frame, temp_int1, 1);
			boolean var=false;
			for(int j=0;j<in_ss.size();j++)
			{
				
				if(i!=j)
				{
					int[] temp_int2=new int[3];
					DifferentiatingVideos.get_mid_value(in_ss.get(j).end_frame,in_ss.get(j).start_frame, temp_int2, 1);
					System.out.println("trying to compare "+temp_int1[2]+ "   "+temp_int2[2]);
					var=DifferentiatingVideos.checksimilarity2(temp_int1, temp_int2);
					System.out.println("Stuck here "+ i+"   "+j);	
					
					if(var)
					{
						System.out.println("Similar shots are " + i+ " and "+j);
						in_ss.get(i).similar_frames+=in_ss.get(j).end_frame-in_ss.get(j).start_frame;
					}
				}
				
				
			}
		}	
			
		int shot_max=0;
		int counter=0;
		int cur_max=0;
		while(counter<in_ss.size())
		{
			int temp_max=in_ss.get(counter).similar_frames;
			System.out.println("No of Similar frames in "+counter+" is "+temp_max);
			if(temp_max>cur_max)
			{
				cur_max=temp_max;
				shot_max=counter;				
			}
			
			counter++;
		}
		
		return shot_max;		
			
			
			
	}
		
	
	public static void get_mid_value(int end_frame, int start_frame, int[] mid_value_in,int i) {
		// TODO Auto-generated method stub
		mid_value_in[0]=start_frame;
		mid_value_in[4]=end_frame;				
		int tempmid=(end_frame + start_frame+1)/2 ;
		mid_value_in[1] = tempmid;
		mid_value_in[2] = (tempmid + start_frame)/2 ;
		mid_value_in[3] = (end_frame + tempmid)/2;
	}

	
	public static void get_mid_value_three(int end_frame, int start_frame, int[] mid_value_in,int i) {
		// TODO Auto-generated method stub
		int tempmid=(end_frame + start_frame+1)/2 ;
		mid_value_in[0] = tempmid;
		mid_value_in[1] = (tempmid + start_frame)/2 ;
		mid_value_in[2] = (end_frame + tempmid)/2;
	}

	
	
	public static boolean checksimilarity2(int[] maximages,int[] tempimages) {
		// TODO Auto-generated method stub
		System.out.println("Check similaity:");	
		for(int i=0; i<maximages.length;i++){
						
			CvMat d1 = feature_detection.featureDetect(sum.complete_video.get( maximages[i]));
			for(int j=0; j<tempimages.length;j++){
				
				CvMat d2 = feature_detection.featureDetect(sum.complete_video.get( tempimages[j]));
				
				if((feature_detection.match(d1,d2) > fd_threshold) )
				{
					return true;
					
				}
				
			}
			
		}
		
		return false;
	}
	
	
	
}
