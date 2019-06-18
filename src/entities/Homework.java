package entities;

import dao.AssignmentDao;
import dao.HomeworkDao;
import dao.StudentDao;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import validation.Validation;

public class Homework {

    private int id;
    private int somark;
    private int stmark;
    private Student student;
    private Assignment assignment;

    // GETTER
    public int getId() {
        return id;
    }

    public int getSomark() {
        return somark;
    }

    public int getStmark() {
        return stmark;
    }

    public Student getStudent() {
        return student;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    // SETTER
    public void setId(int id) {
        this.id = id;
    }

    public void setSomark(int somark) {
        this.somark = somark;
    }

    public void setOralMarkWithValidation(Scanner scan) {
        boolean isNotValid = true;
        int mark = 0;
        while (isNotValid) {
            mark = Validation.maxIntValidationZeroBased(100, scan);
            if (mark <= stmark) {
                this.somark = mark;
                isNotValid = false;
            } else {
                System.out.println("Oral mark must be equal or less than Total mark.");
                System.out.println();
            }
        }
    }

    public void setStmark(int stmark) {
        this.stmark = stmark;
    }

    public void setStmarkWithValidation(Scanner scan) {
        int stmark = Validation.maxIntValidationZeroBased(100, scan);
        this.stmark = stmark;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    // CONSTRUCTORS
    public Homework() {
    }

    public Homework(int somark, int stmark, Student student, Assignment assignment) {
        this.somark = somark;
        this.stmark = stmark;
        this.student = student;
        this.assignment = assignment;
    }

    public Homework(int id, int somark, int stmark, Student student, Assignment assignment) {
        this.id = id;
        this.somark = somark;
        this.stmark = stmark;
        this.student = student;
        this.assignment = assignment;
    }

    // EQUALS
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.id;
        hash = 23 * hash + this.somark;
        hash = 23 * hash + this.stmark;
        hash = 23 * hash + Objects.hashCode(this.student);
        hash = 23 * hash + Objects.hashCode(this.assignment);
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
        final Homework other = (Homework) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.somark != other.somark) {
            return false;
        }
        if (this.stmark != other.stmark) {
            return false;
        }
        if (!Objects.equals(this.student, other.student)) {
            return false;
        }
        if (!Objects.equals(this.assignment, other.assignment)) {
            return false;
        }
        return true;
    }

    // TO STRING
    @Override
    public String toString() {
        return "Homework{" + "id=" + id + ", somark=" + somark + ", stmark=" + stmark + ", student=" + student + ", assignment=" + assignment + '}';
    }

//* ------------------------------------------------------------------------- */
// Homework Print
//* ------------------------------------------------------------------------- */        
    public static void printHomeworks(List<Homework> homeworks) {
        String line = "-";
        for (int i = 0; i < 112; i++) {
            line += "-";
        }

        System.out.println();
        System.out.println(line);
        System.out.printf("%34s%s\n", " ", "A S S I G N M E N T S   O F   S T U D E N T");
        System.out.println(line);
        System.out.printf("%-40s%-50s%11s%11s\n", "Course", "Assignment", "Oral Mark", "Total Mark");
        System.out.println(line);
        for (Homework s : homeworks) {
            String course = s.getAssignment().getAcourseTitle();
            String assignment = s.getAssignment().getAtitle();
            int omark = s.getSomark();
            int tmark = s.getStmark();
            System.out.printf("%-40s%-50s%11d%11d\n", course, assignment, omark, tmark);
        }
        System.out.println(line);
        System.out.println();
    }

