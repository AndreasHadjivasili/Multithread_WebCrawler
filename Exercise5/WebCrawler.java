package Exercise5;


import java.io.*;
import java.net.*;
import java.util.Scanner;


/**
 * @author Amdreas Hadjivasili
 * 
 * This is the class that contains only a main that is running
 * all the program. In this class we are taking all the information
 * that we needed form the user: base URL , depth and threads. After
 * we create our Monitor that is responsible for all the synchronization
 * of our threads and finally we create the given amount of Crawler threads
 * and we let them out start searching for URLs. 
 * 
 */
public class WebCrawler {
	

	public static void main(String[] args) throws IOException {
		
		int depth;
		URL base;
		Scanner scan = new Scanner(System.in);
		String s;
		int threads;
				
		s = UserInput.getURL();
		depth = UserInput.getDepth();
		threads = UserInput.getThreads();		
		base = new URL(s);	 
		
		System.out.println("--------------------------------------------------------------------------------------------------");
		
		System.out.println(" <depth>     <thread id>     <URL>   "  );
		
		
		CrawlerMonitor m = new CrawlerMonitorImpl(base, depth,threads);
		
		Thread[] th = new Thread[threads];
		
		for (int i = 0; i < threads; i++) {
			th[i] = new Thread(new CrawlerThread(m), "Crawler " + i);		
		}
		
		
		for (int i = 0; i < threads; i++) {
			th[i].start();
			
		}

	}

}
