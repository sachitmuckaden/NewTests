import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
//import java.net.InetSocketAddress;
import java.net.SocketException;
//import java.net.UnknownHostException;

class LostTestServer
{
	static DatagramSocket server_socket;
	static byte[] receive_buffer;
	public static final String SERVER_ADDRESS = "localhost";
	public static final int PORTNUM = 9916;
	private static final String END_STRING = "endingtest";
	
	public static DatagramPacket getStartPacket(InetAddress client_addr, int port)
	{
		String request_accepted = "Request Accepted";
		return new DatagramPacket(request_accepted.getBytes(), request_accepted.getBytes().length, client_addr, port);
	}
	public static DatagramPacket getDataPacket(InetAddress client_addr, int port, int count)
	{
		String result = "" +count;
		return new DatagramPacket(result.getBytes(), result.getBytes().length, client_addr, port);
		
	}
	public static String getRequestBuffer()
	{
		String client_request = "startrequest";
		return client_request;
	}
	public static DatagramPacket getAckPacket(InetAddress client_addr, int port)
	{
		byte[] ack_buffer = new byte[1];
		ack_buffer[0] = 1;
		return new DatagramPacket(ack_buffer, ack_buffer.length, client_addr, port);
	}
	public static void main(String[] args)
	{
		receive_buffer = new byte[1024];
		server_socket= null;
		
		DatagramPacket receive_packet = new DatagramPacket(receive_buffer, receive_buffer.length);
		try {
			
			server_socket = new DatagramSocket(PORTNUM);
			
			//server_socket.bind(new InetSocketAddress(SERVER_ADDRESS, PORTNUM));
			while(true)
			{
				
				try {
					
					server_socket.receive(receive_packet);
					String received = new String(receive_packet.getData(), 0 , receive_packet.getLength());
					System.out.println("Received connection");
					//System.out.println(received);
					//System.out.println(getRequestBuffer());
					if(received.equals(getRequestBuffer()))
					{
						
						server_socket.send(getStartPacket(receive_packet.getAddress(), receive_packet.getPort()));
						int count = 0;
						server_socket.receive(receive_packet);
						received = null;
						received = new String(receive_packet.getData(), 0 , receive_packet.getLength());
						while(!received.equals(END_STRING))
						{
							count++;
							server_socket.send(getAckPacket(receive_packet.getAddress(), receive_packet.getPort()));
							System.out.println("Waiting to receive");
							server_socket.receive(receive_packet);
							System.out.println("Sending ACK");
							received = null;
							received = new String(receive_packet.getData(), 0 , receive_packet.getLength());
						}
						System.out.println("Sending count "+count);
						server_socket.send(getDataPacket(receive_packet.getAddress(), receive_packet.getPort(), count));
						received = null;
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					continue;
				}
				
			}
		}
		catch (SocketException e) {
			System.out.println("Could not create socket. Trace:\n");
			e.printStackTrace();
		} 
		/*catch (UnknownHostException e1) {
		// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		finally
		{
			if(server_socket!=null)
				server_socket.close();
			System.out.println("Closed Server Socket");
			receive_buffer = null;
			receive_packet = null;
		}
	}
}