/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author c0661047
 */
public class Utils {

    public final static String SALT = "THISISArandomSTRINGofCHARACTERSusedTOsaltTHEpasswords";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        String host = "localhost";
        String port = "3306";
        String db = "jsf_blog";
        String user = "root";
        String pass = "";

        String jdbc = "jdbc:mysql://" + host + ":" + port + "/" + db;
        return DriverManager.getConnection(jdbc, user, pass);
    }

    /**
     * Salts and hashes a password for semi-secure storage
     *
     * @param password the original password
     * @return the salted and hashed password
     */
    public static String hash(String password) {
        try {
            String salted = password + SALT;
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] hash = md.digest(salted.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(b & 0xff).toUpperCase();
                if (hex.length() == 1) {
                    sb.append("0");
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
