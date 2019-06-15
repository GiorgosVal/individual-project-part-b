package dao;

import entities.Assignment;
import entities.Homework;
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

public class HomeworkDao extends Dao {
    //private final String insertAssignment = "INSERT INTO assignments (atitle, adescr) VALUES (?,?)";
    private final String getHomeworkPerCoursePerStudent = "SELECT h.id, h.somark, h.stmark, h.st_id, h.as_id FROM students as s, homeworks as h, assignments as a, courses as c WHERE s.id=h.st_id AND h.as_id=a.id AND a.co_id=c.id AND h.st_id=?";
    //private final String getAssignmentById = "SELECT * FROM assignments WHERE id=?";
    private final String insertAssignmentToStudent = "INSERT INTO homeworks(st_id, as_id) VALUES(?,?)";
    private final String getStudentsWithHomeworks = "SELECT e.st_id FROM enrollments AS e, students AS s WHERE e.st_id=s.id GROUP BY e.st_id";
    private final String getStudentWithHomeworkById = "SELECT e.st_id FROM enrollments AS e, students AS s WHERE e.st_id=s.id GROUP BY e.st_id HAVING st_id=?";
    private final String getHomeworkById = "SELECT * FROM homeworks WHERE id=?";
    private final String updateHomeworkMark = "UPDATE homeworks SET somark=?, stmark=? WHERE id=?";
    
    protected Connection getConnection() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(HomeworkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private void closeConnections(ResultSet rs, Statement st) {
        try {
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(HomeworkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
/* ----------------------------------------------------------------------------
READ METHODS
 ---------------------------------------------------------------------------- */
    
    
    public List<Homework> getHomeworkPerCoursePerStudent(int id) {
        List<Homework> list = new ArrayList();
        StudentDao sdao = new StudentDao();
        AssignmentDao adao = new AssignmentDao();
        try {
            PreparedStatement pst = getConnection().prepareStatement(getHomeworkPerCoursePerStudent);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                int homeworkId = rs.getInt(1);
                int omark = rs.getInt(2);
                int tmark = rs.getInt(3);
                Student student = sdao.getStudentById(rs.getInt(4));
                Assignment assignment = adao.getAssignmentById(rs.getInt(5));
                Homework h = new Homework(homeworkId, omark, tmark, student, assignment);
                list.add(h);
            }
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Sorry, but something wrong happend. ***");
        }
        return list;
    }
    

    public List<Student> getStudentsWithAssignments() {
        List<Student> list = new ArrayList();
        StudentDao sdao = new StudentDao();
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(getStudentsWithHomeworks);
            while (rs.next()) {
                
                Student student = null;
                int st_id = rs.getInt(1);
                
                if(st_id > 0) {
                    student = sdao.getStudentById(st_id);
                    list.add(student);
                }
            }
            closeConnections(rs, st);
        } catch (SQLException ex) {
            System.out.println("*** Couldn't read from database. ***");
        }
        return list;
    }
    
    
    public Student getStudentWithHomeworkById(int id) {
        StudentDao sdao = new StudentDao();
        Student student = null;
        try {
            PreparedStatement pst = getConnection().prepareStatement(getStudentWithHomeworkById);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int st_id = rs.getInt(1);
            if (st_id > 0) {
                student = sdao.getStudentById(st_id);
            } else {
                System.out.println("Student not selected. You probably entered wrong id.");
            }
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Sorry, but something wrong happend. ***");
        }
        return student;
    }

    
    public Homework getHomeworkById(int homework_id) {
        StudentDao sdao = new StudentDao();
        AssignmentDao adao = new AssignmentDao();
        boolean updateOK = false;
        Homework h = null;
        try {
            PreparedStatement pst = getConnection().prepareStatement(getHomeworkById);
            pst.setInt(1, homework_id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int h_id = rs.getInt(1);
            int omark = rs.getInt(2);
            int tmark = rs.getInt(3);
            Student student = sdao.getStudentById(rs.getInt(4));
            Assignment assignment = adao.getAssignmentById(rs.getInt(5));
            
            if (h_id > 0) {
                h = new Homework(h_id, omark, tmark, student, assignment);
            } else {
                System.out.println("Assignment not selected. You probably entered wrong id.");
            }
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Sorry, but something wrong happend. ***");
        }
        return h;
    }
    
    
    
    
       
    
/* ----------------------------------------------------------------------------
CREATE METHODS
---------------------------------------------------------------------------- */
    public boolean addAssignmentsToStudent(int st_id, int as_id) {
        boolean insertOK = false;
        try {
            PreparedStatement pst = getConnection().prepareStatement(insertAssignmentToStudent);
            pst.setInt(1, st_id);
            pst.setInt(2, as_id);
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Assignment added successfully to the Student.");
                insertOK = true;
            } else {
                AssignmentDao adao = new AssignmentDao();
                String atitle = adao.getAssignmentById(as_id).getAtitle();
                System.out.println("*** Assignment '" + atitle + "' not inserted. ***");
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
UPDATE METHODS
---------------------------------------------------------------------------- */
    public void updateHomeworkMarks(Homework homework) {
        try {
            PreparedStatement pst = getConnection().prepareStatement(updateHomeworkMark);
            pst.setInt(1, homework.getSomark());
            pst.setInt(2, homework.getStmark());
            pst.setInt(3, homework.getId());
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Assignment marks of Student updated successfully.");
            } else {
                System.out.println("Assignment marks not updated.");
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {

            System.out.println("Not inserted.");
        }
    }
    
    
    
    
    
    
    
    
}
