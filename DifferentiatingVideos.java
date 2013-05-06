import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.IplImage;


public class DifferentiatingVideos {

	public static double fd_threshold = 0.35;
	public static LinkedList<shots_structure>maxstructure;
	public static LinkedList<shots_structure>smallstructure;
	
	public static void differ_shots(ArrayList<shots_structure> input_shots){
		
		maxstructure = new LinkedList<shots_structure>();
		smallstructure = new LinkedList<shots_structure>();
		
		Iterator<shots_structure > it = input_shots.iterator();
		int max=0,curmax=0;
		int mid=0,mid1=0,mid2=0,tempmid1=0,tempmid2=0,tempmid=0;;
		
		shots_structure maxshot = new shots_structure();
		while(it.hasNext()){
			shots_structure temp = it.next();
			curmax =  temp.end_frame - temp.start_frame+1 ;
			
			if(curmax > max){
				
				
				maxshot.copy(temp);
				max = curmax;
			}
		}
		
		ArrayList<IplImage> maximages = new ArrayList<IplImage>();
		mid = (maxshot.end_frame + maxshot.start_frame)/2;
		
		/*System.out.println("mid "+mid);
		System.out.println("maxshot start "+maxshot.start_frame);
		System.out.println("maxshot end"+maxshot.end_frame);*/
		
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
		
		//maxstructure.add(maxshot);
		
		while(shots.hasNext()){
			ArrayList<IplImage> tempimages = new ArrayList<IplImage>();
			shots_structure temp = shots.next();
			if(temp.end_frame-temp.start_frame > 5)
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
		
		
		
		for(int i=0; i<maximages.size()-1;i++){
			
			for(int j=0; j<tempimages.size()-1;j++){
				
				CvMat d1 = feature_detection.featureDetect(maximages.get(i));
				CvMat d2 = feature_detection.featureDetect(tempimages.get(j));
				
				double fd_value=(feature_detection.match(d1,d2));
				
				if((fd_value > fd_threshold) ){
					
					return true;
				}
				
			}
			
		}
		
		return false;
	}
	
}
