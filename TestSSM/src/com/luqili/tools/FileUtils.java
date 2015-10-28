package com.luqili.tools;

/**
 * <p>
 * 
 * </p>
 * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
 * @author L.Xu(@2015年5月13日)
 * @version 1.0
 * filename:FileUtils.java 
 */
public class FileUtils {

    /**
     * @param fileName
     * @return
     */
    public static String getFileExt(String filePath) {
        return filePath.substring(filePath.lastIndexOf("."));
    }

    /**
     * @param fileName
     * @return
     */
    public static String getFullName(String filePath) {
        return filePath.indexOf("/") != -1 ? filePath.substring(filePath.lastIndexOf("/") + 1) : filePath.substring(filePath.lastIndexOf("\\") + 1);
    }

    /**
     * @param filePath
     * @return
     */
    public static String getName(String filePath) {
        return filePath.indexOf("/") != -1 ? filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf(".")) : filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf("."));
    }
}
