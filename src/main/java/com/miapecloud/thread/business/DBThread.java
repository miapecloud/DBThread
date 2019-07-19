package com.miapecloud.thread.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.miapecloud.thread.global.DataQueue;

/**
 * 数据库的操作线程
 * @author libing
 *
 */
public class DBThread implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(DBThread.class);
	private static int MAX_OBJECT_NUM=10;//最大的数据大小
	List<Object> objectList=new ArrayList<>();//
	DbDao dbDao=new DbDao();//数据库操作
	
	
	@Override
	public void run() {
		while(true) {
			try {
				if(objectList.size()<MAX_OBJECT_NUM) {
					//当前入库的数据小于最大的要求
					Object object = DataQueue.getObject();
					if(object==null) {
						//执行保存操作
						dbDao.save(objectList);
						objectList.clear();
						Thread.sleep(5000);
					}else {
						//添加进列表
						objectList.add(object);
					}
				}else {
					//执行保存操作
					dbDao.save(objectList);
					objectList.clear();
					Thread.sleep(5000);
				}
			} catch (Exception e) {
				logger.error("出错",e);
			}
			
		}
	}

}
