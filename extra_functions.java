import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.googlecode.javacv.cpp.opencv_core.IplImage;


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
				 		  int tf=ef-sf;
				 		   	
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
		
		Scanner scan = new Scanner(System.in);
		BufferedImage result = new BufferedImage(sum.width, sum.height, BufferedImage.TYPE_INT_RGB);
		JFrame frame = new JFrame();
	    JLabel label = new JLabel(new ImageIcon(result));
	    frame.getContentPane().add(label, BorderLayout.CENTER);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	    byte b = 0;
		int counter=0;
		//result.setData( sum.complete_video.get(0).getBufferedImage().getRaster());
		while(counter<ss_fd_frames_convert.size())
		{
			
			int st=ss_fd_frames_convert.get(counter).start_frame;
			int et=ss_fd_frames_convert.get(counter).end_frame;
			System.out.println("The counter is "+(counter+1)+"\t"+"Start :"+st+"  End :"+et+"  Diff is "+(et-st));
			
			int ii=0;
			while(st<et)
			{
				if(st==0 || st==1)
					result.setData( sum.complete_video.get(st+1).getBufferedImage().getRaster());
				else
					result.setData( sum.complete_video.get(st-1).getBufferedImage().getRaster());
				frame.repaint();
				 	    
		 	   try {
					 Thread.sleep(10);
				 	}  catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				 	} 
				
				st++;
				
			}
			counter++;
			


			
			int xxi = scan.nextInt();

			
			
			
		}
		
		
	}
		 
	
	public static void display_linkedlist(
			LinkedList<shots_structure> ss_fd_frames_convert, int i) {
		// TODO Auto-generated method stub
		

		Scanner scan = new Scanner(System.in);
		BufferedImage result = new BufferedImage(sum.width, sum.height, BufferedImage.TYPE_INT_RGB);
		JFrame frame = new JFrame();
	    JLabel label = new JLabel(new ImageIcon(result));
	    frame.getContentPane().add(label, BorderLayout.CENTER);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	    byte b = 0;
		int counter=0;
		while(counter<ss_fd_frames_convert.size())
		{
			
			int st=ss_fd_frames_convert.get(counter).start_frame;
			int et=ss_fd_frames_convert.get(counter).end_frame;
			
			System.out.println(st+"\t"+et);
			int ii=0;
			while(st<=et)
			{
				 
				result.setData( sum.complete_video.get(st+1).getBufferedImage().getRaster());
				frame.repaint();
				 	    
		 	   try {
					 Thread.sleep(40);
				 	}  catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				 	} 
				
				st++;
				
			}
			counter++;
			
			
			int xxi = scan.nextInt();

			
			
			
		}
		
		
	}
	

	
	public static byte[] read_video_frame(int frame_no)
	{
		
		int numRead = 0;
		int	ind = 0;
		int height=sum.height;
		int width=sum.width;
		File file = new File(sum.fileName) ;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	    
		
    	byte[] bytes = new byte[sum.width* sum.height * 3];
		
    	
	    	try {
	    			is.skip(sum.width*sum.height*3*frame_no);
					numRead=is.read(bytes, 0,sum.width*sum.height*3);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	
	    		for(int y = 0; y < height; y++)
	    		{
	    	
	    			for(int x = 0; x < width; x++)
	    			{
	    		 
	    				byte a = 0;
	    				byte r = bytes[ind+height*width*2];
	    				byte g = bytes[ind+height*width];
	    				byte b = bytes[ind]; 
	    				
	    			
	    			}
	    		}
    		
	    return bytes;	
	}
	
	
	
	public static void write_file(LinkedList<shots_structure> ll) throws IOException
	{
		
		String strFileName = sum.video_output_file;
        BufferedOutputStream bos = null;
        FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(new File(strFileName));
			 bos = new BufferedOutputStream(fos);
		       
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        
		
		for(int i=0;i<ll.size();i++)
		{
			shots_structure temp=ll.get(i);
			int st=temp.start_frame;
			int	et=temp.end_frame;
			
			while(st<=et)
			{
				//read_video_frame(st)
				  bos.write(read_video_frame(st++));
			
			}
		
		
		
		}
		
		bos.close();	
		
		
	}
		
		
		
	
	
	
	
}