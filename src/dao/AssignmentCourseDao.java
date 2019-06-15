package dao;

import entities.Assignment;
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
import validation.Validation;

public class AssignmentCourseDao extends Dao {

    //private final String insertAssignment = "INSERT INTO assignments (atitle, adescr) VALUES (?,?)";
    private final String getAssignmentsPerCourse = "SELECT a.id FROM assignments as a, courses as c WHERE a.co_id=c.id AND c.id=?";
    //private final String getAssignmentById = "SELECT * FROM assignments WHERE id=?";
    private final String getNumOfAssignmentsOfCourse =  "SELECT count(*) FROM assignments AS a, courses AS c WHERE a.co_id=c.id AND a.co_id=?";
    private final String getAvailableAssignments = "SELECT * FROM assignments WHERE assignments.co_id IS NULL";
    private final String getAvailableAssignmentById = "SELECT * FROM assignments WHERE assignments.co_id IS NULL AND id=?";
    private final String relateAssignmentToCourse = "UPDATE assignments SET asubDate=?, co_id=? WHERE id=?";
    
    protected Connection getConnection() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentCourseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private void closeConnections(ResultSet rs, Statement st) {
        try {
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentCourseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

/* ----------------------------------------------------------------------------
READ METHODS
 ---------------------------------------------------------------------------- */
    
    
    public List<Assignment> getAssignmentsPerCourse(int id) {
        List<Assignment> list = new ArrayList();
        AssignmentDao adao = new AssignmentDao();

        try {
            PreparedStatement pst = getConnection().prepareStatement(getAssignmentsPerCourse);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                Assignment a = adao.getAssignmentById(rs.getInt(1));
                list.add(a);
            }
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Sorry, but something wrong happend. ***");
        }
        return list;
    }
    
    public int getNumOfAssignmentsOfCourse(int id) {
        int num = -1;
        try {
            PreparedStatement pst = getConnection().prepareStatement(getNumOfAssignmentsOfCourse);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            num = rs.getInt(1);
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Sorry, but something wrong happend. ***");
        }
        return num;
    }
    
    public List<Assignment> getAvailableAssignments() {
        List<Assignment> list = new ArrayList();
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(getAvailableAssignments);
            while(rs.next()) {
                Assignment a = new Assignment();
                a.setId(rs.getInt(1));
                a.setAtitle(rs.getString(2));
                a.setAdescr(rs.getString(3));
                list.add(a);
            }
        } catch (SQLException ex) {
            System.out.println("*** Sorry, but something wrong happend. ***");
        }
        
        return list;
    }
    
    public Assignment getAvailableAssignmentById(int id) {
        Assignment a = null;
        try {
            PreparedStatement pst = getConnection().prepareStatement(getAvailableAssignmentById);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                a = new Assignment();
                a.setId(rs.getInt(1));
                a.setAtitle(rs.getString(2));
                a.setAdescr(rs.getString(3));
            } else {
                System.out.println("No Assignment found with this id.");
            }
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Sorry, but something wrong happend. ***");
        }
        return a;
    }
    
/* ----------------------------------------------------------------------------
UPDATE METHODS
 ---------------------------------------------------------------------------- */
    public boolean relateAssignmentToCourse(Assignment assignment) {
        boolean insertOK = false;
        try {
            PreparedStatement pst = getConnection().prepareStatement(relateAssignmentToCourse);
            
            String date = Validation.LocalDate_to_String_for_SQL(assignment.getAsubDate());
            
            pst.setString(1, date);
            pst.setInt(2, assignment.getAcourse().getId());
            pst.setInt(3, assignment.getId());
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Assignment added successfully to the Course.");
                insertOK = true;
            } else {
                System.out.println("Assignment not added to the Course.");
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {

            System.out.println("Not inserted.");
        }
        return insertOK;
    }  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}




