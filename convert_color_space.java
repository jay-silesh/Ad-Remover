import java.util.ArrayList;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

public class convert_color_space {

	public static void convert_color(ArrayList<shots_structure> ss_frames, ArrayList<shots_structure> ss_fd_frames_convert, int code) {
		// TODO Auto-generated method stub
		int counter=0;
		while(counter < ss_frames.size())
		{
			shots_structure temp_ss=new shots_structure();
			temp_ss.frame=IplImage.create(sum.width, sum.height, IPL_DEPTH_8U, 3);
			
			cvCvtColor(ss_frames.get(counter).frame,temp_ss.frame, code );
			
			temp_ss.value=ss_frames.get(counter).value;
			temp_ss.frame_number=ss_frames.get(counter).frame_number;
			
			ss_fd_frames_convert.add(temp_ss);
			counter++;
		
		}				
		
	}
	
}
