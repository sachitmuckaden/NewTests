package com.example.newtests;


import com.example.workers.TracerouteWorker;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class TraceActivity extends Activity {

	public TextView resultsview;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace);
		resultsview = (TextView) findViewById(R.id.traceresult);
		TracerouteWorker tracetest = new TracerouteWorker();
		String result = tracetest.Trace();
		resultsview.setText(result);
	}
}