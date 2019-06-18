package entities;

import dao.StudentDao;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import validation.Validation;

public class Student {

    private int id;
    private String fName;
    private String lName;
    private LocalDate dob;
    private double fees;

    // GETTERS
    public int getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public String getfNameReformed() {
        return fName.substring(0, 1).toUpperCase() + fName.substring(1);
    }

    public String getlName() {
        return lName;
    }

    public String getlNameReformed() {
        return lName.substring(0, 1).toUpperCase() + lName.substring(1);
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getDobSQLString() {
        String date = Validation.LocalDate_to_String_for_SQL(dob);
        return date;
    }

    public double getFees() {
        return fees;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setfNameWithValidation(Scanner scan) {
        boolean isNotValid = true;
        while (isNotValid) {
            String s = Validation.namesValidation(scan);
            boolean chars = Validation.maxCharactersValidation(s, 15);
            if (chars == true) {
                s = s.toLowerCase();
                this.fName = s;
                isNotValid = false;
            }
        }
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setlNameWithValidation(Scanner scan) {
        boolean isNotValid = true;
        while (isNotValid) {
            String s = Validation.namesValidation(scan);
            boolean chars = Validation.maxCharactersValidation(s, 25);
            if (chars == true) {
                s = s.toLowerCase();
                this.lName = s;
                isNotValid = false;
            }
        }
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setDobWithValidation(Scanner scan) {
        boolean isNotValid = true;
        while (isNotValid) {
            LocalDate date = Validation.dateValidation(scan);
            LocalDate sixYearsAgo = LocalDate.now().minusYears(6);
            LocalDate min = LocalDate.now().minusYears(100);
            if (date.isAfter(min) && date.isBefore(sixYearsAgo)) {
                this.dob = date;
                isNotValid = false;
            } else {
                String from = Validation.LocalDate_to_String(min);
                String to = Validation.LocalDate_to_String(sixYearsAgo);
                System.out.println("Students can't be less than 6 years old or "
                        + "more than 100 years old.\nYou must enter a date between " + from + " and " + to);
                System.out.println();
            }
        }
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public void setFeesWithValidation(Scanner scan) {
        boolean isNotValid = true;
        while (isNotValid) {
            int fees = Validation.positiveIntValidationZeroBased(scan);
            if (fees > 99999) {
                System.out.println("Fees cannot exceed €99,999.00");
                System.out.println();
            } else {
                this.fees = fees;
                isNotValid = false;
            }
        }
    }

    // CONSTRUCTORS
    private Student() {
    }

    public Student(String fName, String lName, LocalDate dob, double fees) {
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
        this.fees = fees;
    }

    public Student(int id, String fName, String lName, LocalDate dob, double fees) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
        this.fees = fees;
    }

    // EQUALS
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.fName);
        hash = 29 * hash + Objects.hashCode(this.lName);
        hash = 29 * hash + Objects.hashCode(this.dob);
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
        final Student other = (Student) obj;
        if (!Objects.equals(this.fName, other.fName)) {
            return false;
        }
        if (!Objects.equals(this.lName, other.lName)) {
            return false;
        }
        if (!Objects.equals(this.dob, other.dob)) {
            return false;
        }
        return true;
    }

    // TO STRING
    @Override
    public String toString() {
        return "Student{" + "fName=" + fName + ", lName=" + lName + ", dob=" + dob + ", fees=" + fees + '}';
    }

//* ------------------------------------------------------------------------- */
// Student Objects Creation
//* ------------------------------------------------------------------------- */
    public static void createStudent(Scanner scan, int numOfStudents) {
        for (int i = 0; i < numOfStudents; i++) {
            while (true) {
                Student s = new Student();
                System.out.println("First name of student no." + (i + 1));
                s.setfNameWithValidation(scan);
                System.out.println("Last name of student no." + (i + 1));
                s.setlNameWithValidation(scan);
                System.out.println("Date of birth of student no." + (i + 1));
                s.setDobWithValidation(scan);
                System.out.println("Tuition fees of student no." + (i + 1));
                s.setFeesWithValidation(scan);
                StudentDao sd = new StudentDao();
                boolean insertOK = sd.insertStudent(s);
                if (insertOK) {
                    break;
                }
            }
        }
    }

//* ------------------------------------------------------------------------- */
// Students Print
//* ------------------------------------------------------------------------- */    
    public static void printStudents(List<Student> list) {
        if(list.isEmpty()) {
            System.out.println("*** The Students list is empty. ***\n\n");
        } else {
            String line = "-";
            for (int i = 0; i < 75; i++) {
                line += "-";
            }

            System.out.println();
            System.out.println(line);
            System.out.printf("%25s%s\n", " ", "S T U D E N T S   L I S T");
            System.out.println(line);
            System.out.printf("%-5s%-20s%-25s%15s%10s\n", "Id", "First Name", "Last Name", "Date of Birth", "Fees");
            System.out.println(line);
            for (Student s : list) {
                int id = s.getId();
                String fname = s.getfNameReformed();
                String lname = s.getlNameReformed();
                String sdob = Validation.LocalDate_to_String(s.getDob());
                double sfees = s.getFees();
                System.out.printf("%-5d%-20s%-25s%15s%10.2f€\n", id, fname, lname, sdob, sfees);
            }
            System.out.println(line);
            System.out.println(); 
        }
        
    }

//* ------------------------------------------------------------------------- */
// Students Delete
//* ------------------------------------------------------------------------- */       
    public static void deleteStudents(Scanner scan) {
        StudentDao sdao = new StudentDao();
        boolean studentNeeded = true;
        while (studentNeeded) {
            printStudents(sdao.getStudents());
            System.out.println("Select the Student you want to delete by his id, "
                    + "or write 'C<' to return to the previous menu.");
            int choise = Validation.positiveIntValidationWithReturn(scan);
            if(choise == -1) {
                studentNeeded = false;
            } else {
                Student s = sdao.getStudentById(choise);
                if(s != null) {
                    sdao.deleteStudentById(s.getId());
                }
            }
        }

    }

}
