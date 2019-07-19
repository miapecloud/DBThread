package com.miapecloud.thread.global;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DataQueue {
	private static Queue<Object> objectQueue=new ConcurrentLinkedQueue<>();//线程安全的，不会单独加锁
	
	/**
	 * 添加一个对象到任务队列
	 * @param object
	 * @return 返回添加状态
	 */
	public static boolean addObject(Object object) {
		return objectQueue.offer(object);
	}
	
	/**
	 * 取出一个对象 取队列首元素
	 * @return 队列为空返回null
	 */
	public static Object getObject() {
		return objectQueue.poll();
	}
}
