package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dao {
    protected final String URL = "jdbc:mysql://localhost:3306/school?serverTimezone=UTC";
    protected final String USERNAME = "root";
    protected final String PASS = "1234";
    protected Connection conn;
    
    

    
}
