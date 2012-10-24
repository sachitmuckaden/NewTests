package com.example.workers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.util.Log;

public class LossTestWorker implements Runnable
{
	private static final String SERVER_ADDRESS = "ruggles.gtnoise.net";
	private static final int PORT = 9916;
	private static final String REQUEST_ACCEPTED = "Request Accepted";
	private static final int NUMBER_OF_PACKETS = 100;
	private static final int PACKET_SIZE = 100; 
	private static final int THREAD_SLEEP_TIME = 100;
	public double loss; 
	private DatagramPacket getLossTestPacket()
	{
		byte[] losspacketbuffer = new byte[100];
		for(int i=0; i < 100; i++)
		{
			losspacketbuffer[i] = 123;
		}
		
		DatagramPacket losspacket = new DatagramPacket(losspacketbuffer, losspacketbuffer.length);
		return losspacket;
	}
	private
	DatagramPacket
	getRequestPacket() 
	{
		String request = "startrequest";
		byte[] start_request_array = request.getBytes();
		DatagramPacket request_packet = new DatagramPacket(start_request_array, start_request_array.length);
		return request_packet;
	}
	
	private
	DatagramPacket
	getEndPacket()
	{
		String endstring = "endingtest";
		byte[] endtestbuffer = endstring.getBytes();
		DatagramPacket end_packet = new DatagramPacket(endtestbuffer, endtestbuffer.length);
		return end_packet;
	}

	public void run()
	{
		DatagramSocket clientSocket = null;
		DatagramPacket mPacket = getLossTestPacket();
		byte[] receive_data = null;
		byte[] ack_buffer = new byte[1];
		boolean finished = false;
		DatagramPacket receive_ack = new DatagramPacket(ack_buffer, ack_buffer.length);
		try 
		{
			
			clientSocket = new DatagramSocket();
			clientSocket.connect(InetAddress.getByName(SERVER_ADDRESS), PORT);
			clientSocket.send(getRequestPacket());
			receive_data = new byte[PACKET_SIZE];
			DatagramPacket receive_packet = new DatagramPacket(receive_data, receive_data.length);
			clientSocket.receive(receive_packet);
			String received = new String(receive_packet.getData(),0,receive_packet.getLength());
			Log.d("Loss Test Worker", "received");
			if(received.equals(REQUEST_ACCEPTED))
			{
				Log.d("Loss Test worker", "Starting to send");
				for (int i = 0; i<NUMBER_OF_PACKETS; i++)
				{
					clientSocket.send(mPacket);
					clientSocket.receive(receive_ack);
				}	
				clientSocket.send(getEndPacket());
				finished = true;
				clientSocket.receive(receive_packet);
				int numberofpackets = Integer.parseInt(new String(receive_packet.getData(), 0 , receive_packet.getLength()));
				loss = (double) numberofpackets/ (double) NUMBER_OF_PACKETS*100.0;
			}
			received = null;
			
		}
		
		
		catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (UnknownHostException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} /*catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		finally
		{
			if(clientSocket!=null)
			{
				if(!finished)
				{	
					try {
						clientSocket.send(getEndPacket());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					clientSocket.close();
				}
			}
			receive_data = null;
			mPacket = null;
			receive_ack = null;
			ack_buffer = null;
		}
	}
}