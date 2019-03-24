package com.sample.soap.WS;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 
 * @author Evelyn
 *
 */
@WebService
public class CalculatorWS {
	
	@WebMethod
	public double doOperation(@WebParam(name = "num1") double a, @WebParam(name = "num2") double b, @WebParam(name = "num1") String op) {
		
		switch(op) {
		case "+":
			return a + b;
		case "-":
			return a - b;
		case "*":
			return a * b;
		case "/":
			return a / b;
		default:
			throw new IllegalArgumentException("Operation '" + op + "' not recognized. Inform '+', '-', '*', or '/'.");
		}
	}
	
	@WebMethod
	public String sayHello(String name) {
		return "Say Hello to " + name;
	}

}
