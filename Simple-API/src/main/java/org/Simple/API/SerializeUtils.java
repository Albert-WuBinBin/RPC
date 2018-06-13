package org.Simple.API;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtils {

	/**
	 * 序列化
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] ObjectToByte(Object object) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream outputStream = new ObjectOutputStream(os);
		outputStream.writeObject(object);
		byte[] byteArray = os.toByteArray();
		return byteArray;
	}
	/**
	 * 反序列化
	 * @param buf
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object byteToObject(byte[] buf) throws IOException, ClassNotFoundException {
		ByteArrayInputStream is = new ByteArrayInputStream(buf);
		ObjectInputStream inputStream = new ObjectInputStream(is);
		Object object =  inputStream.readObject();
		return object;
	}
}

