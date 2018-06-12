package org.Simple.C;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.Simple.API.NetModel;

public class ProxyFactory {
	
	private static InvocationHandler handler = new InvocationHandler() {
		
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			NetModel netModel = new NetModel();
			
			String className = proxy.getClass().getName();
			
			String m = method.getClass().getName();
			
			String type = args[0].getClass().getName();
			
			netModel.setClassName(className);
			netModel.setArgs(args);
			netModel.setMethod(m);
			netModel.setType(type);
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ObjectOutputStream outputStream = new ObjectOutputStream(os);
			outputStream.writeObject(netModel);
			byte[] byteArray = os.toByteArray();
			TcpClient.send(byteArray);
			return null;
		}
	};

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> clazz) {
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), 
				new Class[]{clazz}, handler );
	}
}
