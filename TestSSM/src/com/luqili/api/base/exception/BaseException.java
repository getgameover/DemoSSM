package com.luqili.api.base.exception;

import com.luqili.tools.ErrorInfo;

/**
 * <p>
 * 统一异常基类
 * </p>
 * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
 * @author L.Xu(@2015年3月31日)
 * @version 1.0
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -6974546874994478414L;
    private ErrorInfo         errorInfo;

    /**
     * @return the errorInfo
     */
    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    /**
     * @param errorInfo the errorInfo to set
     */
    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public BaseException(String msg, Throwable cause) {
        super(msg, cause);
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setSuccess(Boolean.FALSE);
        errorInfo.setErrorinfo(msg);
        errorInfo.setObject(cause);
        this.errorInfo = errorInfo;
    }

    public BaseException() {
        super();
    }

    public BaseException(String msg) {
        super(msg);
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorinfo(msg);
        this.errorInfo = errorInfo;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

}
