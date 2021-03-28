/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmaafs.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.rmaafs.mysql.SelectBD.mysql;

/**
 *
 * @author Rodrigo M A
 */
public class InsertBD extends RequestSQL {

    private HashMap<String, Object> data = new HashMap<>();
    private int id = 0;
    private boolean is1N = false;

    public InsertBD(String tabla) {
        this.tabla = tabla;
    }

    public void add(String column, Object value) {
        data.put(column, value);
        if (value instanceof ArrayList || value instanceof List) {
            is1N = true;
        }
    }

    public String execute() {
        String error = "";
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        StringBuilder sql = new StringBuilder();//Sentencia que será ejecutada

        //Si la consulta NO es 1 a muchos
        if (!is1N) {
            for (Map.Entry me : data.entrySet()) {
                columns.append(me.getKey()).append(", ");
                values.append(convertToValue(me.getValue()));
            }

            columns.setLength(columns.length() - 2);//Eliminamos lo último ", "
            values.setLength(values.length() - 2);//Eliminamos lo último ", "
            sql.append("INSERT INTO ").append(tabla).append("(").append(columns).append(") VALUES(").append(values).append(");");
        } else {
            List<String> array = new ArrayList<>();
            for (Map.Entry me : data.entrySet()) {
                columns.append(me.getKey()).append(", ");
                if (me.getValue() instanceof ArrayList || me.getValue() instanceof List) {
                    array = (ArrayList) me.getValue();
                }
            }
            columns.setLength(columns.length() - 2);//Eliminamos lo último ", "
            sql.append("INSERT INTO ").append(tabla).append(" (").append(columns).append(") VALUES");

            for (String index : array) {
                StringBuilder value = new StringBuilder("(");
                for (Map.Entry me : data.entrySet()) {
                    if (me.getValue() instanceof ArrayList || me.getValue() instanceof List) {
                        value.append(convertToValue(index));
                    } else {
                        value.append(me.getValue()).append(", ");
                    }
                }
                value.setLength(value.length() - 2);//Eliminamos lo último ", "
                value.append("), ");
                sql.append(value);
            }
            sql.setLength(sql.length() - 2);//Eliminamos lo último ", "
        }

        this.sql = sql.toString();
        try {
            mysql.update(sql.toString());
        } catch (Exception e) {
            return "Error al añadir: " + e.getMessage();
        }
        return error;
    }

    public int getId(String column) {
        if (id > 0) {
            return id;
        }
        StringBuilder sql = new StringBuilder("SELECT " + column + " FROM " + tabla + " WHERE ");

        for (Map.Entry me : data.entrySet()) {
            sql.append(me.getKey()).append(" = ");
            
            StringBuilder param = convertToValue(me.getValue());
            param.setLength(param.length() - 2);
            sql.append(param).append(" AND ");
        }
        sql.setLength(sql.length() - 4);//Eliminamos lo último " AND "
        
        this.sql = sql.toString();

        SelectBD select = new SelectBD(sql.toString());
        select.execute();
        id = select.getInt(0, column);
        return id;
    }
}
