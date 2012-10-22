package com.example.newtests;


import com.example.workers.LossTestWorker;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

public class LossTestActivity extends Activity {

	public TextView resultsview;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace);
		resultsview = (TextView) findViewById(R.id.traceresult);
		LossTestWorker losstest = new LossTestWorker();
		Thread loss = new Thread(losstest);
		loss.start();
		try{
			loss.join();
		}
		catch(InterruptedException e)
		{
			Log.d("Loss Activity", "Interrupted");
		}
		double losspercentage = 100.0 - losstest.loss;
		String result = "The loss percentage on your connection is " + losspercentage + "%\n";
		resultsview.setText(result);
		losstest = null;
	}
}