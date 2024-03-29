package com.example.workers;

import android.util.Log;

import com.example.utils.CommandLineUtil;

public class TracerouteWorker
{
	public CommandLineUtil cmdUtil;
	public static final String SERVER_ADDRESS = "cc.gatech.edu";
	public String Trace()
	{
		String result = null;
		String ipDst 	= SERVER_ADDRESS;
		String cmd 		= "ping";
		String options 	= "-c "+1 + " -t ";
		String output 	= "";
		cmdUtil = new CommandLineUtil();
		String temp;
		for(int i = 2; i<20;i++)
		{
			temp = options+i;
			Log.d("TraceWorker", ""+temp);
			output = cmdUtil.runCommand(cmd, ipDst, temp);
			result += (output + "\n");
		}
		result = parseResult(result);
		return result;
	}
	
	String parseResult(String result)
	{
		boolean found = false;
		String parsedResult= "";
		int pos;
		int count = 0;
		for(pos=0; pos<result.length(); pos++)
		{
			if(result.charAt(pos)=='F')
			{
				found = true;
			}
			if(result.charAt(pos)=='\n')
			{
				count++;
			}
			if(found==true){
				pos+=5;
				Log.d("TraceWorker", "" + count);
				if(count>7)
				{
					parsedResult+="Hop details missing\n\n";
				}
				count=0;
				while(result.charAt(pos)!=' ')
				{
					parsedResult += result.charAt(pos);
					pos++;
				}
				parsedResult+="\n\n";
				found = false;
			}
		}
		return parsedResult;
	}
	
}