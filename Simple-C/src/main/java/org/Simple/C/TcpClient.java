package org.Simple.C;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class TcpClient {
	
	 public static void send(byte[] bs) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 9999);
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(bs);  
		InputStream in = socket.getInputStream();  
		byte[] buf = new byte[1024];  
		int len = in.read(buf);
		System.out.println(new String(buf,0,len));  
		socket.close();  
	}
}