package com.broke.activity3;
import com.broke.activity3.R;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //make search button
        Button button1 = (Button) findViewById(R.id.button1);
        Button history = (Button) findViewById(R.id.history);
        Button donate = (Button) findViewById(R.id.donate);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D0D0D0")));
        bar.setDisplayShowTitleEnabled(false);  // required to force redraw, without, gray color
        bar.setDisplayShowTitleEnabled(true);
        button1.setOnClickListener(new View.OnClickListener() 
        {
			
			@Override
			public void onClick(View v)
			{
				onSearchRequested();
			}
		});
        history.setOnClickListener(new View.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		Intent j = new Intent(getApplicationContext(), HistoryList.class);
	    		j.putExtra(HistoryList.EXTRA_MESSAGE, "Downloading");
	    		startActivity(j);
        	}
        });
        
        
        donate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent j = new Intent(getApplicationContext(), Donate.class);
	    		j.putExtra(Donate.EXTRA_MESSAGE, "Downloading");
	    		startActivity(j);
				
			}
		});
		
		
    }

        
    /** Called when the user clicks the Send button */
  //  public void sendMessage(View view)
   // {    
    	
  //  }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
 }

