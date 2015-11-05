package com.luqili.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.cglib.proxy.MethodProxy;
/**
 * 测试方法拦截类，测试使用
 * @author luqili
 * @date 2015年11月4日
 */
public class TestMethod implements MethodInterceptor{
	private Logger log = Logger.getLogger(this.getClass());
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		invocation.getThis().getClass();
		String methodName=invocation.getMethod().getName();
		String className=invocation.getThis().getClass().getName();
		log.debug("当前拦截的方法为："+className+methodName);
		return null;
	}

	

}
