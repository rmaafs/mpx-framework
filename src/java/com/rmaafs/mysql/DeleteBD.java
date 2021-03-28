/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmaafs.mysql;

import static com.rmaafs.mysql.SelectBD.mysql;

/**
 *
 * @author Rodrigo M A
 */
public class DeleteBD extends RequestSQL {

    public DeleteBD(String tabla) {
        this.tabla = tabla;
    }
    
    public String execute() {
        if (where == null) {
            return "Por seguridad, mande algo al WHERE.";
        }
        String error = "";
        StringBuilder sql = new StringBuilder("DELETE FROM ").append(tabla);//Sentencia que será ejecutada
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
