package com.luqili.db.factory;

public class TestFactory {
	public static void main(String[]args){
		System.out.println(A.name);
		System.out.println(B.name);
	}
}
class A{
	static String name="A";
}
class B extends A{
	static String name="B";
}