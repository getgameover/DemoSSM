package com.luqili.action.tool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 工具资源管理类
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/tool")
public class Tools {

	@RequestMapping(value = "/rs-{type}")
	public ModelAndView Resource(@PathVariable(value="type")String type) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("type", type);
		mv.setViewName("/tool/loadfile");
		return mv;
	}
}
