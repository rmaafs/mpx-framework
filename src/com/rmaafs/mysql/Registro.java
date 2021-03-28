package com.rmaafs.mysql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Rodrigo M A
 */
public class Registro {

    private HashMap<String, Object> data = new HashMap<>();

    public Registro(ResultSet rs) throws Exception {
        ResultSetMetaData rsmd = rs.getMetaData();
        Object value = null;
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            try {
                String column = (String) rsmd.getColumnName(i + 1);
                //System.out.println("Tipo " + column + ": " + rsmd.getColumnTypeName(i + 1));
                if (rsmd.getColumnTypeName(i + 1).equals("VARCHAR")) {
                    value = rs.getString(column);
                } else if (rsmd.getColumnTypeName(i + 1).equals("INT") || rsmd.getColumnTypeName(i + 1).equals("SIGNED")) {
                    value = rs.getInt(column);
                } else if (rsmd.getColumnTypeName(i + 1).equals("TINYINT")) {
                    value = rs.getInt(column);
                } else if (rsmd.getColumnTypeName(i + 1).equals("FLOAT")) {
                    value = rs.getFloat(column);
                } else if (rsmd.getColumnTypeName(i + 1).equals("DOUBLE")) {
                    value = rs.getDouble(column);
                } else if (rsmd.getColumnTypeName(i + 1).equals("DATE")) {
                    value = rs.getDate(column);
                } else if (rsmd.getColumnTypeName(i + 1).equals("DATETIME")) {
                    value = new java.util.Date(rs.getTimestamp(column).getTime());
                } else {
//                    System.out.println("AÑADE " + rsmd.getColumnTypeName(i + 1) + " a Registro.java");
                    value = (String) rs.getObject(column);
                }
                data.put(column, value);
            } catch (Exception e) {
                System.out.println(rsmd.getColumnCount() + " | " + i);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Función que nos dará el dato guardado de una columna
     * @param column Columna a obtener
     * @return Retornará el dato que tiene guardado en la columna
     */
    public Object getData(String column) {
        return data.get(column);
    }
    
    public String getString(String column) {
        return getData(column) != null ? (String) getData(column) : "";
    }
    
    public int getInt(String column) {
        return getData(column) != null ? (int) getData(column) : 0;
    }
    
    public float getFloat(String column) {
        return getData(column) != null ? (float) getData(column) : 0;
    }
    
    public double getDouble(String column) {
        return getData(column) != null ? (double) getData(column) : 0;
    }
    
    public boolean getBoolean(String column) {
        return getData(column) != null ? ((int) getData(column)) == 1 : false;
    }
    
    public Date getDate(String column) {
        return getData(column) != null ? (Date) getData(column) : null;
    }
}
