package com.broke.activity3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Search;
import com.google.api.services.youtube.YouTube.Videos;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.Video;

/**
 * Print a list of videos matching a search term.
 *
 * @author Jeremy Walker
 */
public class SearchYoutube {
	
	   
	   public static List<SearchResult> searchListResults = new ArrayList<SearchResult>();
	   public static List<String> ytArrayList = new ArrayList<String>();
	   public static SearchItem [] ytstuff = new SearchItem[10];
	   public static String [] ytdisplay = new String[10];
	   public static int AM = 65521;
	   public static HashMap<String,SearchItem> titleToItem = new HashMap();
	   public String YTURL = "http://www.youtube-mp3.org/get?ab=128&video_id=";
	   public static String timeNow = Long.toString(java.lang.System.currentTimeMillis()); 
	   
	   
	   

    /**
     * Define a global variable that identifies the name of a file that
     * contains the developer's API key.
     */
    private static final String PROPERTIES_FILENAME = "youtube.properties";

    private static long NUMBER_OF_ITEMS_RETURNED = 10;

    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;

    /**
     * Initialize a YouTube object to search for videos on YouTube. Then
     * display the name and thumbnail image of each video in the result set.
     *
     * @param args command line args.
     */
    public static void main(String[] args) {
        // Read the developer key from the properties file.
        Properties properties = new Properties();

        try {
            // This object is used to make YouTube Data API requests. The last
            // argument is required, but since we don't need anything
            // initialized when the HttpRequest is initialized, we override
            // the interface and provide a no-op function.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() 
            {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-search-sample").build();

            // Prompt the user to enter a query term.

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setVideoCategoryId("10");

            // Set your developer key from the Google Developers Console for
            // non-authenticated requests. See:
            // https://console.developers.google.com/
            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey("AIzaSyDzJbDdWBgzKH90gQYaC76YUl6wOjrYlZU");
            search.setQ(SearchActivity.query2);

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults((long)40);

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            /*String idpasser = "";
            for (SearchResult video:searchResultList)
            {
            	String videoID = video.getId().toString();
            	idpasser = idpasser + videoID + ",";
            	
            }
            removeLastChar(idpasser);
    
            YouTube.Videos.List listofvids = youtube.new Videos().list(idpasser);
            if (listofvids.getVideoCategoryId() != "Music")
            {
            	
            }*/
            searchListResults = searchResultList;
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        
        
    }

    /*
     * Prompt the user to enter a query term and return the user-specified term.
     */
    private static String getInputQuery() throws IOException {

        String inputQuery = "";

        System.out.print("Please enter a search term: ");
        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
        inputQuery = bReader.readLine();

        if (inputQuery.length() < 1) {
            // Use the string "YouTube Developers Live" as a default.
            inputQuery = "YouTube Developers Live";
        }
        return inputQuery;
    }

    /*
     * Prints out all results in the Iterator. For each result, print the
     * title, video ID, and thumbnail.
     *
     * @param iteratorSearchResults Iterator of SearchResults to print
     *
     * @param query Search query (String)
     */
    public static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) 
    {
    	int p = (int) NUMBER_OF_ITEMS_RETURNED;
        	for (int i = 0; i < p ;i++)
        	{
        		if (iteratorSearchResults.hasNext())
        		{
	            SearchResult singleVideo = iteratorSearchResults.next();
	            if (!isSong(singleVideo.getSnippet().getTitle()))
	            {
	            	i--;
	            	continue;
	            }
	            ResourceId rId = singleVideo.getId();
	            try {
	            	YouTube youtube2 = null;
	            	youtube2 = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() 
	                {
	                    public void initialize(HttpRequest request) throws IOException {
	                    }
	                }).setApplicationName("youtube-cmdline-search-sample").build();
	            	System.out.println("youtube built");
	            	System.out.println("rid.getkind is " + rId.getKind());
	            	YouTube.Search theSearch = youtube2.search();
	            	YouTube.Search.List theList =  theSearch.list(rId.getVideoId());            	
	            	System.out.println("video category id is " + theList.getVideoCategoryId());		
	            	/*
					if (!(youtube2.new Videos().list(rId.toString()).getVideoCategoryId().equals("10")))
					{
						i-=2;
						System.out.println("I is" + i);
						System.out.println("P is" + p);
						continue;
								
					}
					*/
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
	            // Confirm that the result represents a video. Otherwise, the
	            // item will not contain a video ID.
	            	System.out.println("Before if");
	            	
		            if (rId.getKind().equals("youtube#video")) 
		            {
		            	System.out.println("inside if statement");
		                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
		                //put data in array
		                ytstuff[i] = new SearchItem(singleVideo.getSnippet().getTitle(), "http://YouTubeInMP3.com/fetch/?video=http://www.youtube.com/watch?v=" + rId.getVideoId().toString());
		                titleToItem.put(singleVideo.getSnippet().getTitle(),ytstuff[i]);
		                
		            }
	     
        		}
        	System.out.println("out of for loop");
        	//break loop;
            }
        for (int k = 0;k<NUMBER_OF_ITEMS_RETURNED;k++)
        {
        	System.out.println("before yt display");
        	ytdisplay[k] = ytstuff[k].getTitle();
        }
        System.out.println("ytdisplay before cleanup: " + Arrays.toString(ytdisplay));
        for (int i = 0;i<ytdisplay.length;i++)
        {
        	ytdisplay[i] = cleanUpTitle(ytdisplay[i]);
        }
        System.out.println("ytdisplay after cleanup: " + Arrays.toString(ytdisplay));
    }
    
    
    public static String removeLastChar(String s) 
    {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1);
    }
    static String cleanUpTitle(String title)
    {
    	String retVal = "";
    	retVal = title.replace("(Official Video)","");
    	retVal = retVal.replace("Official Video","");
    	retVal = retVal.replace("(Lyrics on screen)","");
    	retVal = retVal.replace("(Video)","");
    	retVal = retVal.replace("OFFICIAL VIDEO","");
    	retVal = retVal.replace("OFFICIAL MUSIC VIDEO", "");
    	retVal = retVal.replace("Official Music Video", "");
    	retVal = retVal.replace("Music Video", "");
    	retVal = retVal.replace("MUSIC VIDEO", "");
    	retVal = retVal.replace("(OFFICIAL MUSIC VIDEO)", "");
    	retVal = retVal.replace("(Official Music Video)", "");
    	retVal = retVal.replace("(Music Video)", "");
    	retVal = retVal.replace("(MUSIC VIDEO)", "");
    	retVal = retVal.replace("(Lyric Video)", "");
    	retVal = retVal.replace("(Audio)", "");
    	retVal = retVal.replace("(lyrics)","");
    	retVal = retVal.replace("(HD)", "");
    	retVal = retVal.replace("()", "");
    	return retVal;
    }
    static boolean isSong(String title)
    {
    	if (title.contains("FULL ALBUM") || title.contains("Full Album") || title.contains("FULL MIXTAPE") || title.contains("Full Mixtape")
    			|| title.contains("Full CD") || title.contains("FULL CD") || title.contains("HQ")
    			|| title.contains("Live Show") || title.contains("Live performance") || title.contains("Live Performance") || title.contains("(HD)")
    			|| title.contains(".wmv") || title.contains(".avi") || title.contains(".mp4)") || title.contains("Official Video") || title.contains("Official Music Video")
    			|| title.contains("OFFICIAL VIDEO") || title.contains("OFFICIAL MUSIC VIDEO") || title.contains("HQ") || title.contains("lyrics on screen")
    			|| title.contains("Lyrics on Screen") || title.contains("(live)") || title.contains("(Live)") || title.contains("with lyrics") || title.contains("with Lyrics")
    			|| title.contains("[LYRICS]") || title.contains("[Lyrics]") || title.contains("(live") || title.contains("Full EP") || title.contains("FULL EP") || title.contains("Official lyrics")
    			|| title.contains("on-stage") || title.contains("(LIVE"))
    		    
    		
    	{
    		return false;
    	}
    	return true;
    }
    
    public static int cc(String a)
    {            
        int c = 1, b = 0, d, e;
        char[] chars = a.toCharArray();
        for(e =0; e< chars.length; e ++)
        {
            d = chars[e];
            c = (c + d) % AM;
            b = (b + c) % AM;
        }
        return b << 16 | c;
    }
}


