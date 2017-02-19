
/*
    Author : Amol Bidrolkar
	Date Created : 24/11/2016
	Problem Definition : To search the query from user inputs for specified number of links from search engine and store the result in a file

*/
package timepass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MetaSearchEngine 
{
//  public static final String BING_SEARCH_URL  = "https://www.bing.com/search";
  public static final String DATACITE_META_SEARCH_URL  = "https://search.datacite.org/ui";
  //Testing Metadata search engine https://search.datacite.org/ui?q=java&rows=10
  //Testing Metadata search engine  URL https://www.search.com/web?q=java&qo=serpSearchBox&qsrc=10&ot=organic
  
	public static void main(String[] args) throws IOException {
				
		PrintWriter pw = null;
		String outputFolder = ".";
		File output = null;
		String fileName="B";
		    		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Please enter the search term.");
		String searchTerm = scanner.nextLine();
		
		System.out.println("Please enter the number of results. Example: 5, 10, 15,  20");
		
		int num=0;
		num = scanner.nextInt();
		scanner.close();
		
		System.out.println("Number of links requested : "+num);
		
		//String searchURL = BING_SEARCH_URL + "?q=" + searchTerm + "&count=" + num;
		String searchURL = DATACITE_META_SEARCH_URL + "?q=" + searchTerm + "&rows=" + num;
		//without proper User-Agent, we will get 403 error
		Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
		
		//below will print HTML data, save it to a file and open in browser to compare
		 System.out.println(doc.html());
		
		//If google search results HTML change the <h3 class="r" to <h3 class="r1"
		//we need to change below accordingly
		//Elements results = doc.select("li.b_algo h2 a");  //p.web-result-url>b   div.title>a
		 Elements results = doc.select("div.title>a"); 
		int rs=results.size();
		System.out.println(" Results size : "+rs);
		
		// code to create a file and folder 
		try {
	        File dir = new File(outputFolder + '/' + "HTMLSources");
	        if (!dir.exists()) {
	            boolean success = dir.mkdirs();
	            if (success == false) {
	                try {
	                    throw new Exception(dir + " could not be created");
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }

	        output = new File(dir + "/" + fileName);
	        if (!output.exists()) {
	            try {
	                output.createNewFile();
	            } catch (IOException ioe) {
	                ioe.printStackTrace();
	            }
	        }
	        pw = new PrintWriter(new FileWriter(output, true));
	     
                  //loop to print on console and write to a file
	        for (int i = 0; i < results.size(); i++) 
	        {
			   	String linkHref = results.get(i).attr("href");
				String linkText = results.get(i).text();
				System.out.println("\n Link "+i+" Search  Text Result :" + linkText + ", URL is :" + linkHref);
				//System.out.println("<a href=" + linkHref + ">" + linkHref + "</a>");
			     
				//Writing to a file 
			    pw.print("\n Link "+i+" Search  Text Result :" + linkText + ", URL is :" + linkHref);
	            pw.print("\n");
			}
	      } 
		   catch (IOException ioe)
		    {
	    	  ioe.printStackTrace();
		    } finally 
	    	{
              pw.close();
	        }
	}
}
