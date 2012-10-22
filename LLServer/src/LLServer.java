import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

class LLServer
{
	static DatagramSocket server_socket;
	static byte[] receive_buffer;
	public static final String SERVER_ADDRESS = "localhost";
	public static final int PORTNUM = 8888;
	
	public static DatagramPacket getStartPacket(InetAddress client_addr, int port)
	{
		String request_accepted = "Request Accepted";
		DatagramPacket start_packet = new DatagramPacket(request_accepted.getBytes(), request_accepted.getBytes().length, client_addr, port);
		return start_packet;
	}
	
	public static String getRequestBuffer()
	{
		String client_request = "startrequest";
		return client_request;
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
		}
	}
}