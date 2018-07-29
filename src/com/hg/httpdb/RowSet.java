package com.hg.httpdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据集
 * @author wanghg
 */
public class RowSet {
	private List fields = new ArrayList();
	private List rows = new ArrayList();
	public List getFields() {
		return fields;
	}
	public Field fieldAt(int inx) {
		return (Field) this.fields.get(inx);
	}
	public int fieldIndex(String name) {
		for (int i = 0; i < this.fields.size(); i++) {
			if (((Field) this.fields.get(i)).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}
	public List getRows() {
		return rows;
	}
	public Map get(int inx) {
		return (Map) this.rows.get(inx);
	}
	public void add(Map map) {
		this.rows.add(map);
	}
	public int size() {
		return this.rows.size();
	}
	public Object value(int row, int col) {
		if (row < this.rows.size() && col < this.fields.size()) {
			return ((Map) this.rows.get(row)).get(this.fieldAt(col).getName());
		} else {
			return null;
		}
	}
}
