package org.Simple.S;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.Simple.API.NetModel;

public class TCPServer {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException  
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
				try {
					byte[] formatDate = formatDate(buf);
					OutputStream out = socket.getOutputStream();  
					out.write(formatDate);  
					socket.close();  
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
	}
	public static byte[] formatDate(byte[] bs) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		Map<String, String> map = new HashMap<>();
		map.put("org.Simple.API.HelloService", "org.Simple.S.HelloServiceImpl");
		ByteArrayInputStream ai = new ByteArrayInputStream(bs);
		ObjectInputStream is = new ObjectInputStream(ai);
		NetModel netModel = (NetModel) is.readObject();
		String className = netModel.getClassName();
		String[] types = netModel.getTypes();
		Object[] args = netModel.getArgs();
		System.out.println(map.get(className));
		Class<?> clazz = Class.forName(map.get(className));
		Class [] typeClazzs = null;
		if(types!=null) {
			typeClazzs = new Class[types.length];
			for (int i = 0; i < typeClazzs.length; i++) {
				typeClazzs[i] = Class.forName(types[i]);
			}
		}
		Method method = clazz.getMethod(netModel.getMethod(),typeClazzs);
		String invoke = (String) method.invoke(clazz.newInstance(), args);
		System.out.println(invoke);
		return invoke.getBytes();
	}
}
