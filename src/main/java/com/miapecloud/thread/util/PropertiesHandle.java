package com.miapecloud.thread.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源配置信息获取工具类
 * @author libing
 *
 */
public class PropertiesHandle {
	private static Logger logger = LoggerFactory.getLogger(PropertiesHandle.class);
	private static PropertiesHandle ph ;
	private String fileName;
	private Properties props;
	

	public static String getResuouceInfo(String key)
	{
		if(ph==null){
			ph =  new PropertiesHandle("ziyuan.properties");
		}
		try {
			return new String(ph.getProperty(key).getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	public static PropertiesHandle getPropertiesHandle(String name)
	{
		if("ziyuan.properties".equalsIgnoreCase(name)){
			return ph==null?ph=new PropertiesHandle(name):ph ;
		}else{
			return new PropertiesHandle(name);
		}
	}
	
	private PropertiesHandle(String fileName) {
		this.fileName = fileName;
		loadData();
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		 return props.getProperty(key);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setProperty(String key,String value){
		props.setProperty(key, value);
		
		
	}
	
	private void loadData() {
		try {
			props = new Properties();
			InputStream in = null;
			String currentDir = System.getProperty("user.dir");
			fileName = currentDir + File.separator + fileName;
			logger.info("fileName:"+fileName);
			if (null == in) {
				in = new FileInputStream(fileName);
			}
			props.load(in);
			in.close();
		} catch (Exception e) {
			logger.error("加载数据异常",e);
			props = null;
		}
	}

	
	public String getFileName(){
		return this.fileName;
	}
}
