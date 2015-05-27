package com.broke.activity3;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.broke.activity3.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

 public class Progress extends Activity {
     private static final int PROGRESS = 0x1;
     public final static String EXTRA_MESSAGE = "em";
     int tryCount = 0;

     private ProgressBar mProgress;
     private int mProgressStatus = 0;
     ProgressDialog pd, pd2;
     
     private Handler mHandler = new Handler();

     protected void onCreate(Bundle icicle) {
         super.onCreate(icicle);
         pd = ProgressDialog.show(Progress.this, "Downloading", "Downloading " +  SearchYoutube.ytstuff[SearchActivity.i2].getTitle());
         pd.setCancelable(true);
         setContentView(R.layout.activity_progress_bar);
         mProgress = new ProgressBar(this);
         // Start lengthy operation in a background thread
         final long start = System.currentTimeMillis()/1000;
         new Thread(new Runnable() {
             public void run() {
            	 while (true)
            	 {
            		 File target = new File("a");
	            	 try {
	            		 	System.out.println("File url is " + SearchYoutube.ytstuff[SearchActivity.i2].getUrl());
	            		 	System.out.println("Directory is " + Environment.getExternalStorageDirectory() );
	            		    target =  new File(Environment.getExternalStorageDirectory().toString() + "\\" + SearchYoutube.ytstuff[SearchActivity.i2].getTitle() + ".mp3");
	            		 	System.out.println("before download");
	            		 	SearchItem item = new SearchItem(SearchYoutube.ytstuff[SearchActivity.i2].getTitle(),SearchYoutube.ytstuff[SearchActivity.i2].getUrl());
	                        if (!hasSameValue(item, HistoryList.history))
	                        {
	                        	HistoryList.history.add(0, item);
	                        }
							org.apache.commons.io.FileUtils.copyURLToFile(new URL(SearchYoutube.ytstuff[SearchActivity.i2].getUrl()), target);
	                        //saveFile(target.toString(), new URL(SearchYoutube.ytstuff[SearchActivity.i2].getUrl()));	
	                        //saveUrlToFile(target, SearchYoutube.ytstuff[SearchActivity.i2].getUrl());
							//saveUrl(Environment.getExternalStorageDirectory().toString() + "\\" + SearchYoutube.ytstuff[SearchActivity.i2].getTitle() + ".mp3", SearchYoutube.ytstuff[SearchActivity.i2].getUrl());
	            	 } catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							Log.e("SearchActivity","Malformed URL");
					} catch (IOException e) {
						    e.printStackTrace();
						    //if i've tried and failed 10 times
						    if (tryCount >= 10)
						    {
						    	if (pd.isShowing())
						    	{
						    		pd.dismiss();
						    		return;
						    	}
						    	break;
						    }
						    Log.e("Progress", "input/output is wrong");
						    tryCount++;
						    continue;
							// TODO Auto-generated catch block	
					}
						System.out.println("before the while true statement");
						while (true)
						{
							System.out.println("mProgressStatus is " + mProgressStatus);
							
							//update the progress status
							if (mProgressStatus < 100) {
								//100 seconds 
			            		 int nuProgress = (int) ((double) ((System.currentTimeMillis()/1000) - start) * 1);
			            		 System.out.println("nuProgress is " + nuProgress);
			            		 pd.incrementProgressBy(nuProgress-mProgressStatus);
			            		 mProgressStatus = nuProgress;
							}
							break;
						}
						if (mProgressStatus >= 100)
						{
							if (pd.isShowing())
							{
								pd.dismiss();
							}
							break;
						}
							System.out.println("File size is " + target.length());
							if (target.length() > 100000)
							{
								
								if (pd.isShowing())
								{
									pd.dismiss();
								}
								break;
							}
							
            	 }
					
            }
         }).start();
     }

      
     private boolean isCompletelyWritten(File file) {
    	    RandomAccessFile stream = null;
    	    try {
    	        stream = new RandomAccessFile(file, "rw");
    	        return true;
    	    } catch (Exception e) {
    	        System.out.println("Skipping file " + file.getName() + " for this iteration due it's not completely written");
    	    } finally {
    	        if (stream != null) {
    	            try {
    	                stream.close();
    	            } catch (IOException e) {
    	                System.out.println("Exception during closing file " + file.getName());
    	            }
    	        }
    	    }
    	    return false;
    	}
     public void saveUrl(final String filename, final String urlString)
    	        throws MalformedURLException, IOException 
    {
    	    BufferedInputStream in = null;
    	    FileOutputStream fout = null;
    	    try {
    	        in = new BufferedInputStream(new URL(urlString).openStream());
    	        fout = new FileOutputStream(filename);

    	        final byte data[] = new byte[1024];
    	        int count;
    	        while ((count = in.read(data, 0, 1024)) != -1) {
    	            fout.write(data, 0, count);
    	        }
    	    } finally {
    	        if (in != null) {
    	            in.close();
    	        }
    	        if (fout != null) {
    	            fout.close();
    	        }
    	    }
    	}
     public void saveFile(String filename, URL link) throws IOException
     {
    	 InputStream in = new BufferedInputStream(link.openStream());
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		 byte[] buf = new byte[1024];
		 int n = 0;
		 while (-1!=(n=in.read(buf)))
		 {
		    out.write(buf, 0, n);
		 }
		 out.close();
		 in.close();
		 byte[] response = out.toByteArray();
 
		 FileOutputStream fos = new FileOutputStream(filename);
		 fos.write(response);
		 fos.close();
     }
     
     public void saveUrlToFile(File saveFile,String location) throws IOException {
         URL url;
         try {
             url = new URL(location);
             BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
             BufferedWriter out = new BufferedWriter(new FileWriter(saveFile));
             char[] cbuf=new char[255];
             while ((in.read(cbuf)) != -1) {
                 out.write(cbuf);
             }
             in.close();
             out.close();

         } catch (MalformedURLException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
 }
     
     public static boolean hasSameValue(SearchItem item, List<SearchItem> list)
     {
    	 for (int i = 0;i<list.size();i++)
    	 {
    		 if (list.get(i).getTitle().equals( item.getTitle()))
    		 {
    			 return true;
    		 }
    	 }
    	 return false;
     }
 }