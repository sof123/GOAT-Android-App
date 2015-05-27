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
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import com.google.api.services.youtube.model.SearchResult;



public class Donate extends Activity {
	TextView txt;
	static String EXTRA_MESSAGE = "killll";
	
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donate);
	    txt = (TextView) findViewById(R.id.text3);
	    ((TextView) findViewById(R.id.text3)).setMovementMethod(LinkMovementMethod.getInstance());
	    ((TextView) findViewById(R.id.text3)).setText(Html.fromHtml(getResources().getString(R.string.link)));
	}
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
}
