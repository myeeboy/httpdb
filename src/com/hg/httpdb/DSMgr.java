package com.hg.httpdb;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 数据源管理器
 * @author wanghg
 */
public class DSMgr {
	private static Logger logger = Logger.getLogger(DSMgr.class);
	private static DSMgr instance = new DSMgr();

	public static DSMgr getInstance() {
		return instance;
	}
	private Map dsMap = new HashMap();
	private Map dsPasswordMap = new HashMap();
	private Map dsWritableMap = new HashMap();
	public Connection getConnection(String db) throws SQLException {
		DataSource ds = (DataSource) this.dsMap.get(db);
		if (ds != null) {
			return ds.getConnection();
		} else {
			throw new SQLException("无效数据库:" + db);
		}		
	}
	public String getPassword(String db) {
		return String.valueOf(this.dsPasswordMap.get(db));
	}
	public boolean isWritable(String db) {
		return ((Boolean) this.dsWritableMap.get(db)).booleanValue();
	}
	public void init(InputStream in) throws Exception {
		this.dsMap.clear();
		this.dsPasswordMap.clear();
		Document doc = new SAXReader().read(in);
		Iterator it = doc.getRootElement().elementIterator("db");
		Element ele, property;
		DataSource ds;
		Iterator pit;
		while (it.hasNext()) {
			ele = (Element) it.next();
			logger.info("初始化数据库:" + ele.attributeValue("name"));
			if (ele.attributeValue("class", "").length() > 0) {
				try {
					ds = (DataSource) Class.forName(ele.attributeValue("class", "")).newInstance();
					this.dsMap.put(ele.attributeValue("name"), ds);
					this.dsPasswordMap.put(ele.attributeValue("name"), ele.attributeValue("password", ""));
					this.dsWritableMap.put(ele.attributeValue("name"), ele.attributeValue("writable", "false").equals("true") ? Boolean.TRUE : Boolean.FALSE);
					pit = ele.elementIterator("property");
					while (pit.hasNext()) {
						property = (Element) pit.next();
						setProperty(ds, property.attributeValue("name"), property.attributeValue("value"));
					}
					ds.getConnection().close();
				} catch (Exception e) {
					logger.error(e);
				}
			} else {
				logger.warn("class不能为空");
			}
		}
	}
	private void setProperty(Object ds, String method, String value) {
		method = "set" + Character.toUpperCase(method.charAt(0)) + method.substring(1);
		Method[] methods = ds.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(method) && methods[i].getParameterTypes().length == 1) {
				try {
					if (methods[i].getParameterTypes()[0] == String.class) {
						methods[i].invoke(ds, new Object[] {value});
					} else if (methods[i].getParameterTypes()[0] == Integer.class
							|| methods[i].getParameterTypes()[0] == int.class) {
						methods[i].invoke(ds, new Object[] {new Integer(To.toInt(value))});
					} else if (methods[i].getParameterTypes()[0] == Double.class
							|| methods[i].getParameterTypes()[0] == double.class) {
						methods[i].invoke(ds, new Object[] {new Double(To.toDouble(value))});
					} else if (methods[i].getParameterTypes()[0] == Boolean.class
							|| methods[i].getParameterTypes()[0] == boolean.class) {
						methods[i].invoke(ds, new Object[] {new Boolean(To.toBool(value))});
					} else {
						logger.error("不支持的属性数据类型：" + methods[i].getParameterTypes()[0].getName());
					}
				} catch (Exception e) {
					logger.error(e);
				}
				return;
			}
		}
		logger.error("找不到属性方法：" + method);
	}
}
