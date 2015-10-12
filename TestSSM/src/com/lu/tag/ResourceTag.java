package com.lu.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lu.version.ResourceRoot;

public class ResourceTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	public String url;
	public String defualturl;
	public void setUrl(String url) {
		this.url = url;
	}
	public void setDefualturl(String defualturl) {
	    this.defualturl = defualturl;
	}

	@Override
	public int doStartTag()throws JspException{
		try {
		    	if(url==null||url.length()<1){
		    	    url=defualturl;
		    	}
			pageContext.getOut().write(getContextPath()+url+getResourceRoot());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
	private String getContextPath(){
		//return GgoConfig.ImageUrl+pageContext.getRequest().getServletContext().getContextPath();
		return pageContext.getRequest().getServletContext().getContextPath();
	}
	private String getResourceRoot(){
		ResourceRoot resourcePath=WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext()).getBean(ResourceRoot.class);
		return resourcePath.getResourceRoot();
	}
}
