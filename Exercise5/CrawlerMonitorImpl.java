package Exercise5;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Andreas Hadjivasili
 * 
 * 
 * This is the monitor that help us to achieve the synchronization
 * between our threads. In this class we have a constructor for our
 * monitor and two functions that takes care of our critical path 
 * that is in our situation the graph. In our problem we have to 
 * accomplish two things. The first one is that only one thread 
 * can write on the graph each time. The second one is that our
 * threads have to work all at the same time in the same depth. 
 * To be specific if a thread ask for a URL the URLs of the current 
 * are already done , this thread has to wait until all the other 
 * threads are done with their URLs to continue all together to
 * the next depth. Thats the two things that our 2 functions 
 * accomplished.
 * 
 */
public class CrawlerMonitorImpl implements CrawlerMonitor {

	ArrayList<URL>[] graph;
	ArrayList<URL>[] visited;
	ArrayList<URL>[] added;
	String protocol;
	String host;
	URL base;
	int level;
	int index;
	int changelevel;
	int threads;
	int depth;	
	private final Lock lock;
	private final Condition wg; //write on graph
	
	
	
	
	/**
	 * 
	 * This is our only constructor that creates our monitor. This constructor
	 * takes as parameters three variables. The base URL that we will start our 
	 * search on, the depth of the search and the number of threads that we have 
	 * to handle. Based on these three inputs we are initializing our variables
	 * and we add to depth 0 of our graph the base URL. 
	 * 
	 * @param b The base URL
	 * @param d The depth 
	 * @param t The number of threads
	 */
	public CrawlerMonitorImpl(URL b, int d, int t) {
		
		changelevel=0;
		threads = t;
		level = 0;
		index = 0;
		base = b;
		depth = d;
		protocol = b.getProtocol();
		host = b.getHost();
		this.lock = new ReentrantLock();
		this.wg = lock.newCondition();
		graph = new ArrayList[depth+1];
		visited = new ArrayList[100];	
		added = new ArrayList[100];
		
		 for (int i = 0; i < depth+1; i++) { 
	            graph[i] = new ArrayList<URL>(); 
	        } 
		 		 
		 for (int i = 0; i < 100; i++) { 
	            visited[i] = new ArrayList<URL>(); 
	            added[i] = new ArrayList<URL>(); 
	        } 
		 
		 
		graph[0].add(base); 

	}
	

	
	/**
	 * 
	 * This function is called when a thread wants a URL to search in.
	 * The problem is that our threads have to work all at the same 
	 * time in the same depth. To be specific if a thread ask for a URL
	 * and the URLs of the current depth are already done , this thread 
	 * has to wait until all the other threads are done with their URLs
	 * to continue all together to the next depth. So in our function
	 * we implemented this with a condition variable for our threads
	 * that makes them wait until all of the finish if we have a 
	 * situation like the above we described. Also some other checks
	 * are made such as if we did not find any new URLs from the
	 * previous searches and we cannot achieve the given depth we
	 * stop the searching as deep as we can reach.
	 * 
	 */
	public URL AskForUrl(CrawlerThread g) throws InterruptedException{
		
		URL f = null;
		lock.lock();	
		
		try {

		if(graph[level].size() == 0)
			return null;
			
		if(level == depth) 				
			return null;		

	
		if(graph[level].size() == index) {		
			changelevel++;
			wg.await();;
		}


		if(level == depth) 				
			return null;

		
		if(graph[level].size() == 0)
			return null;
		
		f= graph[level].get(index);
		int num = (f.toString().length()) % 100;
		visited[num].add(f);		
		index++;
		
		return f;
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return f;
		
			
	}

	
	
	/**
	 * The problem that we handle with this function is that
	 *  only one thread can write on the graph each time. So
	 *  a thread when it finishes its searching its stored its
	 *  results in a list. After collecting these results it
	 *  has to make two things: First access the visited 
	 *  hash table to check if we visited before this URL
	 *  and if we did not add it in the graph to be searched
	 *  on the depth or just printing it as just found.Then
	 *  we have a small check that we are doing that is 
	 *  checks if the current thread is the last one doing
	 *  a search in the current depth. if it is it wakes up
	 *  all the threads that were waiting to let them continue
	 *  to the next depth and make the appropriate changes to 
	 *  the variables for the next depth.
	 */
	public void WriteonGraph(CrawlerThread g , ArrayList<URL> f) {

		
		lock.lock();
		
		int id = g.getid();
		
		if(level == 0)
			System.out.println("  " + (level) + "      " + id + "     " + base.toString());	
		
			
		for (int i = 0; i < f.size(); i++) {
			
			URL s = f.get(i);
			int num = (s.toString().length()) % 100;
			
	  		  if((!(visited[num].contains(s))) && (!(added[num].contains(s)))) {
  		  
	  	  		  if(host.equals(s.getHost())) {
	  	  			graph[level+1].add(s);
	  	  			added[num].add(s);
	  	  		  }
	  	  				  	  		  
	  	  		  	System.out.println("  " + (level+1) + "      " + id + "     " + s.toString());	
	  	  		  }						
		}

		if(changelevel+1 == threads) {		

			level++;
			index = 0;
			changelevel= 0;
			wg.signalAll();
			
		}		
			lock.unlock();
	}
}
