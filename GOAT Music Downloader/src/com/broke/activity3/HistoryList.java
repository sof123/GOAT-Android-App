package com.broke.activity3;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.broke.activity3.R;
import com.google.api.services.youtube.model.SearchResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HistoryList extends Activity implements AdapterView.OnItemClickListener {
	
	ListView listView;
	String json = "a";
	public static List<SearchItem> history = new ArrayList<SearchItem>();
	public static SearchItem [] array;
	public final static String EXTRA_MESSAGE = "hist";
	static int i2;
	Editor prefsEditor;
	String ytObjects = "a";
	Button clearHistory;
	SharedPreferences appSharedPrefs;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_history_list);
	    final String [] historyDisplay = new String[history.size()];
	    for (int i = 0;i<history.size();i++)
		{
			historyDisplay[i] = history.get(i).getTitle();
		}
	    final ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1,historyDisplay);
	    listView = (ListView) findViewById(R.id.list);
	    listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	    clearHistory = (Button) findViewById(R.id.button3);
	    clearHistory.setOnClickListener(new View.OnClickListener() 
	    {
			
			@Override
			public void onClick(View v) {
				
				
				history.clear();
				for (int i = 0;i<history.size();i++)
				{
					historyDisplay[i] = history.get(i).getTitle();
				}
				adapter.notifyDataSetChanged();
				
			}
			
		});
		appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()); 
		try
		{
			loadArray();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
			
		
		
		
		
	}
	@Override
	protected void onPause()
	{
		super.onPause();
		try {
			saveArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l)
	{
		i2 = i;
		TextView temp = (TextView) view;
		Toast.makeText(this,temp.getText(),Toast.LENGTH_SHORT).show();
		Intent j = new Intent(getApplicationContext(), HistoryProgress.class);
		j.putExtra(HistoryProgress.EXTRA_MESSAGE, "Downloading");
		startActivity(j);
	}
	public void saveArray() throws IOException
	{
		System.out.println("saves array");
		FileOutputStream outStream = new FileOutputStream(getExternalCacheDir() + "/RegionList.dat");
		ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);
		objectOutStream.writeInt(history.size()); // Save size first
		System.out.println("When I save, history is " + history);
		for(SearchItem r:history)
		    objectOutStream.writeObject(r);
		objectOutStream.close();    
	}
	public void loadArray() throws IOException
	{
		System.out.println("cache dir is " + getExternalCacheDir());
		FileInputStream inStream = new FileInputStream(getExternalCacheDir() + "/RegionList.dat");
		ObjectInputStream objectInStream = new ObjectInputStream(inStream);
		int count = objectInStream.readInt(); // Get the number of regions
		//List<SearchItem> rl = new ArrayList<SearchItem>();
		for (int c=0; c < count; c++)
		{
			try {
				SearchItem thing = (SearchItem) objectInStream.readObject();
				if (!Progress.hasSameValue(thing, history))
				{
					history.add(0, thing);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("After loading, history is " + history);
		objectInStream.close();
	}

}
