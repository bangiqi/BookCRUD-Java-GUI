/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookcrud;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author ramdani-pc
 */
public class Koneksi {
    
    public  Connection cc;
    public  Statement ss;
    public  ResultSet rr;
    
    String driverName = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/db_book";
    String userName = "root";
    String password = "";
    
    public  void getKoneksi(){
        try {
            cc = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_book", "root", "");
            JOptionPane.showMessageDialog(null, "Koneksi Berhasil");
            System.out.println("koneksi sukses");
                    
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Koneksi Gagal \n"+e);
             System.out.println("koneksi gagal : "+e);
        }
    }
    
}
