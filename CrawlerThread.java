package Exercise5;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * @author Andreas Hadjivasili
 * 
 * This is a class that implements Runnable and basically this
 * is the class that represents and runs the Crawler threads.
 * In this class we have a constructor for our threads, a toString
 * function, a getter function for the id of the thread and finally
 * the run function that does all the crawling job.
 *
 */
public class CrawlerThread implements Runnable {
	private static int id = 0;
	private final CrawlerMonitor m;
	private int myID;
	private ArrayList<URL> found;
	private boolean flag;
	

	/**
	 * This is our only constructor that takes as input
	 * the monitor that handles all the synchronization
	 * and initializes all the variables of the thread. 
	 * 
	 * @param m The monitor
	 */
	public CrawlerThread(CrawlerMonitor m) {
		this.m = m;
		this.myID = CrawlerThread.id++;
		this.flag = true;
	}
	
	
	/**
	 * Returns the id of the tread
	 * 
	 * @return the id
	 */
	public int getid() {
		return this.myID;
	}


	@Override
	public String toString() {
		return "<Thread (" + this.myID + ")>";
	}
	

	/**
	 *
	 *This is the function run that does all the search work in the URL.
	 *In this function a thread that starts its search firstly ask the 
	 *monitor if there is a URL to send him. If the URL exists and its not 
	 *null we continue else we stop the operation. In the case of a valid 
	 *URL we start by trying to open a connection with the given URL. By
	 *opening this connection we get some information that we need to
	 *check if the connection is fine. By the word fine we mean that the 
	 *connection does not return 404 or 408 error so we are okay to go
	 *ahead and read the html file. As we said if the connection is okay we
	 *go forward and we read the html in a string and then we are searching
	 *in it all the URL that we need. When we are finished with the html 
	 *file and we found all the URL and its stored in a local list found
	 *we are ready to write our results in the graph, so we are moving to
	 *the monitor with our result to ask for access in the graph.   
	 * 
	 */
	@Override
	public void run() {
		
		while (true) {
			
			this.found = new ArrayList<URL>();
			URL r = null;
			try {
				r = m.AskForUrl(this);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			if (r==null) {
				break;
			}

			HttpURLConnection huc = null;
			try {
				huc = ( HttpURLConnection )  r.openConnection ();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			try {
				huc.setRequestMethod ("GET");
			} catch (ProtocolException e) {
				e.printStackTrace();
			} 
			try {
				huc.connect () ;
			} catch (IOException e) {
				
				System.err.println ("Can not connect to the Remote host");
				m.WriteonGraph(this,found);
				continue;		
			} 
			int code = 0;
			try {
				code = huc.getResponseCode();
			} catch (IOException e) {

				System.err.println ("Connection with the Remote host lost!");
				m.WriteonGraph(this,found);
				continue;
			}
			huc.disconnect();

			if (code == 404 || code == 408) {
				
				m.WriteonGraph(this,found);
				continue;				
			}

			ArrayList<Integer> indexes = new ArrayList();
			StringBuilder read = new StringBuilder();
	        BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(r.openStream()));
			} catch (IOException e1) {
				System.err.println ("Remote host timed out during opening connection operation");	
			}

			if(in == null) {				
				m.WriteonGraph(this,found);
				continue;				
			}
			
	                String inputLine= "";
	                try {
						while ((inputLine = in.readLine()) != null) {
							read.append(inputLine);                	
						}
					} catch (IOException e) {
						System.err.println ("Remote host timed out during read operation");
						m.WriteonGraph(this,found);
						continue;		
						
					}
	                
	                try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

	                
	                
	                String html = read.toString();              
	                String t;
	                t = "href=" ;
	                html.replaceAll("\\s", "");
	                
	                int k = 0;
	                String fin = "";
	                
	                while(k<html.length()) {
	                	
	                	if(html.charAt(k) == '<' && html.charAt(k+1) == 'a') {
	                		
	                		while(html.charAt(k) != '>') {
	                			fin = fin + html.charAt(k) + "";
	                			k++;
	                		}
	                		fin = fin + html.charAt(k) + "";
	                	}
	                	
	                	else
	                		k++;	                	            		              		
	                }

	                
	                // moving all the indexes just after the char '=' of
	                //the string "href="
	                for (int i = -1; (i = fin.indexOf(t, i + 1)) != -1; i++) 
	                    indexes.add(i+6);
	                
	                
            
	          for (int i = 0; i < indexes.size(); i++) {
				
	        	 String w = "" ;	        	  
	        	 int p = indexes.get(i); 
	        	 
	        	 if(p+4 >= fin.length())
	        		 continue;
	        	 
	        	 String ch1 = fin.substring(p, p+4);	        	  
	        	 int j = p; 
	        	 
	        	  while(fin.charAt(j) != '"' && fin.charAt(j) != '>') {	        		 
	        		  char a = fin.charAt(j);
	        		  w = w + a + "" ;
	        		  j++;
	        	  }
	        	  
       	  
	        	  if(w.contains(" "))
	        		    w = w.replace(" ", "%20");
	        	  	        	  
	        	  if(ch1.equals("http")) {
	        		  URL g = null;
					try {
						g = new URL(w);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
	        		  found.add(g);
   		 
	        	  }
	        	  else if(fin.charAt(p) == '/') {
	        		  
	        		  URL g = null;
					try {
						g = new URL(r.getProtocol(), r.getHost(), w);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
	        		  found.add(g);

	        	  }	        	  	  
			}
	          
	          // Finally we will ask the monitor to write
	          //all the results that we found in the graph
	          m.WriteonGraph(this,found);

		}		
	}
}
