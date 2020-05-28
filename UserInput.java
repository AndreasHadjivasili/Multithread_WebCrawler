package Exercise5;

import java.util.Scanner;

/**
 * @author Andreas Hadjivasili
 * 
 * this is the class that is responsible to take all the necessary inputs 
 * from the user, check if they are correct and in the given limits. If
 * everything is okay it will return from the getURL function the base
 * URL that the crawler will work , from the getDepth function the 
 * depth that we will search and from the getThreads function the amount 
 * of threads that we will use.
 * 
 * 
 *
 */
public class UserInput {

	static Scanner scan = new Scanner(System.in);
	
	
	 /**
	 * 
	 * function that returnS the base
	 * URL that the crawler will work
	 * 
	 * @return The base URL
	 */
	static String getURL() {
		 
		 
		boolean flag = true;
		String s = null;
		String sure1 = "http://";
		String sure2 = "https://";
		
		while (flag) {
			
			System.out.println("Please choose one of the below :");
			System.out.println();
			System.out.println(" * Write the URL that you want to Crawl");
			System.out.println(" * Or write Ok if you want to continue with the default");
			System.out.println();
			
			System.out.print("Input : ");
			 s = scan.nextLine();
			
			if( s.equals("ok")|| s.equals("Ok") || s.equals("OK")) {
				
			s = "https://www.cs.ucy.ac.cy";
			flag = false;
				
			}
			else {
				
				if(s.length() > 7) {
				String ch1 = s.substring(0, 7);
				String ch2 = s.substring(0, 8);
				
				if (ch1.equals(sure1) || ch2.equals(sure2)) {
					
					flag = false;
				}
				else {
					System.out.println("Wrong Input please give a new input");
				}
				
				
				}
				
			}
			
			
			}
					
		
		return s; 
	}
	 
	 
	 
	 /**
	 * 
	 * function that returns the depth
	 * that the crawler will search for.
	 * 
	 * @return The depth
	 */
	 static int getDepth() {
		 boolean flag = true;
		 int depth = 0;
		 
			while (flag) {
				System.out.println();
				System.out.println("Please give the depth of the search ");
				System.out.println();
				System.out.println(" *** Depth must be at least 3  ***");
				System.out.println();
				
				System.out.print("Depth : ");
				 depth = scan.nextInt();
				
				 if (depth >= 3)
					 flag = false;
				
				 else
					 System.out.println("Wrong Depth size please give one bigger than 3");
						
				
			}
			
		 
		 
		 return depth;
	 }
	 
	 
	 /**
	 * 
	 * function that returns the amount
	 * of threads that the crawler create.
	 * 
	 * @return The number of threads
	 */
	 
	 static int getThreads() {
		 boolean flag = true;
		 int threads = 0;
	 
			
			while (flag) {
				
				System.out.println();
				System.out.println("Please give the number of threads ");
				System.out.println();
				System.out.println(" *** Number of threads must be at between 5 and 10  ***");
				System.out.println();
				
				System.out.print("Threads : ");
				 threads = scan.nextInt();
				
				 if (threads >= 5 && threads <=10 )
					 flag = false;
				
				 else
					 System.out.println("Wrong number of threads please give one between 5 and 10");
						
				
			}
	 
	
	return threads;
	
	 }
 

}
