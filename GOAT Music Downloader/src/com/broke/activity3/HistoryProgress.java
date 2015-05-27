package com.broke.activity3;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;

import com.broke.activity3.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

 public class HistoryProgress extends Activity {
     private static final int PROGRESS = 0x1;
     public final static String EXTRA_MESSAGE = "em";
     int tryCount = 0;

     private ProgressBar mProgress;
     private int mProgressStatus = 0;
     ProgressDialog pd, pd2;
     
     private Handler mHandler = new Handler();
     protected void onCreate(Bundle icicle) {
         super.onCreate(icicle);
         pd = ProgressDialog.show(HistoryProgress.this, "Downloading", "Downloading " +  HistoryList.history.get(HistoryList.i2).getTitle());
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
							org.apache.commons.io.FileUtils.copyURLToFile(new URL(SearchYoutube.ytstuff[SearchActivity.i2].getUrl()), target);
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
							while (mProgressStatus < 100) {
			            		 int nuProgress = (int) ((double) ((System.currentTimeMillis()/1000) - start) * 1.66666666);
			            		 System.out.println("nuProgress is " + nuProgress);
			            		 pd.incrementProgressBy(nuProgress-mProgressStatus);
			            		 mProgressStatus = nuProgress;
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
         /*new Thread(new Runnable() {
             public void run() {
            	 while (mProgressStatus < 100) {
            		 mProgressStatus = (int) ((double) ((System.currentTimeMillis()/1000) - start) * 1.66666666);
                     // Update the progress bar
                     mHandler.post(new Runnable() {
                         public void run() {
                             mProgress.setProgress(mProgressStatus);
                         }
                     });
                 }            	 
             }
         }).start();*/
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
    	        throws MalformedURLException, IOException {
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
 }