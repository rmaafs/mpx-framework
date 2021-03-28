package com.rmaafs.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo M A
 */
public class SelectBD extends RequestSQL {
    
    private HashMap<Integer, Registro> data = new HashMap<>();
    private int size = 0;
    
    public SelectBD(String... array) {
        //Evitamos inyección SQL
        String sql = array[0];
        StringBuilder newSql = new StringBuilder();
        for (int i = 0, index = 1; i < sql.length(); i++) {
            if (sql.charAt(i) == '?') {
                newSql.append(removeSQLInyection(array[index++]));
            } else {
                newSql.append(sql.charAt(i));
            }
        }
        this.sql = newSql.toString();
    }
    
    public String execute() {
        ResultSet rs = mysql.query(sql);
        try {
            int i = 0;
            while (rs.next()) {
                try {
                    Registro reg = new Registro(rs);
                    data.put(i++, reg);
                } catch (Exception ex) {
                    Logger.getLogger(SelectBD.class.getName()).log(Level.SEVERE, null, ex);
                    return "Error al ejecutar: " + ex.getMessage();
                }
            }
            size = i;
        } catch (SQLException ex) {
            Logger.getLogger(SelectBD.class.getName()).log(Level.SEVERE, null, ex);
            return "Error al ejecutar: " + ex.getMessage();
        }
        return "";
    }
    
    public List<Registro> getRegistros() {
        List<Registro> registros = new ArrayList<>();
        for (Map.Entry me : data.entrySet()) {
            registros.add((Registro) me.getValue());
        }
        return registros;
    }
    
    public Registro getRegistro(int index) {
        return data.get(index);
    }
    
    public String getString(int index, String column) {
        Registro reg = data.get(index);
        return reg.getString(column);
    }
    
    public int getInt(int index, String column) {
        Registro reg = data.get(index);
        return reg.getInt(column);
    }
    
    public float getFloat(int index, String column) {
        Registro reg = data.get(index);
        return reg.getFloat(column);
    }
    
    public float getDouble(int index, String column) {
        Registro reg = data.get(index);
        return reg.getFloat(column);
    }
    
    public boolean getBoolean(int index, String column) {
        Registro reg = data.get(index);
        return reg.getInt(column) == 1;
    }
    
    public Date getDate(int index, String column) {
        Registro reg = data.get(index);
        return reg.getDate(column);
    }
    
    public int size() {
        return size;
    }
}
