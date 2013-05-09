import java.util.ArrayList;
import java.util.Iterator;


public class merge_consecutive {

	public static int get_highest_shots(ArrayList<shots_structure> in_ss) {
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
					DifferentiatingVideos.get_mid_value_three(in_ss.get(j).end_frame,in_ss.get(j).start_frame, temp_int2, 1);
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
		
	
}

	
	

