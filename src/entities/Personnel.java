package entities;

import dao.CourseDao;
import dao.EnrollmentDao;
import dao.PersonnelDao;
import dao.TrainerDao;
import java.util.List;
import java.util.Scanner;
import validation.Validation;

public class Personnel {
    private int id;
    private int co_id;
    private int tr_id;

    // GETTERS
    public int getId() {
        return id;
    }

    public int getCo_id() {
        return co_id;
    }

    public int getTr_id() {
        return tr_id;
    }
    
    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setCo_id(int co_id) {
        this.co_id = co_id;
    }

    public void setTr_id(int tr_id) {
        this.tr_id = tr_id;
    }
    
    // CONSTRUCTORS

    public Personnel() {
    }

    public Personnel(int id, int co_id, int tr_id) {
        this.id = id;
        this.co_id = co_id;
        this.tr_id = tr_id;
    }
    
    // EQUALS

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.id;
        hash = 43 * hash + this.co_id;
        hash = 43 * hash + this.tr_id;
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
        final Personnel other = (Personnel) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.co_id != other.co_id) {
            return false;
        }
        if (this.tr_id != other.tr_id) {
            return false;
        }
        return true;
    }
    
    // TO STRING

    @Override
    public String toString() {
        return "Personnel{" + "id=" + id + ", co_id=" + co_id + ", tr_id=" + tr_id + '}';
    }
    
    
/* ------------------------------------------------------------------------- */
// Trainers Per Course Print
//* ------------------------------------------------------------------------- */        
    public static void printTrainersPerCourse(Scanner scan) {
        CourseDao cdao = new CourseDao();
        boolean courseNeeded = true;
        while (courseNeeded) {
            Course.printCourses(cdao.getCourses());
            System.out.println("Select the id of the Course.");
            System.out.println("Write 'C<' if you want to return to the previous menu.");
            int choise = Validation.positiveIntValidationWithReturn(scan);
            if (choise == -1) {
                courseNeeded = false;
            } else {
                Course c = cdao.getCourseById(choise);
                if (c != null) {
                    System.out.println("You chose the Course '" + c.getCtitle() + "'.");
                    PersonnelDao pdao = new PersonnelDao();
                    List<Trainer> trainers = pdao.getTrainersPerCourse(c.getId());
                    System.out.println("\n\nCOURSE " + c.getCtitle());
                    Trainer.printTrainers(trainers);
                    System.out.println("Press 1 to repeat or 'C<' to return the previous menu.");
                    choise = Validation.maxValidationWithReturn(1, scan);
                    if(choise== -1) {
                        courseNeeded = false;
                    }
                }
            }
        }

    }
    
    
    
/* ------------------------------------------------------------------------- */
// Relate Trainer To Course
//* ------------------------------------------------------------------------- */        
    public static void addTrainerToCourse(Scanner scan) {
        CourseDao cdao = new CourseDao();
        TrainerDao tdao = new TrainerDao();
        boolean courseNeeded = true;
        while (courseNeeded) {
            Course.printCourses(cdao.getCourses());
            System.out.println("Select the id of the Course.");
            System.out.println("Write 'C<' if you want to return to the previous menu.");
            int choise = Validation.positiveIntValidationWithReturn(scan);
            if (choise == -1) {
                courseNeeded = false;
            } else {
                Course c = cdao.getCourseById(choise);
                if (c != null) {
                    System.out.println("You chose the Course '" + c.getCtitle() + "'.");
                    PersonnelDao pdao = new PersonnelDao();
                    List<Trainer> trainers = pdao.getAvailableTrainersForCourse(c.getId());
                    
                    boolean trainerNeeded = true;
                    while(trainerNeeded) {
                        System.out.println("\n\nCOURSE " + c.getCtitle() + " available trainers.");
                        Trainer.printTrainers(trainers);
                        System.out.println("Choose the id of Trainer you want to add to the Course, or write 'C<' to return to the previous menu.");
                        choise = Validation.positiveIntValidationWithReturn(scan);
                        if(choise == -1) {
                            trainerNeeded = false;
                        } else {
                            Trainer t = tdao.getTrainerById(choise);
                            if(t != null) {
                                System.out.println("You chose the Trainer " + t.getfNameReformed() + " " + t.getlNameReformed() + ".");
                                boolean insertOK = pdao.addTrainerToCourse(c.getId(), t.getId());
                                if(insertOK) {
                                    trainerNeeded = false;
                                }
                            }
                        }
                    }                    
                }
            }
        }

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
