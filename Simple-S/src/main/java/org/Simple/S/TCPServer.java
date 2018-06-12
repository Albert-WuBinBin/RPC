package org.Simple.S;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException  
	{ 
		
			ServerSocket serverSocket =  new ServerSocket(9999);  
			System.out.println(serverSocket.getLocalPort());
			System.out.println("服务开启");
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println(socket.getInetAddress()+"....connected");  
				InputStream in = socket.getInputStream();
				byte[] buf = new byte[1024];  
				int len = in.read(buf);  
				System.out.println(new String(buf,0,len));  
				OutputStream out = socket.getOutputStream();  
				out.write("服务端已经收到".getBytes());  
				socket.close();  
			}
	}
}
