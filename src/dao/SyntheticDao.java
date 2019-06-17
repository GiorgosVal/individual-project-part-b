package dao;

import entities.LoginDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SyntheticDao extends Dao {

    private final String getSyntheticData = "CALL syntheticData()";

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
    
    
    public void getSyntheticData() {
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(getSyntheticData);
            closeConnections(rs, st);
        } catch (SQLException ex) {
            Logger.getLogger(SyntheticDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

}
