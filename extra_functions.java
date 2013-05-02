import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class extra_functions {

	
	 public static void write_data(ArrayList<shots_structure> ss_fd_frames2)
	 {
		  FileWriter fstream = null;
		   try {
				fstream = new FileWriter("fd_out.txt");
		   } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	BufferedWriter out3 = new BufferedWriter(fstream);
	        int counter=0;
		     while(ss_fd_frames2.size()>counter)
			 {
				 	   try {
							 int frame_no=ss_fd_frames2.get(counter).frame_number;
							 double temp_val=ss_fd_frames2.get(counter).value;
							 out3.write(counter+".\t"+"frame: "+frame_no+" Min : "+(frame_no/24)+"\tData:"+temp_val+"\n");
						 	}  
				 	   		catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				 	   counter++;
			 }		     
		     try {
				out3.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	 }
	 
	 
	 public static void display_frames(ArrayList<shots_structure> ss_fd_frames2,long sleep_time) {
			// TODO Auto-generated method stub

			  	BufferedImage result = new BufferedImage(sum.width, sum.height, BufferedImage.TYPE_INT_RGB);
				JFrame frame = new JFrame();
			    JLabel label = new JLabel(new ImageIcon(result));
			    frame.getContentPane().add(label, BorderLayout.CENTER);
			    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    frame.pack();
			    frame.setVisible(true);
			    int counter=0;
			     while(ss_fd_frames2.size()>counter)
				 {
				 		  	result.setData(ss_fd_frames2.get(counter++).frame.getBufferedImage().getRaster());
					 	    frame.repaint();
					 	    
					 	    
					 	   try {
								 Thread.sleep(sleep_time);
							 	}  catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
							 	} 
				 }
		}
		 

	 
}

