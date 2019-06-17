package dao;

import entities.Student;
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

public class StudentDao extends Dao {

    private final String insertStudent = "INSERT INTO students (sfname, slname, sdob, sfees) VALUES (?,?,?,?)";
    private final String getStudents = "SELECT * FROM students";
    private final String getStudentById = "SELECT * FROM students WHERE id=?";
    private final String deleteStudentById = "delete from students where id=?";
    

    protected Connection getConnection() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private void closeConnections(ResultSet rs, Statement st) {
        try {
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

/* ----------------------------------------------------------------------------
CREATE METHODS
 ---------------------------------------------------------------------------- */
    public boolean insertStudent(Student s) {
        boolean insertOK = false;
        try {
            PreparedStatement pst = getConnection().prepareStatement(insertStudent);
            pst.setString(1, s.getfNameReformed());
            pst.setString(2, s.getlNameReformed());
            pst.setString(3, s.getDobSQLString());
            pst.setDouble(4, s.getFees());
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Student inserted successfully.");
                insertOK = true;
            } else {
                System.out.println("*** Student not inserted. ***");
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
    public List<Student> getStudents() {
        List<Student> list = new ArrayList();
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(getStudents);
            while (rs.next()) {
                LocalDate sdob = rs.getDate(4).toLocalDate();
                Student c = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), sdob, rs.getDouble(5));
                list.add(c);
            }
            closeConnections(rs, st);
        } catch (SQLException ex) {
            System.out.println("*** Couldn't read from database. ***");
        }
        return list;
    }

    public Student getStudentById(int id) {
        Student c = null;
        try {
            PreparedStatement pst = getConnection().prepareStatement(getStudentById);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            LocalDate sdob = rs.getDate(4).toLocalDate();
            c = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), sdob, rs.getDouble(5));
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Student not selected. You probably entered wrong id. ***");
        }
        return c;
    }

    
/* ----------------------------------------------------------------------------
DELETE METHODS
 ---------------------------------------------------------------------------- */    
    
    public void deleteStudentById (int st_id) {
        try {
            PreparedStatement pst = getConnection().prepareStatement(deleteStudentById);
            pst.setInt(1, st_id);

            int result = pst.executeUpdate();       // η μέθοδος επιστρέφει ή τον αριθμό των γραμμών του πίνακα που εισήγαμε (insert), ή που διέγραψε (delete), ή του πίνακα γενικά (update) ή μηδέν
            if (result > 0) {
                System.out.println("Student deleted successfully.");
                
            } else {
                System.out.println("Student not deleted.");
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Not deleted.");
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
