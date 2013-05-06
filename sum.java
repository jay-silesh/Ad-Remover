import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.ietf.jgss.Oid;

import static com.googlecode.javacv.cpp.opencv_core.CV_RGB;

import static com.googlecode.javacv.cpp.opencv_core.CV_TERMCRIT_EPS;
import static com.googlecode.javacv.cpp.opencv_core.CV_TERMCRIT_ITER;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_32F;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;

import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvLine;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_core.cvConvert;
import static com.googlecode.javacv.cpp.opencv_core.cvSplit;
import static com.googlecode.javacv.cpp.opencv_core.cvTermCriteria;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_LOAD_IMAGE_UNCHANGED;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvNamedWindow;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvShowImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_COMP_CHISQR;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_HIST_ARRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCalcHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCompareHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCreateHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvFindCornerSubPix;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvGoodFeaturesToTrack;
import static com.googlecode.javacv.cpp.opencv_video.cvCalcOpticalFlowPyrLK;


import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.IplImageArray;
import com.googlecode.javacv.cpp.opencv_imgproc.CvHistogram;

import static com.googlecode.javacv.cpp.opencv_core.*;


public class sum {

	static int total_frame_size;
	
	public static int width = 352;
	public static int height = 288;
	public static double feature_detection_threshold= 0.3;
	public static int merge_threshold=250;
	
	
	//static ArrayList<int[][]> image_rgb_values = new ArrayList<int[][]>() ;
	
	public static ArrayList<IplImage>complete_video =new ArrayList<IplImage>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Contains all the frames which have gone through Histogram process..
		ArrayList<shots_structure> ss_hist_frames=new ArrayList<shots_structure>();
		
		//Contains all the frames which have gone through the Feature_detection process..
		ArrayList<shots_structure> ss_fd_frames=new ArrayList<shots_structure>();
		ArrayList<shots_structure> ss_fd_frames_convert=new ArrayList<shots_structure>();
		ArrayList<shots_structure> ss_fd_frames_convert2=new ArrayList<shots_structure>();
		
		long start=System.currentTimeMillis();
		String fileName = "C:\\Users\\Jay\\Documents\\project_files\\video4.rgb";
			
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    
		    
		    BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    
		    IplImage iplimg = IplImage.create(width, height, IPL_DEPTH_8U, 3);
		    IplImage iplimg_temp = IplImage.create(width, height, IPL_DEPTH_8U, 3);
		    int counter_access_frames=0;
			    
