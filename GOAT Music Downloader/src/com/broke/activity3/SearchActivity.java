package com.broke.activity3;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.broke.activity3.R;
import com.google.android.gms.ads.*;
import com.google.api.services.youtube.model.SearchResult;



public class SearchActivity extends Activity implements AdapterView.OnItemClickListener {
	ListView lst;
	String [] a = {"J","B","C"};
	static String query2;
	static int i2 = 0;
	
	class MyTask extends AsyncTask<Void, String, Void>
	{
		@Override
		protected void onPreExecute() 
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected void onProgressUpdate(String... values) 
		{
			// TODO Auto-generated method stub
		}
		@Override
		protected void onPostExecute(Void result) 
		{
			// TODO Auto-generated method stub
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		System.out.println("file is " + Environment.getExternalStorageDirectory().toString());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_activity);
		//AdView adv = new AdView(this);
		//adv.setAdUnitId("ca-app-pub-9608512167423649/9907178613");
		//adv.setAdSize(AdSize.MEDIUM_RECTANGLE);
	    Intent intent = getIntent();
	    lst = (ListView) findViewById(R.id.list);
	    System.out.println("After list");
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      query2 = query;
	      SearchYoutube.main(a);
	      Iterator<SearchResult> it = SearchYoutube.searchListResults.iterator();
	      SearchYoutube.prettyPrint(it, query2);
	      ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1,SearchYoutube.ytdisplay);
	      lst.setAdapter(adapter);
	      lst.setOnItemClickListener(this);
	}
	}
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() 
	{
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l)
	{
		i2 = i;
		TextView temp = (TextView) view;
		Toast.makeText(this,temp.getText(),Toast.LENGTH_SHORT).show();
	    	if (isExternalStorageWritable() == true) 
	    	{	    			 	    			    
	    		Intent j = new Intent(getApplicationContext(), Progress.class);
	    		j.putExtra(Progress.EXTRA_MESSAGE, "Downloading");
	    		startActivity(j);
			} 
	}
		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public File getAlbumStorageDir(String albumName) 
	{
	    // Get the directory for the user's public music directory. 
	    File file = new File(Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_MUSIC), albumName);
	    if (!file.mkdirs()) {
	        Log.e("SearchActivity", "Directory not created");
	    }
	    return file;
	}
	
}
