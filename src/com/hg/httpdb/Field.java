package com.hg.httpdb;

import java.sql.Types;
import java.util.Date;

/**
 * 字段
 * @author wanghg
 */
public class Field {
	public static final String TYPE_STRING = "string";
	public static final String TYPE_NUMBER = "number";
	public static final String TYPE_DATE = "date";

	private String name;
	private String type;
	public Field(String name) {
		this(name, TYPE_STRING);
	}
	public Field(String name, String type) {
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public int getIntType() {
		if (type.equals(TYPE_NUMBER)) {
			return Types.NUMERIC;
		} else if (type.equals(TYPE_DATE)) {
			return Types.DATE;
		} else {
			return Types.VARCHAR;
		}
	}
	public void setType(String type) {
		this.type = type;
	}
	public Class getTypeClass() {
		if (type.equals(TYPE_NUMBER)) {
			return Number.class;
		} else if (type.equals(TYPE_DATE)) {
			return Date.class;
		} else {
			return String.class;
		}
	}
}
