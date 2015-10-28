package com.luqili.api.base.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/*********************************************************************************
//* Copyright (C) 2015 bsteel. All Rights Reserved.
//*
//* Filename:      BaseAction.java
//* Revision:      1.0
//* Author:        lxu
//* Created On:    2015年1月28日
//* Modified by:   
//* Modified On:   
//*
//* Description:   公用 action 基类
/********************************************************************************/

public class BaseAction {

    protected Logger logger = Logger.getLogger(this.getClass());

    /**
     * 页面输入框自动转化Dto字段对应类型
     * @param binder  HH:mm:ss
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
