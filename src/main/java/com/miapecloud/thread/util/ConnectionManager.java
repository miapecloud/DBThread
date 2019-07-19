package com.miapecloud.thread.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.miapecloud.thread.global.GlobalData;
/**
 * JDBC连接池工具类
 * @author libing
 *
 */
public class ConnectionManager {
	private static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	static int connNum = 0;

	// 通过标识名来创建相应连接池
//	private static DataSource dataSource = new ComboPooledDataSource("mysql");
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	static {
		try {
			dataSource.setDriverClass(GlobalData.mysqlDbDriverClass);
			dataSource.setJdbcUrl(GlobalData.mysqlDbJdbcUrl);
			dataSource.setUser(GlobalData.mysqlDbUser);
			dataSource.setPassword(GlobalData.mysqlDbPassword);
			dataSource.setInitialPoolSize(GlobalData.mysqlDbInitialPoolSize);
			dataSource.setMinPoolSize(GlobalData.mysqlDbMinPoolSize);
			dataSource.setMaxPoolSize(GlobalData.mysqlDbMaxPoolSize);
			dataSource.setMaxIdleTime(GlobalData.mysqlDbMaxIdleTime);
			dataSource.setMaxStatements(GlobalData.mysqlDbMaxStatements);
		} catch (PropertyVetoException e) {
			logger.error("加载mysql数据库信息出错");
		}
	}
	
	
	// 获取连接对象
	public static Connection getCurrentConnection() throws SQLException {
		// 从ThreadLocal寻找当前线程是否有对应Connection
		Connection conn = threadLocal.get();
		if (conn == null) {
			// 获得新的connection
			conn = dataSource.getConnection();
			// 将conn资源绑定到ThreadLocal（map）上
			threadLocal.set(conn);

			connNum++;
			logger.debug("++操作，当前连接数：" + connNum);
		}
		return conn;
	}

	// 开启事务
	public static void startTransaction() throws SQLException {
		Connection conn = getCurrentConnection();
		conn.setAutoCommit(false);
	}

	// 回滚事务
	public static void rollback() throws SQLException {
		getCurrentConnection().rollback();
	}

	// 提交事务
	public static void commit() throws SQLException {
		Connection conn = getCurrentConnection();
		conn.commit();
		// 将Connection从ThreadLocal中移除
		threadLocal.remove();
		conn.close();

		connNum--;
		logger.debug("[事务提交]--操作，当前连接数：" + connNum);
	}

	// 关闭资源方法
	public static void releaseSource(PreparedStatement pstm,ResultSet rs) throws SQLException {
		closeResultSet(rs);
		closeStatement(pstm);
		closeConnection();
	}
	
	public static void closeConnection() throws SQLException {
		Connection conn = getCurrentConnection();
		if (conn != null) {
			// 将Connection从ThreadLocal中移除
			threadLocal.remove();
			conn.close();
			
			connNum--;
			logger.debug("--操作，当前连接数：" + connNum);
		}
	}
	public static void closeStatement(PreparedStatement pstm) throws SQLException {
		if (pstm != null) {
			pstm.close();
		}
	}
	public static void closeResultSet(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
	}
}
