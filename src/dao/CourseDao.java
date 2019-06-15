package dao;

import entities.Course;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseDao extends Dao {

    private final String insertCourse = "INSERT INTO courses (ctitle, cstream, ctype, cstart, cend) VALUES (?,?,?,?,?)";
    private final String getCourses = "SELECT * FROM courses";
    private final String getCourseById = "SELECT * FROM courses WHERE id=?";

    protected Connection getConnection() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private void closeConnections(ResultSet rs, Statement st) {
        try {
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
/* ----------------------------------------------------------------------------
CREATE METHODS
---------------------------------------------------------------------------- */
    public boolean insertCourse(Course s) {
        boolean insertOK = false;
        try {
            PreparedStatement pst = getConnection().prepareStatement(insertCourse);
            pst.setString(1, s.getCtitle());
            pst.setString(2, s.getCstream());
            pst.setString(3, s.getCtype());
            pst.setString(4, s.getCstartSQLString());
            pst.setString(5, s.getCendSQLString());
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Course inserted successfully.");
                insertOK = true;
            } else {
                System.out.println("*** Course not inserted. ***");
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("*** Not inserted. ***");
            System.out.println("*** You are probably trying to insert doublicate values. ***");
        }
        return insertOK;
    }

    /* ----------------------------------------------------------------------------
READ METHODS
 ---------------------------------------------------------------------------- */
    public List<Course> getCourses() {
        List<Course> list = new ArrayList();
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(getCourses);
            while (rs.next()) {
                LocalDate cstart = rs.getDate(5).toLocalDate();
                LocalDate cend = rs.getDate(6).toLocalDate();
                Course c = new Course(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), cstart, cend);
                list.add(c);
            }
            closeConnections(rs, st);
        } catch (SQLException ex) {
            System.out.println("*** Couldn't read from database. ***");
        }
        return list;
    }

    public Course getCourseById(int id) {
        Course c = null;
        try {
            PreparedStatement pst = getConnection().prepareStatement(getCourseById);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            LocalDate cstart = rs.getDate(5).toLocalDate();
            LocalDate cend = rs.getDate(6).toLocalDate();
            c = new Course(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), cstart, cend);
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Course not selected. You probably entered wrong id. ***");
        }
        return c;
    }
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
