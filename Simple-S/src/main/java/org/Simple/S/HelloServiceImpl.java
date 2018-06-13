package org.Simple.S;

import org.Simple.API.HelloService;
import org.Simple.API.Person;

public class HelloServiceImpl implements HelloService {

	public String sayHello(String name) {
		return "hello,"+name;
	}

	public Person getPerson(String name) {
		Person person = new Person();
		person.setName(name);
		person.setAge(20);
		return person;
	}

}