		    int offset = 0;
		    try {
			    File file = new File(fileName) ;
			    InputStream is = new FileInputStream(file) ;
			    System.out.println("Starting.....");
			    long len = file.length();
			    byte[] bytes = new byte[width * height * 3];
			    
			    int numRead = 0;
				
				/****************************************************************/
			    
			/*    JFrame frame = new JFrame();
			    JLabel label = new JLabel(new ImageIcon(result));
			    frame.getContentPane().add(label, BorderLayout.CENTER);
			    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    frame.pack();
			    frame.setVisible(true);
			    */
			    /****************************************************************/
				
				/****************************************************************/
				//Reading the image just once
						int ind = 0;
			        	numRead=is.read(bytes, 0,width*height*3);			        	
			    		for(int y = 0; y < height; y++){			    	
			    			for(int x = 0; x < width; x++){
			    		 
			    				byte a = 0;
			    				byte r = bytes[ind+height*width*2];
			    				byte g = bytes[ind+height*width];
			    				byte b = bytes[ind]; 
			    				
			    				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
			    				img.setRGB(x,y,pix);
			    			
			    				ind++;
			    			}
			    		}
			    		
			    	
			    			
			    		iplimg_temp=IplImage.createFrom(img);
			    		offset += numRead;
			    		complete_video.add(iplimg_temp);
				/*****************************************************************/
			    		Scanner scan = new Scanner(System.in);
						
		        while (offset < file.length() ) {
		        	
		        	
		        	ind = 0;
		        	numRead=is.read(bytes, 0,width*height*3);
		        	if(numRead < 0)
		        		break;
		        	
		    		for(int y = 0; y < height; y++){
		    	
		    			for(int x = 0; x < width; x++){
		    		 
		    				byte a = 0;
		    				byte r = bytes[ind+height*width*2];
		    				byte g = bytes[ind+height*width];
		    				byte b = bytes[ind]; 
		    				
		    				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
		    				img.setRGB(x,y,pix);
		    			
		    				ind++;
		    			}
		    		}
		    		
		    	
		    			
		    		iplimg=IplImage.createFrom(img);
		    		complete_video.add(iplimg);
		    		int hist_compared_value=(int) cvCompareHist(histogram_color_difference.getHueHistogram(iplimg_temp), histogram_color_difference.getHueHistogram(iplimg), CV_COMP_CHISQR);
		    		 byte b = 0;
		    			
		    		if(histogram_color_difference.check_threshold(hist_compared_value))
		    		{
		    			
		    			/*System.out.println("Adding frame no: "+counter_access_frames);
		    			result.setData( iplimg.getBufferedImage().getRaster());
						frame.repaint();
						DataInputStream in=new DataInputStream(System.in);
						int i = scan.nextInt();*/
						
		    			shots_structure temp_ss=new shots_structure();
		    			temp_ss.frame_number=counter_access_frames;
		    			temp_ss.value=hist_compared_value;
		    			ss_hist_frames.add(temp_ss);
		       		}
		    		iplimg_temp.deallocate();
		    		iplimg_temp=IplImage.createFrom(img);
		    		
					offset += numRead;
					counter_access_frames++;
					
		        }
		        is.close();
		    		
		    	
				
				
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    System.out.println("\nInputted all the frames...\nStarting Feature Detection");
		    System.out.println("Mins taken: "+(((System.currentTimeMillis()-start)/1000)/60));
		    
		    process_feature_detection(ss_hist_frames,ss_fd_frames);
		    System.out.println("\nFeature Detection done!...");
		    System.out.println("Mins taken: "+(((System.currentTimeMillis()-start)/1000)/60));
		    extra_functions.write_data_difference(ss_fd_frames,1);		 
		  //  extra_functions.display_frames_NEW(ss_fd_frames,250);
		    
		    merge_shots(ss_fd_frames,ss_fd_frames_convert);
		    System.out.println("\nMerging done...");
		    System.out.println("Mins taken: "+(((System.currentTimeMillis()-start)/1000)/60));
		    
		    extra_functions.write_data_difference(ss_fd_frames_convert,2);
		    extra_functions.display_frames_NEW(ss_fd_frames_convert,250);
/*
		    DifferentiatingVideos.differ_shots(ss_fd_frames_convert);
		    extra_functions.display_linkedlist(DifferentiatingVideos.maxstructure, 250);*/
		    System.out.println("Complete!!\n");
}
	
	 	

		
		public static void merge_shots(ArrayList<shots_structure> input_ss, ArrayList<shots_structure> output_ss)
		// TODO Auto-generated method stub
		{
			int count1=0;
			int count2=count1+1;
			int max_size=input_ss.size();
			while(count1+1 < max_size)
			{
						System.out.println("comapring shots - count "+count1);
						
						int end_point=input_ss.get(count1).end_frame;
						int start_point=input_ss.get(count1).start_frame;
						int end_point_frame=input_ss.get(count1).frame_number;
						
						
						if( (input_ss.get(count2).end_frame-input_ss.get(count1).start_frame)<=merge_threshold)
						{
								System.out.println(" tf greater than threshold");
								if(  (input_ss.get(count2).end_frame-input_ss.get(count1).start_frame) >merge_threshold)
								{
									System.out.println(" CUR2-CUR1 is getter than merge_threshold");
									
									if(!output_ss.isEmpty())
									{	
										shots_structure last_ss= output_ss.get(output_ss.size() - 1);
										if(last_ss.start_frame==input_ss.get(count1).start_frame)
										{
											output_ss.remove(output_ss.get(output_ss.size()-1));
										}
									}
									shots_structure temp_ss=new shots_structure();
									temp_ss.end_frame=end_point;
									temp_ss.start_frame=start_point;
									temp_ss.tf=end_point-start_point+1;
									output_ss.add(temp_ss);
									count1++;
									count2=count1+1;
									if(count2>=max_size)
										break;
									continue;						
								}
								else
								{
									System.out.println(" CUR2-CUR1 is lesse than merge_threshold");
									
									while(true)
									{
										if( (input_ss.get(count2).end_frame-input_ss.get(count1).start_frame +1) >merge_threshold)
										{
													System.out.println("2. CUR2-CUR1 is getter than merge_threshold");
													shots_structure temp=new shots_structure();
													temp.end_frame=end_point;
													temp.start_frame=start_point;
													temp.tf=end_point-start_point+1;
													
													if(!output_ss.isEmpty())
													{	shots_structure last_ss= output_ss.get(output_ss.size() - 1);
														if(last_ss.start_frame==temp.start_frame)
														{
															output_ss.remove(output_ss.get(output_ss.size()-1));
														}
													}
													output_ss.add(temp);
													count1=end_point_frame;   //CHECK HERE.... THIS MIGHT BE AN ERROR...
													count2=count1+1;
													input_ss.get(end_point_frame).start_frame=start_point;
													
													break;
										}
										else  // If the next shot is basically matching
										{
											
											
													boolean val=check_fd(input_ss.get(count1), input_ss.get(count2));
													if(val)
													{
														System.out
																.println("Merging "+count1+" and "+count2 + " and Difference is "+( (input_ss.get(count2).end_frame-input_ss.get(count1).start_frame +1)));
															System.out
																	.println("The shots are same");
															end_point=input_ss.get(count2).end_frame;
															end_point_frame=count2;
															count2++;
															if(count2>=max_size)
															{	
																	shots_structure temp=new shots_structure();
																	temp.end_frame=end_point;
																	temp.start_frame=start_point;
																	temp.tf=end_point-start_point+1;
																	if(!output_ss.isEmpty())
																	{	
																		shots_structure last_ss= output_ss.get(output_ss.size() - 1);
																		if(last_ss.start_frame==temp.start_frame)
																		{
																			output_ss.remove(output_ss.get(output_ss.size()-1));
																		}
																	}
																	output_ss.add(temp);
																	
																	count1=end_point_frame;
																	count2=count1+1;
																	input_ss.get(end_point_frame).start_frame=start_point;
																	break;
															}
															
															
														
													}
													else if(!val)
													{
														System.out
														.println("The shots are diff");
						
															count2++;
															if(count2>=max_size || ( (input_ss.get(count2).end_frame-input_ss.get(count1).start_frame+1) >merge_threshold))
															{	
																shots_structure temp=new shots_structure();
																temp.end_frame=end_point;
																temp.start_frame=start_point;
																temp.tf=end_point-start_point+1;
																
																if(!output_ss.isEmpty())
																{	shots_structure last_ss= output_ss.get(output_ss.size() - 1);
																	if(last_ss.start_frame==temp.start_frame)
																	{
																		output_ss.remove(output_ss.get(output_ss.size()-1));
																	}
																}
																output_ss.add(temp);
																count1=end_point_frame;
																count2=count1+1;
																input_ss.get(end_point_frame).start_frame=start_point;																
																break;
															}
													
													}
										}
									
									}
									//This is after it breaks...
								}
							
						}
						else
						{	
							
							if(!output_ss.isEmpty())
							{	shots_structure last_ss= output_ss.get(output_ss.size() - 1);
								if(last_ss.start_frame==input_ss.get(count1).start_frame)
								{
									output_ss.remove(output_ss.get(output_ss.size()-1));
								}
							}
							
							output_ss.add(input_ss.get(count1));
							count1++;
							count2=count1+1;
							if(count2>=max_size)
								break;							
						}
			}	
	}



