/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dao.Dao;
import dao.HomeworkDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giorgos
 */
public class LoginDao extends Dao {

    private final String getUser = "SELECT * FROM users WHERE username=?";

    protected Connection getConnection() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private void closeConnections(ResultSet rs, Statement st) {
        try {
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Login getUser(String username) {
        Login login = null;
        try {
            PreparedStatement pst = getConnection().prepareStatement(getUser);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            rs.next();
            String user = rs.getString(2);
            if(user != null) {
                String pass = rs.getString(3);
                login = new Login(user, pass);
            }
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** No user found. ***");
        }
        return login;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
