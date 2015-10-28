package com.luqili.tools;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * <p>
 * 通用返回结果包装
 * </p>
 * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
 * @author Lei.XU (Create on:2014年11月15日)
 * @version 1.0
 * @fileName ErrorInfo.java
 */
public class ErrorInfo implements Serializable {

    private static final long serialVersionUID = -3640003041881125462L;
    private boolean           breturn;
    private boolean           success;
    private int               ireturn;
    private Object            object;
    private String            errorinfo;

    /**
     * @return the breturn
     */
    public boolean isBreturn() {
        return breturn;
    }

    /**
     * @param breturn the breturn to set
     */
    public void setBreturn(boolean breturn) {
        this.breturn = breturn;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the errorinfo
     */
    public String getErrorinfo() {
        return errorinfo;
    }

    /**
     * @param errorinfo the errorinfo to set
     */
    public void setErrorinfo(String errorinfo) {
        this.errorinfo = errorinfo;
    }

    /**
     * @return the ireturn
     */
    public int getIreturn() {
        return ireturn;
    }

    /**
     * @param ireturn the ireturn to set
     */
    public void setIreturn(int ireturn) {
        this.ireturn = ireturn;
    }

    /**
     * @return the object
     */
    public Object getObject() {
        return object;
    }

    /**
     * @param object the object to set
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> tranMap() {
        Map<String, Object> rval = Collections.emptyMap();
        if (this.object instanceof Map) {
            rval = (Map<String, Object>) object;
        }
        return rval;
    }

}
