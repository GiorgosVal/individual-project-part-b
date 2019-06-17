package dao;

import entities.Assignment;
import entities.Course;
import java.sql.Connection;
import java.sql.Date;
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

public class AssignmentDao extends Dao {
    
    private final String insertAssignment = "INSERT INTO assignments (atitle, adescr) VALUES (?,?)";
    private final String getAssignments = "SELECT * FROM assignments";
    private final String getAssignmentById = "SELECT * FROM assignments WHERE id=?";
    private final String deleteAssignmentById = "DELETE FROM assignments WHERE id=?";
    private final String getDateOfLastAssignment = "select max(asubDate) from assignments";
    private final String getDateOfFirstAssignment = "select min(asubDate) from assignments";

    protected Connection getConnection() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private void closeConnections(ResultSet rs, Statement st) {
        try {
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


/* ----------------------------------------------------------------------------
CREATE METHODS
---------------------------------------------------------------------------- */
    public boolean insertAssignment(Assignment s) {
        boolean insertOK = false;
        try {
            PreparedStatement pst = getConnection().prepareStatement(insertAssignment);
            pst.setString(1, s.getAtitle());
            pst.setString(2, s.getAdescr());
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Assignment inserted successfully.");
                insertOK = true;
            } else {
                System.out.println("*** Assignment not inserted. ***");
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
    public List<Assignment> getAssignments() {
        List<Assignment> list = new ArrayList();
        CourseDao cdao = new CourseDao();
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(getAssignments);
            while (rs.next()) {
                
                Course course = null;
                int courseId = rs.getInt(5);
                
                if(courseId > 0) {
                    course = cdao.getCourseById(courseId);
                }
                
                LocalDate subDate = null;
                Date date = rs.getDate(4);
                if (date!=null) {
                    subDate = date.toLocalDate();
                }
                
                Assignment c = new Assignment(rs.getInt(1), rs.getString(2), rs.getString(3), subDate, course);
                list.add(c);
            }
            closeConnections(rs, st);
        } catch (SQLException ex) {
            System.out.println("*** Couldn't read from database. ***");
        }
        return list;
    }

    public Assignment getAssignmentById(int id) {
        Assignment c = null;
        CourseDao cdao = new CourseDao();
        try {
            PreparedStatement pst = getConnection().prepareStatement(getAssignmentById);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();

            Course course = null;
            int courseId = rs.getInt(5);

            if (courseId > 0) {
                course = cdao.getCourseById(courseId);
            }

            LocalDate subDate = null;
            Date date = rs.getDate(4);
            if (date != null) {
                subDate = date.toLocalDate();
            }
            
            c = new Assignment(rs.getInt(1), rs.getString(2), rs.getString(3), subDate, course);
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Assignment not selected. You probably entered wrong id. ***");
        }
        return c;
    }
    
    
    public LocalDate getMaxDate() {
        LocalDate date = null;
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(getDateOfLastAssignment);
            rs.next();
            Date d = rs.getDate(1);
            if(d != null) {
                date = d.toLocalDate();
            }
            closeConnections(rs, st);
        } catch (SQLException ex) {
            System.out.println("*** Date not found. ***");
        }
        return date;
    }
    
    
    
    public LocalDate getMinDate() {
        LocalDate date = null;
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(getDateOfFirstAssignment);
            rs.next();
            Date d = rs.getDate(1);
            if(d != null) {
                date = d.toLocalDate();
            }
            closeConnections(rs, st);
        } catch (SQLException ex) {
            System.out.println("*** Date not found. ***");
        }
        return date;
    }
    
    
    
    
    
    
/* ----------------------------------------------------------------------------
DELETE METHODS
 ---------------------------------------------------------------------------- */    
    
    public void deleteAssignmentById (int st_id) {
        try {
            PreparedStatement pst = getConnection().prepareStatement(deleteAssignmentById);
            pst.setInt(1, st_id);

            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Assignment deleted successfully.");
                
            } else {
                System.out.println("Assignment not deleted.");
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Not deleted.");
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
