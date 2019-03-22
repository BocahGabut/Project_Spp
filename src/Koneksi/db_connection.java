/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Koneksi;

import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Randkill <Randkill.leader@gmail.com>
 */
public class db_connection {
 private static java.sql.Connection con;
    public static java.sql.Connection getKoneksi()
    {
        String host ="jdbc:mysql://localhost/project_spp",
                user = "root",
                pass = "";
        try 
        {
            con = (java.sql.Connection) DriverManager.getConnection(host,user,pass);
        }
            catch (SQLException err)
        {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        
        return con;
    }
}
