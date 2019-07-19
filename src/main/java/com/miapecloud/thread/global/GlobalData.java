package com.miapecloud.thread.global;

import com.miapecloud.thread.util.PropertiesHandle;

/**
 * 公共变量接口
 * @author libing
 *
 */
public interface GlobalData {
	String mysqlDbDriverClass = PropertiesHandle.getResuouceInfo("mysql.db.driverClass");
	String mysqlDbJdbcUrl = PropertiesHandle.getResuouceInfo("mysql.db.jdbcUrl");
	String mysqlDbUser = PropertiesHandle.getResuouceInfo("mysql.db.user");
	String mysqlDbPassword = PropertiesHandle.getResuouceInfo("mysql.db.password");
	Integer mysqlDbInitialPoolSize = Integer.parseInt(PropertiesHandle.getResuouceInfo("mysql.db.initialPoolSize"));
	Integer mysqlDbMinPoolSize = Integer.parseInt(PropertiesHandle.getResuouceInfo("mysql.db.minPoolSize"));
	Integer mysqlDbMaxPoolSize = Integer.parseInt(PropertiesHandle.getResuouceInfo("mysql.db.maxPoolSize"));
	Integer mysqlDbMaxIdleTime = Integer.parseInt(PropertiesHandle.getResuouceInfo("mysql.db.maxIdleTime"));
	Integer mysqlDbMaxStatements = Integer.parseInt(PropertiesHandle.getResuouceInfo("mysql.db.maxStatements"));
}
