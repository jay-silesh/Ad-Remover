import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
	 
	 
	 
	 
	 public static void write_data_difference(ArrayList<shots_structure> ss_fd_frames2, int i)
	 {
		  FileWriter fstream = null;
		   try {
			   if(i==1)
				fstream = new FileWriter("fd_out.txt");
			   else
				   fstream = new FileWriter("Merge_FD.txt");  
		   } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	BufferedWriter out3 = new BufferedWriter(fstream);
	        int counter=0;
		     while(ss_fd_frames2.size()>counter)
			 {
				 	   try {
				 		   	int sf=ss_fd_frames2.get(counter).start_frame;
				 		   int ef=ss_fd_frames2.get(counter).end_frame;
				 		  int tf=ss_fd_frames2.get(counter).tf;
				 		   	
							 out3.write("Start frame: "+sf+"\tEnd Frame: "+ef+"\tNo of frames in bwt: "+tf+"\n");
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
				 	result.setData( sum.complete_video.get(ss_fd_frames2.get(counter++).frame_number).getBufferedImage().getRaster());
					frame.repaint();
					 	    
					 	    
			 	   try {
						 Thread.sleep(sleep_time);
					 	}  catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					 	} 
				 }
		}




	public static void display_frames_NEW(
			ArrayList<shots_structure> ss_fd_frames_convert, int i) {
		// TODO Auto-generated method stub
		
		
		BufferedImage result = new BufferedImage(sum.width, sum.height, BufferedImage.TYPE_INT_RGB);
		JFrame frame = new JFrame();
	    JLabel label = new JLabel(new ImageIcon(result));
	    frame.getContentPane().add(label, BorderLayout.CENTER);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
		
		int counter=0;
		while(counter<ss_fd_frames_convert.size())
		{
			
			int st=ss_fd_frames_convert.get(counter).start_frame;
			int et=ss_fd_frames_convert.get(counter).end_frame;
			
			
			
			while(st<=et)
			{
				result.setData( sum.complete_video.get(st).getBufferedImage().getRaster());
				frame.repaint();
				 	    
				 	    
		 	   try {
					 Thread.sleep(i);
				 	}  catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				 	} 
				
				st++;
				
			}
			Scanner scan=new Scanner(System.in);
			int num=scan.nextInt();
			counter++;
			scan.close();
			
		}
		
		
	}
		 

	 
}

