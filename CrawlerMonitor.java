package Exercise5;

import java.net.URL;
import java.util.ArrayList;

/**
 * @author Andreas Hadjivasili
 * 
 * This is the interface that includes the functions
 * that we need for the monitor
 *
 */
public interface CrawlerMonitor {

	public void WriteonGraph(CrawlerThread g, ArrayList<URL> f);
	
	public URL AskForUrl(CrawlerThread g) throws InterruptedException;
}
