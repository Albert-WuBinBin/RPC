package org.Simple.C;

import org.Simple.API.HelloService;

public class Test123 {
	public static void main(String[] args) {
		HelloService helloService = ProxyFactory.getInstance(HelloService.class);
		String sayHello = helloService.sayHello("abc");
		System.out.println(sayHello);
	}
}
