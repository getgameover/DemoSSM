package com.luqili.tools;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

/**
 * <p>
 * 
 * </p>
 * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
 * @author L.Xu(@2015年5月13日)
 * @version 1.0
 * filename:FtpClientUtils.java 
 */
public class FtpClientUtils {

    private static final Logger LOG = Logger.getLogger(FtpClientUtils.class);

    /**
     * @Account:获取FTP链接
     * @Author: jiangbin
     * @Time：2014-7-22 下午05:20:43
     * @Params：
     */
    public static FTPClient connectServce(String ftpHost, String ftpLoginName, String ftpLoginPwd) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost);
            boolean flag = ftpClient.login(ftpLoginName, ftpLoginPwd);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            LOG.info("====打开ftp链接===date:" + new Date() + "|flag:" + flag);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    /**
     * @Account:从应用程序（页面），直接上传至指定ftp路径
     * @Author: jiangbin
     * @Time：2014-7-22 下午05:22:06
     * @Params：
     */
    public static boolean uploadFtpInputStream(FTPClient ftpClient, String filePath, String fileName, InputStream inputStream) {
        boolean result = false;
        if (ftpClient != null) {
            filePath = filePath.replace("\\", "/");
            try {
                ftpClient.setDefaultTimeout(1000 * 60);
                makeDirectory(ftpClient, filePath);
                //设置上传目录
                ftpClient.makeDirectory(filePath);
                // if (flag) {
                ftpClient.changeWorkingDirectory(filePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                //设置文件类型（二进制） 
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.storeFile(fileName, inputStream);
                result = true;
                //  }
            }
            catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("FTP客户端出错！", e);
            }
            finally {
                IOUtils.closeQuietly(inputStream);
                try {
                    if (ftpClient != null) {
                        ftpClient.disconnect();
                        LOG.info("====关闭ftp链接===date:" + new Date());
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("关闭FTP连接发生异常！", e);
                }
                result = true;
            }
        }
        return result;
    }

    /**
     * @param ftpClient
     * @param path
     * @return
     */
    public static boolean makeDirectory(FTPClient ftpClient, String path) {
        if ("".equals(path))
            return false;
        boolean flag = true;
        try {
            String s[] = path.split("/");
            for (int i = 0 ; i < s.length ; i++) {
                if (!ftpClient.changeWorkingDirectory(s[i])) {
                    flag = ftpClient.makeDirectory(s[i]);
                    ftpClient.changeWorkingDirectory(s[i]);
                }
                else {
                    ftpClient.changeWorkingDirectory(s[i]);
                }
            }
            if (flag) {
                LOG.info("make Directory " + path + " succeed");
            }
            else {
                LOG.info("make Directory " + path + " false");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 
     * @param filePath
     * @param fileName
     * @return
     * @throws Exception
     */
    public static InputStream downloadFile(FTPClient ftpClient,String filePath, String fileName) throws Exception {
        ftpClient.changeWorkingDirectory(filePath);
        InputStream is = ftpClient.retrieveFileStream(filePath + fileName);
        if (ftpClient != null) {
            ftpClient.disconnect();
            LOG.info("====关闭ftp链接===date:" + new Date());
        }
        return is;
    }
    /**
     * @param busiFlag 业务标识
     * @param busiId 业务ID
     * @return
     */
    public static String genFileName(String busiFlag, String busiId) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat(DATA_PATTERN);
        String dateStr = format.format(date);
        Random random = new Random();
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0 ; i < 5 ; i++) {
            strBuf.append(CODE_STR.charAt(random.nextInt(CODE_STR.length())));
        }
        return busiFlag + "-" + busiId + "-" + dateStr + "-" + strBuf;
    }

    static final String CODE_STR     = "0123456789";
    static final String DATA_PATTERN = "yyyyMMddhhmmss";
}
