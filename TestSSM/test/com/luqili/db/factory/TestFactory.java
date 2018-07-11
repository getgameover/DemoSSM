package com.luqili.db.factory;

import org.apache.catalina.filters.SetCharacterEncodingFilter;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class TestFactory {
	public static void main(String[]args){
		System.out.println(A.name);
		System.out.println(B.name);
		ComboPooledDataSource c = new ComboPooledDataSource();
	}
}
class A{
	static String name="A";
}
class B extends A{
	static String name="B";
}