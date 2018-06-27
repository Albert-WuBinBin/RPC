package org.Simple.API;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
/**
 * Hessian序列化
 */
public class HessianSerialize {

	/**
	 * 序列化
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] Serialize(Object object) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Hessian2Output hOutput = new Hessian2Output(os);
		hOutput.writeObject(object);
		hOutput.flush();
		byte[] byteArray = os.toByteArray();
		hOutput.close();
		os.close();
		return byteArray;
	}
	/**
	 * 反序列化
	 * @param buf
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deSerialize(byte[] buf) throws IOException, ClassNotFoundException {
		ByteArrayInputStream is = new ByteArrayInputStream(buf);
		Hessian2Input hInput = new Hessian2Input(is);
		Object object =  hInput.readObject();
		hInput.close();
		is.close();
		return object;
	}
}
