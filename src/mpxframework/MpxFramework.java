/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpxframework;

import com.rmaafs.filemanager.FileConfiguration;
import com.rmaafs.filemanager.YamlConfiguration;
import com.rmaafs.mysql.MySQL;
import com.rmaafs.mysql.SelectBD;
import java.io.File;

/**
 *
 * @author Rodrigo Maafs
 */
public class MpxFramework {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        File config = new File("config.yml");
//        if (config.exists()) {
//            System.out.println("Existe");
//        }
//        FileConfiguration cconfig = YamlConfiguration.loadConfiguration(config);
//
//        System.out.println("Hola " + cconfig.getString("nombre"));

        SelectBD select = new SelectBD("SELECT * FROM registros LIMIT 10");
        select.execute();
        System.out.println("Total: " + select.size());
        
        
        SelectBD select2 = new SelectBD("SELECT * FROM registros LIMIT 100");
        select2.execute();
        System.out.println("Total2: " + select2.size());
    }
    
}