    public static void printHomeworksWithId(List<Homework> homeworks) {
        String line = "-";
        for (int i = 0; i < 119; i++) {
            line += "-";
        }

        System.out.println();
        System.out.println(line);
        System.out.printf("%37s%s\n", " ", "A S S I G N M E N T S   O F   S T U D E N T");
        System.out.println(line);
        System.out.printf("%-7s%-40s%-50s%11s%11s\n", "id", "Course", "Assignment", "Oral Mark", "Total Mark");
        System.out.println(line);
        for (Homework s : homeworks) {
            int h_id = s.getId();
            String course = s.getAssignment().getAcourseTitle();
            String assignment = s.getAssignment().getAtitle();
            int omark = s.getSomark();
            int tmark = s.getStmark();
            System.out.printf("%-7s%-40s%-50s%11d%11d\n", h_id, course, assignment, omark, tmark);
        }
        System.out.println(line);
        System.out.println();
    }

    public static void printWeeksHomeworks(List<Homework> homeworks) {
        String line = "-";
        for (int i = 0; i < 169; i++) {
            line += "-";
        }

        System.out.println();
        System.out.println(line);
        System.out.printf("%62s%s\n", " ", "A S S I G N M E N T S   O F   T H E   W E E K");
        System.out.println(line);
        System.out.printf("%-7s%-20s%-30s%-40s%-50s%11s%11s\n", "id", "First Name", "Last Name", "Course", "Assignment", "Oral Mark", "Total Mark");
        System.out.println(line);
        for (Homework s : homeworks) {
            int h_id = s.getId();
            String fName = s.getStudent().getfNameReformed();
            String lName = s.getStudent().getlNameReformed();
            String course = s.getAssignment().getAcourseTitle();
            String assignment = s.getAssignment().getAtitle();
            int omark = s.getSomark();
            int tmark = s.getStmark();
            System.out.printf("%-7s%-20s%-30s%-40s%-50s%11d%11d\n", h_id, fName, lName, course, assignment, omark, tmark);
        }
        System.out.println(line);
        System.out.println();
    }

    /* ------------------------------------------------------------------------- */
// Assignments Per Course Per Student Print
//* ------------------------------------------------------------------------- */        
    public static void printHomeworkOfStudent(Scanner scan) {
        StudentDao sdao = new StudentDao();
        boolean studentNeeded = true;
        while (studentNeeded) {
            List<Student> students = sdao.getStudents();
            if(students.isEmpty()) {
                System.out.println("*** The Students list is empty. ***\n\n");
                studentNeeded = false;
            } else {
                Student.printStudents(students);
                System.out.println("Select the id of the Student.");
                System.out.println("Write 'C<' if you want to return to the previous menu.");
                int choise = Validation.positiveIntValidationWithReturn(scan);
                if (choise == -1) {
                    studentNeeded = false;
                } else {
                    Student s = sdao.getStudentById(choise);
                    if (s != null) {
                        System.out.println("You chose the Student " + s.getfNameReformed() + " " + s.getlNameReformed() + ".");
                        HomeworkDao hdao = new HomeworkDao();
                        List<Homework> homework = hdao.getHomeworkPerCoursePerStudent(s.getId());
                        System.out.println("\n\nSTUDENT " + s.getfNameReformed() + " " + s.getlNameReformed());
                        Homework.printHomeworks(homework);
                        System.out.println("Press 1 to repeat or 'C<' to return to the previous menu.");
                        choise = Validation.maxValidationWithReturn(1, scan);
                        if (choise == -1) {
                            studentNeeded = false;
                        }
                    }
                } 
            }

        }

    }

