package dao;

import entities.Student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnrollmentDao extends Dao {

    private final String insertStudentToCourse = "INSERT INTO enrollments(co_id, st_id) VALUES(?,?)";
    private final String getStudentsPerCourse = "SELECT e.st_id FROM enrollments as e, students as s, courses as c WHERE e.st_id=s.id AND e.co_id=c.id AND e.co_id=?";
    //private final String getAssignmentById = "SELECT * FROM assignments WHERE id=?";
    private final String getStudentsWithMultipleCourses = "SELECT s.id, count(st_id) as Enrollments FROM enrollments as e, students as s WHERE e.st_id=s.id GROUP BY s.id HAVING Enrollments > 1";
    private final String getAvailableStudentsForCourse = "SELECT id FROM students WHERE NOT EXISTS (SELECT * FROM enrollments, courses WHERE enrollments.st_id = students.id AND enrollments.co_id=?)";
    private final String checkIfStudentISAvailable = "SELECT id FROM students WHERE NOT EXISTS (SELECT * FROM enrollments, courses WHERE enrollments.st_id = students.id AND enrollments.co_id=?) HAVING id=?";
    private final String getStudentsEnrolledToCourse = "SELECT * FROM students WHERE EXISTS (SELECT * FROM enrollments, courses WHERE enrollments.st_id = students.id AND enrollments.co_id=?)";
    
    protected Connection getConnection() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(EnrollmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private void closeConnections(ResultSet rs, Statement st) {
        try {
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(EnrollmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /* ----------------------------------------------------------------------------
CREATE METHODS
---------------------------------------------------------------------------- */
    public boolean addStudentToCourse(int co_id, int tr_id) {
        boolean insertOK = false;
        try {
            PreparedStatement pst = getConnection().prepareStatement(insertStudentToCourse);
            pst.setInt(1, co_id);
            pst.setInt(2, tr_id);
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Student added successfully to the Course.");
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
    public List<Student> getStudentsPerCourse(int id) {
        List<Student> list = new ArrayList();
        StudentDao sdao = new StudentDao();

        try {
            PreparedStatement pst = getConnection().prepareStatement(getStudentsPerCourse);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Student a = sdao.getStudentById(rs.getInt(1));
                list.add(a);
            }
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Sorry, but something wrong happend. ***");
        }
        return list;
    }

    public List<Student> getStudentsWithMultipleEnrollments() {
        List<Student> list = new ArrayList();
        StudentDao sdao = new StudentDao();
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(getStudentsWithMultipleCourses);
            while (rs.next()) {
                list.add(sdao.getStudentById(rs.getInt(1)));
            }
            closeConnections(rs, st);
        } catch (SQLException ex) {
            System.out.println("*** Couldn't read from database. ***");
        }
        return list;
    }

    public List<Student> getAvailableStudentsForCourse(int id) {
        List<Student> list = new ArrayList();
        StudentDao sdao = new StudentDao();
        try {
            PreparedStatement pst = getConnection().prepareStatement(getAvailableStudentsForCourse);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Student s = sdao.getStudentById(rs.getInt(1));
                list.add(s);
            }
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Sorry, but something wrong happend. ***");
        }
        return list;
    }

    public boolean checkIfStudentISavailable(int co_id, int st_id) {
        boolean checkOK = false;
        try {
            PreparedStatement pst = getConnection().prepareStatement(checkIfStudentISAvailable);
            pst.setInt(1, co_id);
            pst.setInt(2, st_id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            
            int check = rs.getInt(1);
            if (check > 0) {
                checkOK = true;
            } else {
                System.out.println("Student not available, or you entered wrong id.");
            }
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Sorry, but something wrong happend. ***");
        }
        return checkOK;
    }
    
    
    public List<Student> getStudentsEnrolledToCourse(int co_id) {
        List<Student> list = new ArrayList();
        StudentDao sdao = new StudentDao();
        try {
            PreparedStatement pst = getConnection().prepareStatement(getStudentsEnrolledToCourse);
            pst.setInt(1, co_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Student s = sdao.getStudentById(rs.getInt(1));
                list.add(s);
            }
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Sorry, but something wrong happend. ***");
        }
        return list;
    }
    
    
    
    
    
}
