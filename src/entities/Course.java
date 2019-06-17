package entities;

import dao.CourseDao;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import validation.Validation;

public class Course {
    private int id;
    private String ctitle;
    private String cstream;
    private String ctype;
    private LocalDate cstart;
    private LocalDate cend;
    
    
    // GETTERS

    public int getId() {
        return id;
    }

    public String getCtitle() {
        return ctitle;
    }

    public String getCstream() {
        return cstream;
    }

    public String getCtype() {
        return ctype;
    }

    public LocalDate getCstart() {
        return cstart;
    }
    
    public String getCstartSQLString() {
        String date = Validation.LocalDate_to_String_for_SQL(cstart);
        return date;
    }

    public LocalDate getCend() {
        return cend;
    }
    
    public String getCendSQLString() {
        String date = Validation.LocalDate_to_String_for_SQL(cend);
        return date;
    }
    
    
    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setCtitle(String ctitle) {
        this.ctitle = ctitle;
    }
    
    public void setCtitleWithValidation(Scanner scan) {
        String t = Validation.maxCharactersValidation(scan, 25);
        t = t.toLowerCase();
        this.ctitle = t;
    }

    public void setCstream(String cstream) {
        this.cstream = cstream;
    }
    
    public void setCstreamWithValidation(Scanner scan) {
        String t = Validation.maxCharactersValidation(scan, 20);
        t = t.toLowerCase();
        this.cstream = t;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }
    
    public void setCtypeWithValidation(Scanner scan) {
        String t = Validation.maxCharactersValidation(scan, 30);
        t = t.toLowerCase();
        this.ctype = t;
    }

    public void setCstart(LocalDate cstart) {
        this.cstart = cstart;
    }
    
    public void setCstartWithValidation(Scanner scan) {
        boolean isNotValid = true;
        while (isNotValid) {
            LocalDate date = Validation.dateValidation(scan);
            LocalDate min = LocalDate.now().minusYears(5);
            if (date.isAfter(min) || date.isEqual(min)) {
                this.cstart = date;
                isNotValid = false;
            } else {
                System.out.println("The starting date cannot be more than 5 years ago.");
                System.out.println();
            }
        }
    }

    public void setCend(LocalDate cend) {
        this.cend = cend;
    }
    
    public void setCendWithValidation(Scanner scan) {
        boolean isNotValid = true;
        while (isNotValid) {
            LocalDate end = Validation.dateValidation(scan);
            LocalDate start = this.cstart;
            if (end.isAfter(start)) {
                isNotValid = false;
                this.cend = end;
            } else {
                System.out.println("The ending date must be later than the starting date.");
                System.out.println();
            }
        }
    }
    
    // CONSTRUCTORS

    public Course() {
    }

    public Course(String ctitle, String cstream, String ctype, LocalDate cstart, LocalDate cend) {
        this.ctitle = ctitle;
        this.cstream = cstream;
        this.ctype = ctype;
        this.cstart = cstart;
        this.cend = cend;
    }

    public Course(int id, String ctitle, String cstream, String ctype, LocalDate cstart, LocalDate cend) {
        this.id = id;
        this.ctitle = ctitle;
        this.cstream = cstream;
        this.ctype = ctype;
        this.cstart = cstart;
        this.cend = cend;
    }
    
    // EQUALS

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.id;
        hash = 73 * hash + Objects.hashCode(this.ctitle);
        hash = 73 * hash + Objects.hashCode(this.cstream);
        hash = 73 * hash + Objects.hashCode(this.ctype);
        hash = 73 * hash + Objects.hashCode(this.cstart);
        hash = 73 * hash + Objects.hashCode(this.cend);
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
        final Course other = (Course) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.ctitle, other.ctitle)) {
            return false;
        }
        if (!Objects.equals(this.cstream, other.cstream)) {
            return false;
        }
        if (!Objects.equals(this.ctype, other.ctype)) {
            return false;
        }
        if (!Objects.equals(this.cstart, other.cstart)) {
            return false;
        }
        if (!Objects.equals(this.cend, other.cend)) {
            return false;
        }
        return true;
    }
    
    // TO STRING

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", ctitle=" + ctitle + ", cstream=" + cstream + ", ctype=" + ctype + ", cstart=" + cstart + ", cend=" + cend + '}';
    }
    
//* ------------------------------------------------------------------------- */
// Course Objects Creation
//* ------------------------------------------------------------------------- */
    public static void createCourse(Scanner scan, int numOfCourses) {
        for (int i = 0; i < numOfCourses; i++) {
            while (true) {
                Course s = new Course();
                System.out.println("Enter the title of Course no." + (i + 1));
                s.setCtitleWithValidation(scan);
                System.out.println("Enter the stream of Course no." + (i + 1));
                s.setCstreamWithValidation(scan);
                System.out.println("Enter the type of Course no." + (i + 1));
                s.setCtypeWithValidation(scan);
                System.out.println("Enter the starting date of Course no." + (i + 1));
                s.setCstartWithValidation(scan);
                System.out.println("Enter the ending date of Course no." + (i + 1));
                s.setCendWithValidation(scan);
                
                CourseDao sd = new CourseDao();
                boolean insertOK = sd.insertCourse(s);
                if (insertOK) {
                    break;
                }
            }

        }
    }

//* ------------------------------------------------------------------------- */
// Courses Print
//* ------------------------------------------------------------------------- */        
    
    public static void printCourses(List<Course> list) {
        String line = "-";
        for (int i = 0; i < 86; i++) {
            line += "-";
        }

        System.out.println();
        System.out.println(line);
        System.out.printf("%31s%s\n", " ", "C O U R S E S   L I S T");
        System.out.println(line);
        System.out.printf("%-5s%-25s%-15s%-15s%13s%13s\n", "Id", "Title", "Stream", "Type", "Start Date", "End Date");
        System.out.println(line);
        for (Course s : list) {
            int id = s.getId();
            String ctitle = s.getCtitle();
            String cstream = s.getCstream();
            String ctype = s.getCtype();
            String cstart = Validation.LocalDate_to_String(s.getCstart());
            String cend = Validation.LocalDate_to_String(s.getCend());
            System.out.printf("%-5s%-25s%-15s%-15s%13s%13s\n", id, ctitle, cstream, ctype, cstart, cend);
        }
        System.out.println(line);
        System.out.println();
    }
    
    
//* ------------------------------------------------------------------------- */
// Courses Delete
//* ------------------------------------------------------------------------- */       
    public static void deleteCourses(Scanner scan) {
        CourseDao cdao = new CourseDao();
        boolean courseNeeded = true;
        while (courseNeeded) {
            printCourses(cdao.getCourses());
            System.out.println("Select the Course you want to delete by its id, "
                    + "or write 'C<' to return to the previous menu.");
            int choise = Validation.positiveIntValidationWithReturn(scan);
            if(choise == -1) {
                courseNeeded = false;
            } else {
                Course s = cdao.getCourseById(choise);
                if(s != null) {
                    cdao.deleteCourseById(s.getId());
                }
            }
        }

    }    
    
    
    
    
    
    
    
}
