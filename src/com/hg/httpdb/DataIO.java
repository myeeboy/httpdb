package com.hg.httpdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据传输
 * @author wanghg
 */
public class DataIO {
    public static Object read(InputStream in, String password) throws Exception {
    	ByteArrayOutputStream bout = new ByteArrayOutputStream();
    	pipe(in, bout);
    	DataInputStream din = new DataInputStream(new ByteArrayInputStream(Crypt.decrypt(bout.toByteArray(), password)));
    	bout = null;
    	if (din.readBoolean()) {
    		int n = din.readInt();
    		if (n == 0) {
    			return readString(din);
    		} else if (n == 1) {
    			n = din.readInt();
                RowSet rowSet = new RowSet();
                List fields = rowSet.getFields();
                List rows = rowSet.getRows();
                for (int i = 0; i < n; i++) {
                	fields.add(new Field(readString(din), readString(din)));
                }
                int rowSize = din.readInt();
                Map map;
                for (int i = 0; i < rowSize; i++) {
            		map = new HashMap();
            		rows.add(map);
                    for (int j = 0; j < n; j++) {
                    	map.put(((Field) fields.get(j)).getName(), readString(din));
                    }
                }
                return rowSet;
    		} else {
    			return "";
    		}
    	} else {
            throw new Exception(readString(din), new Exception(readString(din)));
    	}
    }

	public static void write(OutputStream out, Object obj, String password) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		dout.writeBoolean(!(obj instanceof Exception));
		if (obj instanceof Exception) {
			Exception e = (Exception) obj;
			writeString(dout, e.getMessage() != null ? e.getMessage() : "");
	        StringWriter sw = new StringWriter();
	        e.printStackTrace(new PrintWriter(sw));
			writeString(dout, sw.toString());
		} else if (obj instanceof RowSet) {
			RowSet rowSet = (RowSet) obj;
			dout.writeInt(1);
			dout.writeInt(rowSet.getFields().size());
			for (int i = 0; i < rowSet.getFields().size(); i++) {
				writeString(dout, rowSet.fieldAt(i).getName());
				writeString(dout, rowSet.fieldAt(i).getType());
			}
			dout.writeInt(rowSet.size());
			Map row;
			for (int i = 0; i < rowSet.getRows().size(); i++) {
				row = (Map) rowSet.getRows().get(i);
				for (int j = 0; j < rowSet.getFields().size(); j++) {
					writeString(dout, (String) row.get(rowSet.fieldAt(j).getName()));
				}
			}
		} else {
			dout.writeInt(0);
			writeString(dout, To.toString(obj));
		}
		dout.flush();
		dout.close();
		bout.flush();
		bout.close();
		out.write(Crypt.encrypt(bout.toByteArray(), password));
		out.flush();
		out.close();
	}

	private static void writeString(DataOutputStream dout, String str) throws IOException {
		if (str.length() > 0) {
			byte[] bytes = str.getBytes("utf-8");
			dout.writeInt(bytes.length);
			dout.write(bytes);
		} else {
			dout.writeInt(0);
		}
	}
	private static String readString(DataInputStream din) throws IOException {
		int n = din.readInt();
		if (n > 0) {
			byte[] bytes = new byte[n];
			din.readFully(bytes);
			return new String(bytes, "utf-8");
		} else {
			return "";
		}
	}
    private static void pipe(InputStream in, OutputStream out) throws IOException {
        int len;
        byte[] buf = new byte[4096];
        while (true) {
            len = in.read(buf);
            if (len > 0) {
                out.write(buf, 0, len);
            } else {
                break;
            }
        }
        out.flush();
    }
}
