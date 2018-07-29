package com.hg.httpdb;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

/**
 * HttpStatement
 * @author wanghg
 */
public class HttpStatement implements Statement {
    private HttpConnection conn;
	public HttpStatement(HttpConnection conn) {
    	this.conn = conn;
	}
    public ResultSet executeQuery(String sql) throws SQLException {
		try {
			Object res = invoke(sql);
			if (res instanceof RowSet) {
				return new HttpResultSet((RowSet) res);
			} else {
				return new HttpResultSet(new RowSet());
			}
		} catch (Exception e) {
			throw new SQLException(e);
		}
    }
    private Object invoke(String sql) throws Exception {
		HttpURLConnection httpConn = (HttpURLConnection) new URL(this.conn.getUrl()).openConnection();
		httpConn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
		httpConn.setDoOutput(true);
		OutputStream out = httpConn.getOutputStream();
		out.write("&db=".getBytes());
		out.write(URLEncoder.encode(this.conn.getUser(), "UTF-8").getBytes());
		out.write("&sql=".getBytes());
		out.write(URLEncoder.encode(Crypt.encrypt(sql, this.conn.getPassword()), "UTF-8").getBytes());
		if (this.maxRows != Integer.MAX_VALUE) {
			out.write("&maxrows=".getBytes());
			out.write(String.valueOf(this.maxRows).getBytes());
		}
		out.flush();
		return DataIO.read(httpConn.getInputStream(), this.conn.getPassword());
    }
	public int executeUpdate(String sql) throws SQLException {
		try {
			return To.toInt(invoke(sql));
		} catch (Exception e) {
			throw new SQLException(e);
		}
    }
    public void close() throws SQLException {
        if (this.resultSet != null) {
            this.resultSet.close();
        }
    }
    private int maxFieldSize = Integer.MAX_VALUE;
    public int getMaxFieldSize() throws SQLException {
        return this.maxFieldSize;
    }
    public void setMaxFieldSize(int max) throws SQLException {
        this.maxFieldSize = max;
    }
    private int maxRows = Integer.MAX_VALUE;
    public int getMaxRows() throws SQLException {
        return this.maxRows;
    }
    public void setMaxRows(int max) throws SQLException {
        this.maxRows = max;
    }
    public void setEscapeProcessing(boolean enable) throws SQLException {
    }
    private int quertTimeout = Integer.MAX_VALUE;
    public int getQueryTimeout() throws SQLException {
        return quertTimeout;
    }
    public void setQueryTimeout(int seconds) throws SQLException {
        this.quertTimeout = seconds;
    }
    public void cancel() throws SQLException {
    }
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }
    public void clearWarnings() throws SQLException {
    }
    public void setCursorName(String name) throws SQLException {
    }
    protected ResultSet resultSet;
    public boolean execute(String sql) throws SQLException {
    	this.resultSet = this.executeQuery(sql);
        return true;
    }
    public ResultSet getResultSet() throws SQLException {
        return this.resultSet;
    }
    protected int updateCount = 0;
    public int getUpdateCount() throws SQLException {
        return updateCount;
    }
    protected int curResult = -1;
    public boolean getMoreResults() throws SQLException {
        if (curResult > 1) {
            return false;
        } else {
            curResult++;
            return true;
        }
    }
    private int fetchDirection = ResultSet.FETCH_UNKNOWN;
    public void setFetchDirection(int direction) throws SQLException {
        this.fetchDirection = direction;
    }
    public int getFetchDirection() throws SQLException {
        return this.fetchDirection;
    }
    private int fetchSize = 100;
    public void setFetchSize(int rows) throws SQLException {
        this.fetchSize = rows;
    }
    public int getFetchSize() throws SQLException {
        return this.fetchSize;
    }
    public int getResultSetConcurrency() throws SQLException {
        return ResultSet.CONCUR_READ_ONLY;
    }
    public int getResultSetType() throws SQLException {
        if (this.resultSet != null) {
            return this.resultSet.getType();
        } else {
            throw new SQLException("数据集为空");
        }
    }
    StringBuffer batchSql = new StringBuffer();
    public void addBatch(String sql) throws SQLException {
        batchSql.append(sql).append(";");
    }
    public void clearBatch() throws SQLException {
        batchSql.setLength(0);
    }
    public int[] executeBatch() throws SQLException {
        if (this.execute(this.batchSql.toString())) {
            return new int[] {1};
        } else {
            return new int[] {0};
        }
    }
    public Connection getConnection() throws SQLException {
        return this.conn;
    }
    public boolean getMoreResults(int current) throws SQLException {
        //只支持单记录集合
        return false;
    }
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }
    public int executeUpdate(String sql, int autoGeneratedKeys)
            throws SQLException {
        //忽略key
        return this.executeUpdate(sql);
    }
    public int executeUpdate(String sql, int[] columnIndexes)
            throws SQLException {
        //忽略key
        return this.executeUpdate(sql);
    }
    public int executeUpdate(String sql, String[] columnNames)
            throws SQLException {
        return this.executeUpdate(sql);
    }
    public boolean execute(String sql, int autoGeneratedKeys)
            throws SQLException {
        return this.execute(sql);
    }
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return this.execute(sql);
    }
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return this.execute(sql);
    }
    public int getResultSetHoldability() throws SQLException {
        return ResultSet.CLOSE_CURSORS_AT_COMMIT;
    }
    public boolean isClosed() throws SQLException {
        return false;
    }
    public void setPoolable(boolean poolable) throws SQLException {
    }
    public boolean isPoolable() throws SQLException {
        return false;
    }
    public Object unwrap(Class arg0) throws SQLException {
        return null;
    }
    public boolean isWrapperFor(Class arg0) throws SQLException {
        return false;
    }
}
