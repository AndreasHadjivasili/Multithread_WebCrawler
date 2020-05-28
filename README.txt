/**

@author Andreas Hadjivasili
	Copyright (C) 2020 Andreas Hadjivasili
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.
    Υou should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.	
		
	This program evolve a Multithreaded Web Crawler which is based on a given 
	URL from the user or on the default which is https://www.cs.ucy.ac.cy/. 
	This program is searching on specific depth , with a specific amount of 
	threads that they are given by the user. Also, the crawler is searching
	only pages in the host domain. Any other URL from a different host is 
	just printed as found by a thread and its not be searched further.
	
	Correct Usage:
	
	In the start of the program the user has to give three inputs: the start
	URL, the depth of the search and the number of threads.
	
	URL: For the URL we have two options:
			1. Write "ok" for the default URL 
			2. Give your own base URL
			
	Depth: The depth must be at least three
	
	Threads: The number of threads must be between 5 and 10 included
	
	Results Explained:
	
	examples:
	 <depth>     <thread id>     <URL>   
		0     		 0     		 https://www.google.com/
	    2      		 6    		 https://www.youtube.com/watch?v=WHv7hPJLZBI
	
	The above examples meaning: The first example is basically means that thread
	0 in depth 0 found the URL  https://www.google.com/ which is basically our 
	base URL given from the user. The second example it means that thread 6 in 
	his search in a depth 1 URL given to it, it found the URL 
	https://www.youtube.com/watch?v=WHv7hPJLZBI which is in the next depth, the
	depth 2.

*/