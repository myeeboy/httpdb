package com.hg.httpdb;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 连接
 * @author wanghg
 */
public class HttpConnection implements Connection {
	private String url;
	private String user;
	private String password;
	
	public String getUrl() {
		return url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public HttpConnection(String url, String user, String password) throws SQLException {
		this.url = url;
		this.user = user;
		this.password = password;
		if (!"ok".equals(((HttpResultSet) new HttpStatement(this).executeQuery("$hi$")).getRowSet().value(0, 0))) {
			throw new SQLException("服务无效或用户名口令错误");
		}
	}
    public Statement createStatement() throws SQLException {
        return new HttpStatement(this);
    }
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return new HttpPreparedStatement(this);
    }
    public CallableStatement prepareCall(String sql) throws SQLException {
        throw new SQLException(HttpConnection.NOT_IMPLEMENT);
    }
    public String nativeSQL(String sql) throws SQLException {
        return sql;
    }
    public void setAutoCommit(boolean autoCommit) throws SQLException {}
    public boolean getAutoCommit() throws SQLException {
    	return true;
    }
    public void commit() throws SQLException {}
    public void rollback() throws SQLException {}
    protected boolean closeed = false;
    public void close() throws SQLException {
    	closeed = true;
    }
    public boolean isClosed() throws SQLException {
        return this.closeed;
    }
    private DatabaseMetaData metaData = null;
    public DatabaseMetaData getMetaData() throws SQLException {
    	if (metaData == null) {
    		metaData = new HttpDBMetaData(this);
    	}
        return metaData;
    }
    public void setReadOnly(boolean readOnly) throws SQLException {
        throw new SQLException(HttpConnection.NOT_IMPLEMENT);
    }
    public boolean isReadOnly() throws SQLException {
        return true;
    }
    public void setCatalog(String catalog) throws SQLException {
        throw new SQLException(HttpConnection.NOT_IMPLEMENT);
    }
    public String getCatalog() throws SQLException {
        return "";
    }
    public void setTransactionIsolation(int level) throws SQLException {
    }
    public int getTransactionIsolation() throws SQLException {
        return 0;
    }
    public SQLWarning getWarnings() throws SQLException {
        throw new SQLException(HttpConnection.NOT_IMPLEMENT);
    }
    public void clearWarnings() throws SQLException {
    }
    public Statement createStatement(int resultSetType, int resultSetConcurrency)
            throws SQLException {
        return new HttpStatement(this);
    }
    public PreparedStatement prepareStatement(String sql, int resultSetType,
            int resultSetConcurrency) throws SQLException {
        return new HttpPreparedStatement(this);
    }
    public CallableStatement prepareCall(String sql, int resultSetType,
            int resultSetConcurrency) throws SQLException {
        throw new SQLException(HttpConnection.NOT_IMPLEMENT);
    }
    private Map typeMap = new HashMap();
	public static final String NOT_IMPLEMENT = "Not implement!";
    public Map getTypeMap() throws SQLException {
        return typeMap;
    }
    public void setTypeMap(Map map) throws SQLException {
        this.typeMap = map;
    }
    public void setHoldability(int holdability) throws SQLException {
    }
    public int getHoldability() throws SQLException {
        return 0;
    }
    public Savepoint setSavepoint() throws SQLException {
        throw new SQLException(HttpConnection.NOT_IMPLEMENT);
    }
    public Savepoint setSavepoint(String name) throws SQLException {
        throw new SQLException(HttpConnection.NOT_IMPLEMENT);
    }
    public void rollback(Savepoint savepoint) throws SQLException {
    }
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    }
    public Statement createStatement(int resultSetType,
            int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        return new HttpStatement(this);
    }
    public PreparedStatement prepareStatement(String sql, int resultSetType,
            int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        return new HttpPreparedStatement(this);
    }
    public CallableStatement prepareCall(String sql, int resultSetType,
            int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        throw new SQLException(HttpConnection.NOT_IMPLEMENT);
    }
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
            throws SQLException {
        return new HttpPreparedStatement(this);
    }
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
            throws SQLException {
        return new HttpPreparedStatement(this);
    }
    public PreparedStatement prepareStatement(String sql, String[] columnNames)
            throws SQLException {
        return new HttpPreparedStatement(this);
    }
    protected int executeUpdate(String sql) throws SQLException {
        return 0;
    }
    public ResultSet query(String sql) throws SQLException {
        return new HttpStatement(this).executeQuery(sql);
    }
    public Clob createClob() throws SQLException {
        return null;
    }
    public Blob createBlob() throws SQLException {
        return null;
    }
    public NClob createNClob() throws SQLException {
        return null;
    }
    public SQLXML createSQLXML() throws SQLException {
        return null;
    }
    public boolean isValid(int timeout) throws SQLException {
        return false;
    }
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        
    }
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        
    }
    public String getClientInfo(String name) throws SQLException {
        return "";
    }
    public Properties getClientInfo() throws SQLException {
        return new Properties();
    }
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return null;
    }
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return null;
    }
    public Object unwrap(Class arg0) throws SQLException {
        return null;
    }
    public boolean isWrapperFor(Class arg0) throws SQLException {
        return false;
    }
}
