package com.miapecloud.thread.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.miapecloud.thread.util.ConnectionManager;

public class DbDao {
	
	private static Logger logger = LoggerFactory.getLogger(DbDao.class);


	public void save(List<Object> objectList) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into test(name) values(?)";
		try {
			conn = ConnectionManager.getCurrentConnection();
			conn.setAutoCommit(false);
			pstm=conn.prepareStatement(sql);
			for (Object object : objectList) {
				pstm.setObject(1, object);
				pstm.addBatch();
			}
			pstm.executeBatch();
			conn.commit();
			pstm.clearBatch();
		} catch (SQLException e) {
			logger.error("插入数据失败！", e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("回滚失败！",e1);
			}
		} finally {
			try {
				conn.setAutoCommit(true);
				ConnectionManager.releaseSource(pstm, null);
			} catch (SQLException e) {
				logger.error("关闭数据库资源出错！", e);
			}
		}
		
	}

}
