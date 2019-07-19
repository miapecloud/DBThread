package com.miapecloud.thread.business;


public class MainClass {
	
	public static void main(String[] args) {
		new Thread(new DataThread()).start();//模拟其它线程正在写数据
		new Thread(new DBThread()).start();//开启数据入库线程
	}

}
