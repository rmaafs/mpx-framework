/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmaafs.mysql;

import java.util.HashMap;
import java.util.Map;
import static com.rmaafs.mysql.SelectBD.mysql;

/**
 *
 * @author Rodrigo M A
 */
public class UpdateBD extends RequestSQL {

    private HashMap<String, Object> data = new HashMap<>();

    public UpdateBD(String tabla) {
        this.tabla = tabla;
    }

    public void add(String column, Object value) {
        data.put(column, value);
    }

    public String execute() {
        if (data.isEmpty()) {
            return "No se ha añadido níngun dato a editar.";
        }
        String error = "";
        StringBuilder sql = new StringBuilder("UPDATE ").append(tabla).append(" SET ");//Sentencia que será ejecutada

        for (Map.Entry me : data.entrySet()) {
            sql.append(me.getKey()).append(" = ");
            sql.append(convertToValue(me.getValue()));
        }

        sql.setLength(sql.length() - 2);//Eliminamos lo último ", "
        sql.append(" WHERE ").append(where).append(";");
        this.sql = sql.toString();
        try {
            mysql.update(sql.toString());
        } catch (Exception e) {
            return "Error al añadir: " + e.getMessage();
        }
        return error;
    }
}
