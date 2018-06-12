package org.Simple.S;

import org.Simple.API.HelloService;

public class HelloServiceImpl implements HelloService {

	public String sayHello(String name) {
		return "hello"+name;
	}

}
