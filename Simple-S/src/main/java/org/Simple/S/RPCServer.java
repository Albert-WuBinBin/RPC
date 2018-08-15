package org.Simple.S;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

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
			 //将收到的byte数组反序列化为NetModel类型,然后通过反射调用HelloServiceImpl实现类的方法
            NetModel netModel = (NetModel)SerializeUtils.deSerialize(bs);
            String className = netModel.getClassName();
            String[] types = netModel.getTypes();
            Object[] args = netModel.getArgs();

            /*这里简单通过Map来做接口映射到实现类,从map中取
            Map<String, String> map = new HashMap<String,String>();
            map.put("org.Simple.API.HelloService", "org.Simple.S.HelloServiceImpl");
            Class<?> clazz = Class.forName(map.get(className)); 
            */ 
            //也可以把这个键值放到配置文件下，通过配置文件读取
            Class<?> clazz = Class.forName(getPropertyValue(className));
            Class<?> [] typeClazzs = null;
            if(types!=null) {
                typeClazzs = new Class[types.length];
                for (int i = 0; i < typeClazzs.length; i++) {
                    typeClazzs[i] = Class.forName(types[i]);
                }
            }
            Method method = clazz.getMethod(netModel.getMethod(),typeClazzs);
            Object object = method.invoke(clazz.newInstance(), args);
            byte[] byteArray = SerializeUtils.serialize(object);
            return byteArray;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//通过key去properties文件中取值
	public static String getPropertyValue(String key) throws IOException {
		Properties properties = new Properties();
		FileInputStream in = new FileInputStream("src/main/resources/config.properties");
		properties.load(in);
		in.close();
		return properties.getProperty(key);
	}
}
