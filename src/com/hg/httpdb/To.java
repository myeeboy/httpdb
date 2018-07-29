package com.hg.httpdb;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据类型转换
 * @author wanghg
 */
public class To {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static boolean toBool(Object obj, boolean def) {
        if (obj != null) {
            if (obj instanceof Boolean) {
                return ((Boolean) obj).booleanValue();
            } else if (obj instanceof Integer) {
                return ((Integer) obj).intValue() == 0 ? false : true;
            } else if (obj instanceof Long) {
                return ((Long) obj).longValue() == 0 ? false : true;
            } else if (obj instanceof Double) {
                return ((Double) obj).doubleValue() == 0 ? false : true;
            } else if (obj instanceof Date) {
                return ((Date) obj).getTime() == 0 ? false : true;
            } else {
                String str = obj.toString().toUpperCase();
                return str.equals("FALSE") || str.equals("F") || str.equals("NO") 
                    || str.equals("OFF") || str.equals("0") || str.equals("") ? false : true;
            }
        } else {
            return def;
        }
    }
    public static boolean toBool(Object obj) {
        return toBool(obj, false);
    }
    public static long toLong(Object obj, long def) {
        if (obj != null) {
            if (obj instanceof Long) {
                return ((Long) obj).longValue();
            } else if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            } else if (obj instanceof Double) {
                return ((Double) obj).longValue();
            } else if (obj instanceof Boolean) {
                return ((Boolean) obj).booleanValue() ? 1 : 0;
            } else if (obj instanceof Date) {
                return ((Date) obj).getTime();
            } else {
                try {
                    return Long.parseLong(obj.toString());
                } catch (Exception e) {
                    return def;
                }
            }
        } else {
            return def;
        }
    }
    public static long toLong(Object obj) {
        return toLong(obj, (long) 0);
    }
    public static int toInt(Object obj, int def) {
        if (obj != null) {
            if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            } else if (obj instanceof Short) {
                return ((Short) obj).intValue();
            } else if (obj instanceof Long) {
                return ((Long) obj).intValue();
            } else if (obj instanceof Double) {
                return ((Double) obj).intValue();
            } else if (obj instanceof Boolean) {
                return ((Boolean) obj).booleanValue() ? 1 : 0;
            } else if (obj instanceof Date) {
                return (int) ((Date) obj).getTime();
            } else {
                try {
                    return Integer.parseInt(obj.toString());
                } catch (Exception e) {
                    try {
                        return (new Double(Double.parseDouble(obj.toString()))).intValue();
                    } catch (Exception e2) {
                        return def;
                    }
                }
            }
        } else {
            return def;
        }
    }
    public static int toInt(Object obj) {
        return toInt(obj, 0);
    }
    public static double toDouble(Object obj, double def) {
        if (obj != null) {
            if (obj instanceof Double) {
                return ((Double) obj).doubleValue();
            } else if (obj instanceof Float) {
                return ((Float) obj).doubleValue();
            } else if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            } else if (obj instanceof Long) {
                return ((Long) obj).doubleValue();
            } else if (obj instanceof Boolean) {
                return ((Boolean) obj).booleanValue() ? 1 : 0;
            } else if (obj instanceof Date) {
                return ((Date) obj).getTime();
            } else {
                try {
                    return Double.parseDouble(obj.toString());
                } catch (Exception e) {
                    return def;
                }
            }
        } else {
            return def;
        }
    }
    public static double toDouble(Object obj) {
        return toDouble(obj, 0);
    }
    public static Date toDate(Object obj, Date def) {
        if (obj != null) {
            if (obj instanceof Date) {
                return ((Date) obj);
            } else if (obj instanceof Integer) {
                return new Date((long) ((Integer) obj).intValue());
            } else if (obj instanceof Long) {
                return new Date(((Long) obj).longValue());
            } else if (obj instanceof Double) {
                return new Date(((Double) obj).longValue());
            } else if (obj instanceof String) {
            	String str = (String) obj;
            	try {
            		if (str.length() <= DATETIME_FORMAT.length()) {
            			return new SimpleDateFormat(DATETIME_FORMAT.substring(0, str.length())).parse(str);
            		} else {
            			return def;
            		}
            	} catch (ParseException e) {
        			return def;
            	}
            } else {
                return def;
            }
        } else {
            return def;
        }
    }
    public static String toString(Object obj) {
        return toString(obj, "");
    }
	private static SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
    public static String toString(Object obj, String def) {
        if (obj != null) {
            if (obj instanceof Date) {
                return sdf.format((Date) obj);
            } else if (obj instanceof Float || obj instanceof Double) {
                String str = obj.toString();
                if (str.indexOf("E") > 0) {
                    DecimalFormat df = new DecimalFormat("0.######");
                    if (obj instanceof Float) {
                        str = df.format(((Float) obj).floatValue());
                    } else {
                        str = df.format(((Double) obj).doubleValue());
                    }
                }
                if (str.endsWith(".0")) {
                	str = str.substring(0, str.length() - 2);
                }
                return str;
            } else {
                return obj.toString();
            }
        } else {
            return def;
        }
    }
	public static Date toDate(Object obj) {
		return toDate(obj, new Date());
	}
}
