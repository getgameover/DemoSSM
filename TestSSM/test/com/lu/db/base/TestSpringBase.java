package com.lu.db.base;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:com/lu/config/applicationContext.xml")
public class TestSpringBase {
	@Test
	public final void loadIsSuccess(){
		System.out.println("LOAD SUCCESS");
	}
}
