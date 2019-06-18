package entities;

import dao.TrainerDao;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import validation.Validation;

public class Trainer {
    private int id;
    private String tfname;
    private String tlname;
    private String tsubject;
    
    // GETTERS

    public int getId() {
        return id;
    }

    public String getTfname() {
        return tfname;
    }
    
    public String getfNameReformed() {
        return tfname.substring(0, 1).toUpperCase() + tfname.substring(1);
    }

    public String getTlname() {
        return tlname;
    }
    
    public String getlNameReformed() {
        return tlname.substring(0, 1).toUpperCase() + tlname.substring(1);
    }

    public String getTsubject() {
        return tsubject;
    }
    
    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setTfname(String tfname) {
        this.tfname = tfname;
    }
    
    public void setfNameWithValidation(Scanner scan) {
        boolean isNotValid = true;
        while (isNotValid) {
            String s = Validation.namesValidation(scan);
            boolean chars = Validation.maxCharactersValidation(s, 15);
            if (chars == true) {
                s = s.toLowerCase();
                this.tfname = s;
                isNotValid = false;
            }
        }
    }

    public void setTlname(String tlname) {
        this.tlname = tlname;
    }
    
    
    public void setlNameWithValidation(Scanner scan) {
        boolean isNotValid = true;
        while (isNotValid) {
            String s = Validation.namesValidation(scan);
            boolean chars = Validation.maxCharactersValidation(s, 25);
            if (chars == true) {
                s = s.toLowerCase();
                this.tlname = s;
                isNotValid = false;
            }
        }
    }
    
    

    public void setTsubject(String tsubject) {
        this.tsubject = tsubject;
    }
    
    public void setTsubjectWithValidation(Scanner scan) {
        String t = Validation.maxCharactersValidation(scan, 40);
        t = t.toLowerCase();
        this.tsubject = t;
    }
    
    // CONSTRUCTORS

    public Trainer() {
    }

    public Trainer(String tfname, String tlname, String tsubject) {
        this.tfname = tfname;
        this.tlname = tlname;
        this.tsubject = tsubject;
    }

    public Trainer(int id, String tfname, String tlname, String tsubject) {
        this.id = id;
        this.tfname = tfname;
        this.tlname = tlname;
        this.tsubject = tsubject;
    }
    
    // EQUALS

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.id;
        hash = 13 * hash + Objects.hashCode(this.tfname);
        hash = 13 * hash + Objects.hashCode(this.tlname);
        hash = 13 * hash + Objects.hashCode(this.tsubject);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trainer other = (Trainer) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.tfname, other.tfname)) {
            return false;
        }
        if (!Objects.equals(this.tlname, other.tlname)) {
            return false;
        }
        if (!Objects.equals(this.tsubject, other.tsubject)) {
            return false;
        }
        return true;
    }
    
    // TO STRING

    @Override
    public String toString() {
        return "Trainer{" + "id=" + id + ", tfname=" + tfname + ", tlname=" + tlname + ", tsubject=" + tsubject + '}';
    }
    
    
//* ------------------------------------------------------------------------- */
// Trainer Objects Creation
//* ------------------------------------------------------------------------- */
    public static void createTrainer(Scanner scan, int numOfTrainers) {
        for (int i = 0; i < numOfTrainers; i++) {
            while (true) {
                Trainer s = new Trainer();
                System.out.println("First name of trainer no." + (i + 1));
                s.setfNameWithValidation(scan);
                System.out.println("Last name of trainer no." + (i + 1));
                s.setlNameWithValidation(scan);
                System.out.println("Teaching subject of trainer no." + (i + 1));
                s.setTsubjectWithValidation(scan);
                TrainerDao sd = new TrainerDao();
                boolean insertOK = sd.insertTrainer(s);
                if (insertOK) {
                    break;
                }
            }
        }
    }
   
    
/* ------------------------------------------------------------------------- */
// Trainers Print
//* ------------------------------------------------------------------------- */    
    public static void printTrainers(List<Trainer> list) {
        if(list.isEmpty()) {
            System.out.println("*** The Trainers list is empty. ***\n\n");
        } else {
            String line = "-";
            for (int i = 0; i < 95; i++) {
                line += "-";
            }

            System.out.println();
            System.out.println(line);
            System.out.printf("%35s%s\n", " ", "T R A I N E R S   L I S T");
            System.out.println(line);
            System.out.printf("%-5s%-20s%-30s%-40s\n", "Id", "First Name", "Last Name", "Subject");
            System.out.println(line);
            for (Trainer s : list) {
                int id = s.getId();
                String fname = s.getfNameReformed();
                String lname = s.getlNameReformed();
                String tsubject = s.getTsubject();
                System.out.printf("%-5d%-20s%-30s%-40s\n", id, fname, lname, tsubject);
            }
            System.out.println(line);
            System.out.println();   
        }
        
    }


  //* ------------------------------------------------------------------------- */
// Trainers Delete
//* ------------------------------------------------------------------------- */       
    public static void deleteTrainers(Scanner scan) {
        TrainerDao cdao = new TrainerDao();
        boolean courseNeeded = true;
        while (courseNeeded) {
            printTrainers(cdao.getTrainers());
            System.out.println("Select the Trainer you want to delete by its id, "
                    + "or write 'C<' to return to the previous menu.");
            int choise = Validation.positiveIntValidationWithReturn(scan);
            if(choise == -1) {
                courseNeeded = false;
            } else {
                Trainer s = cdao.getTrainerById(choise);
                if(s != null) {
                    cdao.deleteTrainerById(s.getId());
                }
            }
        }

    }   


    
}