		public static boolean check_fd(shots_structure cur1,shots_structure cur2)
		{
			// TODO Auto-generated method stub
			
			int [] ff=new int[]{cur1.start_frame,cur1.end_frame,(cur1.start_frame+cur1.end_frame)/2};
			int [] sf=new int[]{cur2.start_frame,cur2.end_frame,(cur2.start_frame+cur2.end_frame)/2};
			
			
			for(int i=0;i<ff.length;i++)
			{
				for(int j=0;j<sf.length;j++)
				{
					
					CvMat d1 = feature_detection.featureDetect(complete_video.get(ff[i]));
					CvMat d2 = feature_detection.featureDetect(complete_video.get(sf[j]));
					
					if((feature_detection.match(d1,d2)) > feature_detection_threshold)
					{
						return true;
					}
				}
			}			
			return false;
		}




		public static void process_feature_detection(ArrayList<shots_structure> ss_hist_frames2, ArrayList<shots_structure> ss_fd_frames2) {
		// TODO Auto-generated method stub
		
		 	
			int counter=0;
			shots_structure temp_ss1=new shots_structure();
			temp_ss1.start_frame=0;
			temp_ss1.end_frame=ss_hist_frames2.get(counter).frame_number-1;			
			temp_ss1.tf=temp_ss1.end_frame-temp_ss1.start_frame+1;			
			ss_fd_frames2.add(temp_ss1);
			
			
		 	int prev_start=ss_hist_frames2.get(counter).frame_number;
		 	int sum_diff=0;
			while(counter+1 < ss_hist_frames2.size())
			{
				CvMat d1 = feature_detection.featureDetect(complete_video.get(ss_hist_frames2.get(counter).frame_number));
				CvMat d2 = feature_detection.featureDetect(complete_video.get(ss_hist_frames2.get(counter+1).frame_number));
				
				double fd_value=(feature_detection.match(d1,d2));
				
				 if( fd_value < feature_detection_threshold )
				{
					 	shots_structure temp_ss=new shots_structure();
						temp_ss.start_frame=prev_start;
						temp_ss.end_frame=ss_hist_frames2.get(counter+1).frame_number-1;
						
						temp_ss.tf=temp_ss.end_frame-temp_ss.start_frame+1;
						
						ss_fd_frames2.add(temp_ss);
						prev_start=ss_hist_frames2.get(counter+1).frame_number;
						sum_diff+=temp_ss.tf;
				}
				counter++;

			}			
			System.out.println("\nTotal Frames:"+sum_diff);
			
	}
	
}