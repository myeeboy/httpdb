package com.hg.httpdb;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 简单数据源实现
 * @author wanghg
 */
public class DataSource implements javax.sql.DataSource {
	private String driver;
	private String jdbcUrl;
	private String user;
	private String password;
	public Connection getConnection() throws SQLException {
		return this.getConnection(this.user, this.password);
	}
	public Connection getConnection(String username, String password)
			throws SQLException {
		try {
			Class.forName(this.driver).newInstance();
			return DriverManager.getConnection(this.jdbcUrl, username, password);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
	public void setDriverClass(String driver) {
		this.driver = driver;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}
	public void setLogWriter(PrintWriter out) throws SQLException {}

	public void setLoginTimeout(int seconds) throws SQLException {}

	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	public Object unwrap(Class arg0) throws SQLException {
		return null;
	}

	public boolean isWrapperFor(Class arg0) throws SQLException {
		return false;
	}
}
