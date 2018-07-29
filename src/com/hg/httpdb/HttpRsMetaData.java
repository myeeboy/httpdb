package com.hg.httpdb;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * 记录集元数据
 * @author wanghg
 */
public class HttpRsMetaData implements ResultSetMetaData {
    private List fields;

    public HttpRsMetaData(List fields) {
        this.fields = fields;
    }
    public int getColumnCount() throws SQLException {
        return this.fields.size();
    }
    public boolean isAutoIncrement(int column) throws SQLException {
        return true;
    }
    public boolean isCaseSensitive(int column) throws SQLException {
        return false;
    }
    public boolean isSearchable(int column) throws SQLException {
        return true;
    }
    public boolean isCurrency(int column) throws SQLException {
        return false;
    }
    public int isNullable(int column) throws SQLException {
        return ResultSetMetaData.columnNullable;
    }
    public boolean isSigned(int column) throws SQLException {
        return true;
    }
    public int getColumnDisplaySize(int column) throws SQLException {
        return 20;
    }
    private Field getField(int column) throws SQLException {
        if (column <= this.fields.size()) {
            return (Field) this.fields.get(column - 1);
        } else {
            throw new SQLException(new IndexOutOfBoundsException(
            		"Index: "+column+", Size: "+this.fields.size()));
        }
    }
    public String getColumnLabel(int column) throws SQLException {
        return this.getField(column).getName();
    }
    public String getColumnName(int column) throws SQLException {
        return this.getField(column).getName();
    }
    public String getSchemaName(int column) throws SQLException {
        return "";
    }
    public int getPrecision(int column) throws SQLException {
        return 0;
    }
    public int getScale(int column) throws SQLException {
        return 0;
    }
    public String getTableName(int column) throws SQLException {
        return "";
    }
    public String getCatalogName(int column) throws SQLException {
        return "";
    }
    public int getColumnType(int column) throws SQLException {
        return this.getField(column).getIntType();
    }
    public String getColumnTypeName(int column) throws SQLException {
        return this.getField(column).getType();
    }
    public boolean isReadOnly(int column) throws SQLException {
        return true;
    }
    public boolean isWritable(int column) throws SQLException {
        return false;
    }
    public boolean isDefinitelyWritable(int column) throws SQLException {
        return false;
    }
    public String getColumnClassName(int column) throws SQLException {
        return this.getField(column).getTypeClass().getName();
    }
    public Object unwrap(Class arg0) throws SQLException {
        return null;
    }
    public boolean isWrapperFor(Class arg0) throws SQLException {
        return false;
    }
}
