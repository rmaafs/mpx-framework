package com.rmaafs.mysql;

import com.rmaafs.filemanager.FileConfiguration;
import com.rmaafs.filemanager.YamlConfiguration;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo M A
 */
public class MySQL {

    private Connection con;
    private static String mysqlIp, mysqlPort, mysqlDB, mysqlUser, mysqlPass;
    private File fileConfig = new File("src/com/rmaafs/mysql/mysql-config.yml");
    private static FileConfiguration config;

    public MySQL() {
        //Si el archivo no existe.
        if (!fileConfig.exists()) {
            try {
                throw new Exception("No se encontró el archivo de configuración de MySQL");
            } catch (Exception ex) {
                Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Únicamente cargar una vez en RAM
        if (fileConfig.exists() && config == null) {
            config = YamlConfiguration.loadConfiguration(fileConfig);

            mysqlIp = config.getString("ip");
            mysqlPort = config.getString("port");
            mysqlDB = config.getString("database");
            mysqlUser = config.getString("user");
            mysqlPass = config.getString("password");
        }

        connect();
        inicializar();
    }

    private void connect() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            con = DriverManager.getConnection("jdbc:mysql://" + mysqlIp + ":" + mysqlPort + "/" + mysqlDB + "?characterEncoding=utf8", mysqlUser, mysqlPass);
            System.out.println("Successfully connected to database.");
        } catch (SQLException e) {
            System.out.println("ERROR MYSQL: " + e.getMessage());
        }
    }

    private void inicializar() {
        //update("CREATE TABLE `mych`.`usuario` ( `ID_USER` INT NOT NULL AUTO_INCREMENT , `usNombre` VARCHAR(255) NOT NULL , `usApellidos` VARCHAR(255) NOT NULL , `usCorreo` VARCHAR(255) NOT NULL , `usTelefono` VARCHAR(15) NOT NULL , `usFechaRegistro` DATETIME NOT NULL , `usFechaSesion` DATETIME NOT NULL , `usIsAdmin` BOOLEAN NOT NULL , `usEnabled` BOOLEAN NOT NULL DEFAULT TRUE , `usFotoPerfil` INT NOT NULL , PRIMARY KEY (`ID_USER`)) ENGINE = InnoDB;");
    }

    public void close() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Database closed");
            }
        } catch (SQLException e) {
            System.out.println("ERROR MYSQL: " + e.getMessage());
        }
    }

    public void update(String qry) throws Exception {
        Statement st = con.createStatement();
        st.executeUpdate(qry);
        st.close();
    }

    public ResultSet query(String qry) {
        ResultSet rs = null;

        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(qry);
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }
        return rs;
    }
}
