package com.hg.httpdb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * HttpDB服务
 * @author wanghg
 */
public class Servlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(Servlet.class);
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
        	Properties logs = new Properties();
        	Properties tmpLogs = new Properties();
        	tmpLogs.load(config.getServletContext().getResourceAsStream("/WEB-INF/log4j.properties"));
        	Iterator it = tmpLogs.keySet().iterator();
        	String key;
        	String value;
        	while (it.hasNext()) {
        		key = (String) it.next();
        		value = (String) tmpLogs.get(key);
        		if (key.endsWith(".File")) {
        			value = config.getServletContext().getRealPath(value);
        		}
        		logs.put(key, value);
        	}
        	PropertyConfigurator.configure(logs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DSMgr.getInstance().init(config.getServletContext().getResourceAsStream("/WEB-INF/httpdb.xml"));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		PrintWriter writer = res.getWriter();
		writer.println("<html><body><h1>HttpDB Running...</h1><body><html>");
		writer.flush();
		writer.close();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String db = req.getParameter("db");
        String sql = req.getParameter("sql");
        String password = DSMgr.getInstance().getPassword(db);
        sql = Crypt.decrypt(sql, DSMgr.getInstance().getPassword(db));
        Connection conn = null;
        if (db != null && sql != null && sql.length() > 0) {
        	Statement stmt = null;
        	ResultSet rs = null;
        	try {
        		conn = DSMgr.getInstance().getConnection(db);
        		logger.info(db + ":" + sql);
        		if (sql.startsWith("$")) {
        			String strs[] = sql.split(",");
            		if (sql.startsWith("$hi$")) {
            			rs = toResultSet("ok");
            		} else if (sql.startsWith("$meta$")) {
            			DatabaseMetaData meta = conn.getMetaData();
            			Map map = new HashMap();
            			map.put("DatabaseProductName", meta.getDatabaseProductName());
            			map.put("DatabaseProductVersion", meta.getDatabaseProductVersion());
            			map.put("DatabaseMajorVersion", String.valueOf(meta.getDatabaseMajorVersion()));
            			map.put("DatabaseMinorVersion", String.valueOf(meta.getDatabaseMinorVersion()));
            			map.put("DriverName", String.valueOf(meta.getDriverName()));
            			map.put("DriverVersion", String.valueOf(meta.getDriverVersion()));
            			map.put("supportsSchemasInTableDefinitions", String.valueOf(meta.supportsSchemasInTableDefinitions()));
                		rs = toResultSet(map);
                	} else if (sql.startsWith("$catalogs$")) {
                		rs = conn.getMetaData().getCatalogs();
                	} else if (sql.startsWith("$schemas$")) {
                		try {
                			rs = conn.getMetaData().getSchemas(metaParam(strs, 0),
                					null);
                		} catch (Throwable e) {
                			rs = conn.getMetaData().getSchemas();
                		}
            		} else if (sql.startsWith("$tables$")) {
            			rs = conn.getMetaData().getTables(metaParam(strs, 0),
            					metaParam(strs, 1),
            					null,
            					null);
            		} else if (sql.startsWith("$columns$")) {
            			rs = conn.getMetaData().getColumns(metaParam(strs, 0),
            					metaParam(strs, 1),
            					metaParam(strs, 2),
            					null);
	        		} else {
	        			throw new Exception("无效元数据查询");
	        		}
        		} else if (sql.trim().toLowerCase().startsWith("select")) {
        			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        			if (req.getParameter("maxrows") != null) {
        				stmt.setMaxRows(To.toInt(req.getParameter("maxrows"), Integer.MAX_VALUE));
        			}
        			rs = stmt.executeQuery(sql);
        		} else if (DSMgr.getInstance().isWritable(db)) {
        			stmt = conn.createStatement();
        			write(req, res, String.valueOf(stmt.executeUpdate(sql)), password);
        		} else {
        			throw new Exception("无效查询语句");
        		}
        		if (rs != null) {
        			write(req, res, toRowSet(rs), password);
        		}
        	} catch (Exception e) {
        		logger.error(e);
        		write(req, res, e, password);
        	} finally {
        		if (rs != null) {
        			try {
        				rs.close();
        			} catch (Exception e) {
        			}
        		}
        		if (stmt != null) {
        			try {
        				stmt.close();
        			} catch (Exception e) {
        			}
        		}
        		if (conn != null) {
        			try {
        				conn.close();
        			} catch (Exception e) {
        			}
        		}
        	}
        }
	}

	private String metaParam(String[] strs, int inx) {
		if (strs.length > inx + 1 && !strs[inx + 1].equals("null")) {
			return strs[inx + 1];
		} else {
			return null;
		}
	}

	private void write(HttpServletRequest req, HttpServletResponse res, Object obj, String password) {
		try {
			res.setContentType("application/octet-stream");
			ServletOutputStream out = res.getOutputStream();
			DataIO.write(out, obj, password);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	private Object toRowSet(ResultSet rs) throws SQLException {
        RowSet rowSet = new RowSet();
        Field field;
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
        	field = new Field(String.valueOf(rsmd.getColumnName(i)));
        	rowSet.getFields().add(field);
        }
        Object obj;
        Map map;
        while (rs.next()) {
        	map = new HashMap();
        	for (int i = 1; i <= rowSet.getFields().size(); i++) {
        		obj = rs.getObject(i);
        		map.put(rowSet.fieldAt(i - 1).getName(), To.toString(obj));
        	}
        	rowSet.add(map);
        }
        return rowSet;
    }
	private ResultSet toResultSet(String str) {
        RowSet rowSet = new RowSet();
        rowSet.getFields().add(new Field("STRING"));
        Map map = new HashMap();
        rowSet.add(map);
        map.put("STRING", str);
        return new HttpResultSet(rowSet);
	}
	private ResultSet toResultSet(Map map) {
		RowSet rowSet = new RowSet();
        rowSet.getFields().add(new Field("KEY"));
        rowSet.getFields().add(new Field("VALUE"));
        Iterator it = map.keySet().iterator();
        Map row;
        String key;
        while (it.hasNext()) {
        	key = (String) it.next();
        	row = new HashMap();
        	rowSet.add(row);
        	row.put("KEY", key);
        	row.put("VALUE", To.toString(map.get(key)));
        }
        return new HttpResultSet(rowSet);
	}
}
