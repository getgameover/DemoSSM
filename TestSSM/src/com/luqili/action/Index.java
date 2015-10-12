package com.luqili.action;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.luqili.db.beans.User;
import com.luqili.db.dao.UserDao;
import com.luqili.db.dao.impl.UserDaoImpl;
import com.luqili.service.UserService;

@Controller
public class Index {
	@Resource(name = "userService")
	private UserService userService;

	@RequestMapping(value = "/index")
	public ModelAndView OneIndex() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("p", "Hello Word!");
		mv.setViewName("/index/index");
		return mv;
	}
	
	@RequestMapping(value = "/bootstarp")
	public ModelAndView BootStarp() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/index/demo");
		return mv;
	}

	@RequestMapping(value = "/saveuser")
	public ModelAndView saveUser(String username, String password, Integer age, String sex) {
		User u = userService.saveUser(username, password, age, sex.charAt(0));
		String msg = null;
		if (u == null) {
			msg = "用户信息保存失败";
		} else {
			msg = "用户信息保存成功,user=" + u;
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", msg);
		mv.setViewName("/index/msg");
		return mv;
	}

	@RequestMapping(value = "/getuser")
	public ModelAndView getUserr(Integer id) {
		String msg = "";

		User user = userService.getUserById(id);
		if (user != null) {
			msg = user.toString();
		} else {
			msg = "未查询到用户信息";
		}

		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", "User=" + msg);
		mv.setViewName("/index/msg");
		return mv;
	}
}
