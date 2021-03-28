package com.rmaafs.mysql;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Rodrigo M A
 */
public class RequestSQL {

    public static MySQL mysql = new MySQL();

    protected String sql;
    protected String tabla, where;

    /**
     * Función que cambia la sentencia WHERE que se usará en la petición
     * @param array Sentencía WHERE con parámetros
     */
    public void setWhere(Object... array) {
        String newArray[] = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            if (array[i] instanceof Integer) {
                newArray[i] = (Integer) array[i] + "";
            } else if (array[i] instanceof BigDecimal) {
                newArray[i] = ((BigDecimal) array[i]).intValue() + "";
            } else {
                newArray[i] = (String) array[i];
            }

        }
        setWhere(newArray);
    }

    /**
     * Función que cambia la sentencia WHERE que se usará en la petición
     * @param array Sentencía WHERE con parámetros
     */
    public void setWhere(String... array) {
        //Evitamos inyección SQL
        String where = array[0];
        StringBuilder newSql = new StringBuilder();
        for (int i = 0, index = 1; i < where.length(); i++) {
            if (where.charAt(i) == '?') {
                newSql.append(removeSQLInyection(array[index++]));
            } else {
                newSql.append(where.charAt(i));
            }
        }
        this.where = newSql.toString();
    }

    /**
     * Función que detecta si un objeto es String o no, y lo encierra en ''.
     * Por ejemplo, WHERE usNombre = 'Rodrigo' OR usEdad = 21
     * @param object Objeto a verificar
     * @return Retorna un String con o sin ''
     */
    protected StringBuilder convertToValue(Object object) {
        if (object instanceof Integer) {
            return new StringBuilder(((int) object) + "").append(", ");
        } else if (object instanceof Float) {
            return new StringBuilder("'" + ((float) object) + "', ");
        } else if (object instanceof Boolean) {
            return new StringBuilder((((Boolean) object) == true ? 1 : 0) + ", ");
        } else if (object instanceof BigDecimal) {
            return new StringBuilder(((BigDecimal) object).intValue() + ", ");
        } else if (object instanceof Date) {
            return new StringBuilder("str_to_date('" + (new SimpleDateFormat("dd/MM/yyyy").format(((Date) object))) + "', '%d/%m/%Y'), ");
        }
        try {
            return new StringBuilder("'").append(removeSQLInyection((String) object)).append("', ");
        } catch (Exception e) {
            return new StringBuilder((int) object).append(", ");
        }
    }

    /**
     * Función que nos da la petición MySQL que se ejecutó
     * @return Petición MySQL que se ejecutó
     */
    public String getSQL() {
        return sql;
    }

    /**
     * Función que remueve carácteres extraños que se usan para inyectar código SQL
     * @param param Parámetro a verificar
     * @return Retornará el parámetro con inyección SQL eliminado
     */
    protected String removeSQLInyection(String param) {
        param = param.replaceAll("'", "");
        return param;
    }
}
