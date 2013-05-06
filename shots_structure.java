import java.util.ArrayList;

import com.googlecode.javacv.cpp.opencv_core.IplImage;


public class shots_structure {
	int frame_number;		//Holds the frame number
	double value;		//Holds the start time...
	int start_frame;
	int end_frame;
	int tf;
	
	public shots_structure() {
		// TODO Auto-generated constructor stub
		
		
	}
	public void copy(shots_structure ss) {
	
		/*ss.frame_number = this.frame_number;
		ss.value = this.value;
		ss.start_frame = this.start_frame;
		ss.end_frame = this.end_frame;
		ss.tf = this.tf;*/		
		
		this.frame_number = ss.frame_number;
		this.value = ss.value ;
		this.start_frame = ss.start_frame ; 
		this.end_frame = ss.end_frame ;
		this.tf = ss.tf;
		
	}
	
}


