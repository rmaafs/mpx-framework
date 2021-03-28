/**
 * Rodrigo Maafs | AppsCamelot 2020
 */
package com.rmaafs.mysql;

/**
 *
 * @author Rodrigo Maafs
 */
public class MySQLConfig {
    
    protected String mysqlIp, mysqlPort, mysqlDB, mysqlUser, mysqlPass;

    public MySQLConfig() {
        mysqlIp = "localhost";
        mysqlPort = "3306";
        mysqlDB = "database";
        mysqlUser = "user";
        mysqlPass = "password";
    }
}
