/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Trainer;
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

/**
 *
 * @author giorgos
 */
public class PersonnelDao extends Dao {
    private final String insertTrainerToCourse = "INSERT INTO personnel (co_id, tr_id) VALUES(?,?)";
    private final String getTrainersPerCourse = "SELECT p.tr_id FROM personnel as p, trainers as t, courses as c WHERE p.co_id=c.id AND p.tr_id=t.id AND p.co_id=?";
    //private final String getAssignmentById = "SELECT * FROM assignments WHERE id=?";
    private final String getAvailableTrainerForCourse = "SELECT id FROM trainers WHERE NOT EXISTS (SELECT * FROM personnel, courses WHERE personnel.tr_id = trainers.id AND personnel.co_id=?)";

    protected Connection getConnection() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(PersonnelDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private void closeConnections(ResultSet rs, Statement st) {
        try {
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonnelDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
/* ----------------------------------------------------------------------------
CREATE METHODS
---------------------------------------------------------------------------- */
    public boolean addTrainerToCourse(int co_id, int tr_id) {
        boolean insertOK = false;
        try {
            PreparedStatement pst = getConnection().prepareStatement(insertTrainerToCourse);
            pst.setInt(1, co_id);
            pst.setInt(2, tr_id);
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Trainer added successfully to the Course.");
                insertOK = true;
            } else {
                System.out.println("*** Trainer not inserted. ***");
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
    
    
    public List<Trainer> getTrainersPerCourse(int id) {
        List<Trainer> list = new ArrayList();
        TrainerDao tdao = new TrainerDao();

        try {
            PreparedStatement pst = getConnection().prepareStatement(getTrainersPerCourse);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                Trainer a = tdao.getTrainerById(rs.getInt(1));
                list.add(a);
            }
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Sorry, but something wrong happend. ***");
        }
        return list;
    }
    
    
    public List<Trainer> getAvailableTrainersForCourse(int id) {
        List<Trainer> list = new ArrayList();
        TrainerDao tdao = new TrainerDao();

        try {
            PreparedStatement pst = getConnection().prepareStatement(getAvailableTrainerForCourse);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Trainer a = tdao.getTrainerById(rs.getInt(1));
                list.add(a);
            }
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Sorry, but something wrong happend. ***");
        }
        return list;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
