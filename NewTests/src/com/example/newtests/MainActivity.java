package com.example.newtests;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	public Button losstestbutton, traceroutebutton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        losstestbutton = (Button) findViewById(R.id.lossbutton);
        traceroutebutton = (Button) findViewById(R.id.tracebutton);
        
        traceroutebutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
              	Intent i= new Intent(getApplicationContext(),TraceActivity.class);
            	startActivity(i);
            }
        });
        
        losstestbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
	          	Intent i= new Intent(getApplicationContext(),LossTestActivity.class);
	        	startActivity(i);
            }
        });
        
                
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
