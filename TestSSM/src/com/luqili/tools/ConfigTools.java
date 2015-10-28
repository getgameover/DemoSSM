package com.luqili.tools;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * 读取 classpath下 conf/properties/xx.properties 配置文件属性
 * </p>
 * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
 * @author Yan'jiong.LU (Create on 2015年1月20)
 * @version 1.0
 * @FileName : ConfigTools.java
 */
public class ConfigTools {

    private static ConfigTools      instance      = new ConfigTools();
    private Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

    private ConfigTools() {

    }

    /**
     * 获取配置属性值
     * @param fileName 文件名称
     * @param field 属性key值
     * @return
     */
    public String getProperty(String fileName, String field) {
        String result = null;
        Properties properties = null;
        if (this.propertiesMap.get(fileName) != null) {
            properties = (Properties) this.propertiesMap.get(fileName);
        }
        else {
            properties = loadProperties(fileName);
        }
        result = properties.getProperty(field);
        return result;
    }

    /**
     * 获取配置文件所有属性
     * @param fileName 文件名称
     * @return
     */
    public Properties getProperty(String fileName) {
        Properties properties = null;
        if (this.propertiesMap.get(fileName) != null) {
            properties = (Properties) this.propertiesMap.get(fileName);
        }
        else {
            properties = loadProperties(fileName);
        }
        return properties;
    }

    /**
     * 获取配置文件所有属性
     * @param fileName 文件名称
     * @return
     */
    public Properties getClassPathProperty(String fileName) {
        Properties properties = null;
        if (this.propertiesMap.get(fileName) != null) {
            properties = (Properties) this.propertiesMap.get(fileName);
        }
        else {
            properties = loadClassPathProperties(fileName);
        }
        return properties;
    }

    /**
     * 加载配置文件
     * @param filename 文件名
     * @return
     */
    private Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        String filePath = File.separator + "config" + File.separator + "properties" + File.separator + fileName + ".properties";
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath));
            if (!properties.isEmpty()) {
                this.propertiesMap.put(fileName, properties);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 获取配置属性值
     * @param fileName 文件名称
     * @param field 属性key值
     * @return
     */
    public String getClassPathProperty(String fileName, String field) {
        String result = null;
        Properties properties = null;
        if (this.propertiesMap.get(fileName) != null) {
            properties = (Properties) this.propertiesMap.get(fileName);
        }
        else {
            properties = loadClassPathProperties(fileName);
        }
        result = properties.getProperty(field);
        return result;
    }

    /**
     * 加载配置文件
     * @param filename
     * @return
     */
    private Properties loadClassPathProperties(String fileName) {
        Properties properties = new Properties();
        String filePath = File.separator + fileName + ".properties";
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath));
            if (!properties.isEmpty()) {
                this.propertiesMap.put(fileName, properties);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * @return
     */
    public static ConfigTools getInstance() {
        return instance;
    }

}