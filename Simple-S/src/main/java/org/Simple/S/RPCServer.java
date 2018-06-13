package org.Simple.S;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.Simple.API.NetModel;
import org.Simple.API.SerializeUtils;

public class RPCServer {
	
	public static void main(String[] args) 
	{ 
		try {
			openServer();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	public static void openServer() throws IOException  {
		ServerSocket serverSocket =  new ServerSocket(9999); 
		try { 
			System.out.println("服务开启");
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println(socket.getInetAddress()+"-connected");  
				InputStream in = socket.getInputStream();
				byte[] buf = new byte[1024];  
				in.read(buf);  
				byte[] formatDate = formatData(buf);
				OutputStream out = socket.getOutputStream();  
				out.write(formatDate);  
				socket.close();  
			}
		} catch (Exception e) {
			e.printStackTrace();
			serverSocket.close();
		}
	}
	public static byte[] formatData(byte[] bs){
		try {
			Map<String, String> map = new HashMap<String,String>();
			map.put("org.Simple.API.HelloService", "org.Simple.S.HelloServiceImpl");
			
			NetModel netModel = (NetModel)SerializeUtils.byteToObject(bs);
			
			String className = netModel.getClassName();
			String[] types = netModel.getTypes();
			Object[] args = netModel.getArgs();

			Class<?> clazz = Class.forName(map.get(className));
			
			Class [] typeClazzs = null;
			
			if(types!=null) {
				typeClazzs = new Class[types.length];
				for (int i = 0; i < typeClazzs.length; i++) {
					typeClazzs[i] = Class.forName(types[i]);
				}
			}
			Method method = clazz.getMethod(netModel.getMethod(),typeClazzs);
			Object object = method.invoke(clazz.newInstance(), args);
			
			byte[] byteArray = SerializeUtils.ObjectToByte(object);
			return byteArray;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
