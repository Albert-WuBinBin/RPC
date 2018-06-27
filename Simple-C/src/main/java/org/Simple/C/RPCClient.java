package org.Simple.C;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.Simple.API.HelloService;
import org.Simple.API.SerializeUtils;


public class RPCClient {
	
	 public static Object send(byte[] bs)  {
		try {
			Socket socket = new Socket("127.0.0.1", 9999);
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(bs);  
			InputStream in = socket.getInputStream();  
			byte[] buf = new byte[1024];
			in.read(buf);
			Object formatDate = SerializeUtils.deSerialize(buf);
			socket.close();  
			return formatDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	 
	public static void main(String[] args) {
		HelloService helloService = ProxyFactory.getInstance(HelloService.class);
		System.out.println("say:"+helloService.sayHello("wbb"));
		System.out.println("Person:"+helloService.getPerson("wbb"));

	}
}