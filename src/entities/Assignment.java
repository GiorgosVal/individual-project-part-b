package entities;

import dao.AssignmentDao;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import validation.Validation;

public class Assignment {
    private int id;
    private String atitle;
    private String adescr;
    private LocalDate asubDate;
    private Course acourse;
    
    
    // GETTERS

    public int getId() {
        return id;
    }

    public String getAtitle() {
        return atitle;
    }

    public String getAdescr() {
        return adescr;
    }

    public LocalDate getAsubDate() {
        return asubDate;
    }

    public Course getAcourse() {
        return acourse;
    }
    
    public String getAcourseTitle() {
        String title = "No related course";
        if(acourse!=null) {
            title = acourse.getCtitle();
        }
        return title;
    }
    
    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setAtitle(String atitle) {
        this.atitle = atitle;
    }
    
    public void setAtitleWithValidation(Scanner scan) {
        String t = Validation.maxCharactersValidation(scan, 50);
        t = t.toLowerCase();
        this.atitle = t;
    }

    public void setAdescr(String adescr) {
        this.adescr = adescr;
    }
    
    public void setAdescrWithValidation(Scanner scan) {
        String t = Validation.maxCharactersValidation(scan, 70);
        t = t.toLowerCase();
        this.adescr = t;
    }

    public void setAsubDate(LocalDate asubDate) {
        this.asubDate = asubDate;
    }
    
    public void setAsubDateWithValidation(Scanner scan, LocalDate start_date, LocalDate end_date) {
        String start = Validation.LocalDate_to_String(start_date);
        String end = Validation.LocalDate_to_String(end_date);
        System.out.println("Set the submission date to be after " + start + " up until to " + end);
        System.out.println("The assignments submissions take place Monday-Friday.");
        System.out.println();
        boolean isNotValid = true;

        while (isNotValid) {
            LocalDate subDate = Validation.dateValidation(scan);
            if (subDate.isAfter(start_date) && (subDate.isBefore(end_date) || subDate.isEqual(end_date))) {
                int x = subDate.getDayOfWeek().getValue();
                if (x == 6 || x == 7) {
                    System.out.println("The date you entered is Weekend.");
                    System.out.println("The assignments submissions take place Monday-Friday.");
                    System.out.println();
                } else {
                    this.asubDate = subDate;
                    System.out.println("Date successfully registered.");
                    System.out.println();
                    isNotValid = false;
                }

            } else {
                System.out.println("The date must be after " + start + " up until to " + end);
            }
        }
    }

    public void setAcourse(Course acourse) {
        this.acourse = acourse;
    }
    
    // CONSTRUCTORS

    public Assignment() {
    }

    public Assignment(String atitle, String adescr, LocalDate asubDate, Course acourse) {
        this.atitle = atitle;
        this.adescr = adescr;
        this.asubDate = asubDate;
        this.acourse = acourse;
    }

    public Assignment(int id, String atitle, String adescr, LocalDate asubDate, Course acourse) {
        this.id = id;
        this.atitle = atitle;
        this.adescr = adescr;
        this.asubDate = asubDate;
        this.acourse = acourse;
    }
    
    // EQUALS

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.atitle);
        hash = 37 * hash + Objects.hashCode(this.adescr);
        hash = 37 * hash + Objects.hashCode(this.asubDate);
        hash = 37 * hash + Objects.hashCode(this.acourse);
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
        final Assignment other = (Assignment) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.atitle, other.atitle)) {
            return false;
        }
        if (!Objects.equals(this.adescr, other.adescr)) {
            return false;
        }
        if (!Objects.equals(this.asubDate, other.asubDate)) {
            return false;
        }
        if (!Objects.equals(this.acourse, other.acourse)) {
            return false;
        }
        return true;
    }
    
    // TO STRING

    @Override
    public String toString() {
        return "Assignment{" + "id=" + id + ", atitle=" + atitle + ", adescr=" + adescr + ", asubDate=" + asubDate + ", acourse=" + acourse + '}';
    }
    
//* ------------------------------------------------------------------------- */
// Assignmen Objects Creation
//* ------------------------------------------------------------------------- */
    public static void createAssignment(Scanner scan, int numOfAssignments) {
        for (int i = 0; i < numOfAssignments; i++) {
            while (true) {
                Assignment s = new Assignment();
                System.out.println("Title of Assignment no." + (i + 1));
                s.setAtitleWithValidation(scan);
                System.out.println("Description of Assignment no." + (i + 1));
                s.setAdescrWithValidation(scan);
                AssignmentDao sd = new AssignmentDao();
                boolean insertOK = sd.insertAssignment(s);
                if (insertOK) {
                    break;
                }
            }
        }
    }
    

//* ------------------------------------------------------------------------- */
// Assignments Print
//* ------------------------------------------------------------------------- */        
    
    public static void printAssignments(List<Assignment> list) {
        if(list.isEmpty()) {
            System.out.println("*** The Assignments list is empty. ***\n\n");
        } else {
            String line = "-";
            for (int i = 0; i < 182; i++) {
                line += "-";
            }

            System.out.println();
            System.out.println(line);
            System.out.printf("%75s%s\n", " ", "A S S I G N M E N T S   L I S T");
            System.out.println(line);
            System.out.printf("%-5s%-50s%-70s%-17s%-40s\n", "Id", "Title", "Description", "Submission Date", "Related Course");
            System.out.println(line);
            for (Assignment s : list) {
                int id = s.getId();
                String title = s.getAtitle();
                String descr = s.getAdescr();
                String date = Validation.LocalDate_to_String(s.getAsubDate());
                String course = s.getAcourseTitle();
                System.out.printf("%-5s%-50s%-70s%-17s%-40s\n", id, title, descr, date, course);
            }
            System.out.println(line);
            System.out.println(); 
        }
        
    }
   
   
    
  //* ------------------------------------------------------------------------- */
// Assignments Delete
//* ------------------------------------------------------------------------- */       
    public static void deleteAssignments(Scanner scan) {
        AssignmentDao cdao = new AssignmentDao();
        boolean courseNeeded = true;
        while (courseNeeded) {
            printAssignments(cdao.getAssignments());
            System.out.println("Select the Assignment you want to delete by its id, "
                    + "or write 'C<' to return to the previous menu.");
            int choise = Validation.positiveIntValidationWithReturn(scan);
            if(choise == -1) {
                courseNeeded = false;
            } else {
                Assignment s = cdao.getAssignmentById(choise);
                if(s != null) {
                    cdao.deleteAssignmentById(s.getId());
                }
            }
        }

    }   
    
    
    
    
    
    
    
    
    
    
}
