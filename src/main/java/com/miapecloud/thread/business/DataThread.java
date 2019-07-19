package com.miapecloud.thread.business;

import com.miapecloud.thread.global.DataQueue;

/**
 * 模拟数据添加线程
 * @author libing
 *
 */
public class DataThread implements Runnable {

	@Override
	public void run() {
		while(true) {
			//每100ms添加当前时间戳
			DataQueue.addObject(System.currentTimeMillis());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
