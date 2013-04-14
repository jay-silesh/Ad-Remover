import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.googlecode.javacpp.*;
public class sum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			String fileName = "C:\\Users\\Jay\\Documents\\project_files\\video1.rgb";
		   	int width = 352;
			int height = 288;
			
		    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    
		    // Use a label to display the image
		    JFrame frame = new JFrame();
		    JLabel label = new JLabel(new ImageIcon(img));
		    frame.getContentPane().add(label, BorderLayout.CENTER);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.pack();
		    frame.setVisible(true);
		    long sleep_time=-1,sle=-1;
		    int offset = 0;
		    try {
			    File file = new File(fileName);
			    InputStream is = new FileInputStream(file);
			    System.out.println(file.length());
			    byte[] bytes = new byte[width * height * 3];
			    
			    int numRead = 0;
		        
			 	long start=System.currentTimeMillis();
		        while (offset < file.length() ) {
		       
		        	int ind = 0;
		        	numRead=is.read(bytes, 0,width*height*3);
		        	if(numRead < 0)
		        		break;
		        	
		    		for(int y = 0; y < height; y++){
		    	
		    			for(int x = 0; x < width; x++){
		    		 
		    				byte r = bytes[ind+height*width*2];
		    				byte g = bytes[ind+height*width];
		    				byte b = bytes[ind]; 
		    				
		    				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
		    				img.setRGB(x,y,pix);
		    				ind++;
		    			}
		    		}
		       		sleep_time=System.currentTimeMillis()-start;
		     		if(sle==-1){
		     			sle++;
			    	//	sleep_time=(long)(0.04166-sleep_time);
			    		System.out.println("Sleep time is "+sleep_time);
			    		}
			   
		    		frame.repaint();
		    		
		    		
		    		try {
		    			Thread.sleep(10);
				//		Thread.sleep(sleep_time);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		offset += numRead;
		        	
		        }
		    
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    
		    

		   }
		  
		}