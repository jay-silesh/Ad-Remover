import java.util.ArrayList;
import java.util.Iterator;


public class testing {

	public static void maine32(String[] argv)
	{
		int []i=new int[3];
		settibvndk(i);
		i[2]=333;
		//settibvndk(i);
		for(int ii=0;ii<i.length;ii++)
		{
			System.out.println(i[ii]);
		}
		
		
		
	}
	
	
	public static void settibvndk(int []a)
	{
		a[0]=999;
		a[2]=888;
		a[1]=777;
	}
	
}