    public static void updateMarksOfStudent(Scanner scan) {
        HomeworkDao hdao = new HomeworkDao();
        boolean studentNeeded = true;
        while (studentNeeded) {
            List<Student> students = hdao.getStudentsWithAssignments();
            if(students.isEmpty()) {
                System.out.println("*** There are no Students with Assignmnents. ***\n\n");
                studentNeeded = false;
            } else {
                Student.printStudents(students);
                System.out.println("Select the id of the Student.");
                System.out.println("Write 'C<' if you want to return to the previous menu.");
                int choise = Validation.positiveIntValidationWithReturn(scan);
                if (choise == -1) {
                    studentNeeded = false;
                } else {
                    Student s = hdao.getStudentWithHomeworkById(choise);
                    if (s != null) {
                        System.out.println("You chose the Student " + s.getfNameReformed() + " " + s.getlNameReformed() + ".");
                        List<Homework> homework = hdao.getHomeworkPerCoursePerStudent(s.getId());

                        boolean homeworkNeeded = true;
                        while (homeworkNeeded) {
                            System.out.println("\n\nSTUDENT " + s.getfNameReformed() + " " + s.getlNameReformed());
                            Homework.printHomeworksWithId(homework);
                            System.out.println("Select the id of Assignment or write 'C<' to return to the previous menu.");
                            choise = Validation.positiveIntValidationWithReturn(scan);
                            if (choise == -1) {
                                homeworkNeeded = false;
                            } else {
                                Homework h = hdao.getHomeworkById(choise);
                                if (h != null) {
                                    System.out.println("Enter the student's total mark in the assignment.");
                                    h.setStmarkWithValidation(scan);
                                    System.out.println("Enter the student's oral mark in the assignment.");
                                    h.setOralMarkWithValidation(scan);
                                    hdao.updateHomeworkMarks(h);
                                    homeworkNeeded = false;
                                }
                            }
                        }
                    }
                } 
            }

        }
    }

    public static void weeksAssinments(Scanner scan) {
        boolean checkNeeded = true;
        while (checkNeeded) {

            // Finding the date range in which the assignments are
            AssignmentDao adao = new AssignmentDao();
            LocalDate endDate = adao.getMaxDate();
            LocalDate startDate = adao.getMinDate();
            if(endDate != null && startDate != null) {
                String end = Validation.LocalDate_to_String(endDate);
                String start = Validation.LocalDate_to_String(startDate);

                System.out.println("Enter a date to see the Students who have to submit"
                        + " assignments for the according week. Date format dd/mm/yyyy.");
                System.out.println("According to the data, the submission dates begin on "
                        + start + " and end on " + end);

                // validation input
                LocalDate request = null;
                boolean validation = true;
                while (validation) {
                    request = Validation.dateValidation(scan);
                    if (request.isBefore(startDate) || request.isAfter(endDate)) {
                        System.out.println("Enter a date from " + start + " to " + end);
                        System.out.println();
                    } else {
                        validation = false;
                    }
                }

                // Finding the week
                LocalDate requestMax = request;
                LocalDate requestMin = request;
                DayOfWeek firstDayOfWeek = request.getDayOfWeek();
                DayOfWeek lastDayOfWeek = request.getDayOfWeek();

                while (firstDayOfWeek != DayOfWeek.MONDAY) {
                    firstDayOfWeek = firstDayOfWeek.minus(1);
                    requestMin = requestMin.minusDays(1);
                }

                while (lastDayOfWeek != DayOfWeek.FRIDAY) {
                    if (lastDayOfWeek.getValue() < 5) {
                        lastDayOfWeek = lastDayOfWeek.plus(1);
                        requestMax = requestMax.plusDays(1);
                    } else {
                        lastDayOfWeek = lastDayOfWeek.minus(1);
                        requestMax = requestMax.minusDays(1);
                    }
                }

                start = Validation.LocalDate_to_String(requestMin);
                end = Validation.LocalDate_to_String(requestMax);
                String startSQL = Validation.LocalDate_to_String_for_SQL(requestMin);
                String endSQL = Validation.LocalDate_to_String_for_SQL(requestMax);

                HomeworkDao hdao = new HomeworkDao();
                List<Homework> weeksHomeworks = hdao.getWeeksHomeworks(startSQL, endSQL);

                System.out.println("\n\nAssignments from " + start + " to " + end + ".");
                printWeeksHomeworks(weeksHomeworks);
                System.out.println("Press 1 to repeat or type 'C<' to return to the previous menu.");
                int choise = Validation.maxValidationWithReturn(1, scan);
                if (choise == -1) {
                    checkNeeded = false;
                }
            } else {
                System.out.println("*** There are no assignments with submission dates. ***");
                checkNeeded = false;
            }
            
        }

    }

}
