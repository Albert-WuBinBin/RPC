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
			
			Class<?>[] classes = proxy.getClass().getInterfaces();
			String className = classes[0].getName();
			
			netModel.setClassName(className);
			netModel.setArgs(args);
			netModel.setMethod(method.getName());
			String [] types = null; 
			if(args!=null) {
				types = new String [args.length];
				for (int i = 0; i < types.length; i++) {
					types[i] = args[i].getClass().getName();
				}
			}
			netModel.setTypes(types);
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
