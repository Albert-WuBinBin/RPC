package org.Simple.API;

import java.io.IOException;
/**
 * 几个序列胡比较
 */
public class SerializeTest {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Person person = new Person();
		person.setAge(20);
		person.setName("zhangsan");
		byte[] byte1 = SerializeUtils.serialize(person);
		System.out.println("JDK:"+byte1.length);
		System.out.println(SerializeUtils.deSerialize(byte1));
		byte[] byte2 = HessianSerialize.Serialize(person);
		System.out.println("Hessian:"+byte2.length);
		System.out.println(HessianSerialize.deSerialize(byte2));
		byte[] byte3 = ProtobufSerialize.serialize(person, Person.class);
		System.out.println("Protobuf:"+byte3.length);
		System.out.println(ProtobufSerialize.deSerialize(byte3, Person.class));
	}
}
