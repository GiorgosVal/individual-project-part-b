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

public class TrainerDao extends Dao {

    private final String insertTrainer = "INSERT INTO trainers (tfname, tlname, tsubject) VALUES (?,?,?)";
    private final String getTrainers = "SELECT * FROM trainers";
    private final String getTrainerById = "SELECT * FROM trainers WHERE id=?";
    
    protected Connection getConnection() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private void closeConnections(ResultSet rs, Statement st) {
        try {
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

/* ----------------------------------------------------------------------------
CREATE METHODS
 ---------------------------------------------------------------------------- */
    public boolean insertTrainer(Trainer s) {
        boolean insertOK = false;
        try {
            PreparedStatement pst = getConnection().prepareStatement(insertTrainer);
            pst.setString(1, s.getfNameReformed());
            pst.setString(2, s.getlNameReformed());
            pst.setString(3, s.getTsubject());
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Trainer inserted successfully.");
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
    public List<Trainer> getTrainers() {
        List<Trainer> list = new ArrayList();
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(getTrainers);
            while (rs.next()) {
                Trainer c = new Trainer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                list.add(c);
            }
            closeConnections(rs, st);
        } catch (SQLException ex) {
            System.out.println("*** Couldn't read from database. ***");
        }
        return list;
    }

    public Trainer getTrainerById(int id) {
        Trainer c = null;
        try {
            PreparedStatement pst = getConnection().prepareStatement(getTrainerById);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            c = new Trainer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("*** Trainer not selected. You probably entered wrong id. ***");
        }
        return c;
    }
    
    
    
    

}
