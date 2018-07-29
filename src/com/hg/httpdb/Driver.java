package com.hg.httpdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * HttpDB JDBC驱动
 * @author wanghg
 */
public class Driver implements java.sql.Driver {
    public Connection connect(String url, Properties info) throws SQLException {
        if (this.acceptsURL(url)) {
            return new HttpConnection(url, info.getProperty("user", ""), info.getProperty("password", ""));
        } else {
            return null;
        }
    }

    public boolean acceptsURL(String url) throws SQLException {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return true;
        } else {
            return false;
        }
    }
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
            throws SQLException {
        return new DriverPropertyInfo[] {};
    }
    public int getMajorVersion() {
        return 0;
    }
    public int getMinorVersion() {
        return 1;
    }
    public boolean jdbcCompliant() {
        return false;
    }
    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
}
