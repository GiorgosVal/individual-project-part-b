package entities;

import dao.AssignmentCourseDao;
import dao.AssignmentDao;
import dao.CourseDao;
import dao.EnrollmentDao;
import dao.HomeworkDao;
import dao.StudentDao;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import validation.Validation;

public class Enrollment {
    private int id;
   // private int st_id;
   // private int co_id;
    private Student student;
    private Course course;
    
    // GETTERS

    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }


    
    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
    // CONSTRUCTORS

    public Enrollment() {
    }

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public Enrollment(int id, Student student, Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
    }

    
    // EQUALS

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.student);
        hash = 47 * hash + Objects.hashCode(this.course);
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
        final Enrollment other = (Enrollment) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.student, other.student)) {
            return false;
        }
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        return true;
    }
    
    // TO STRING

    @Override
    public String toString() {
        return "Enrollment{" + "id=" + id + ", student=" + student + ", course=" + course + '}';
    }

    
    
/* ------------------------------------------------------------------------- */
// Students Per Course Print
//* ------------------------------------------------------------------------- */        
    public static void printStudentsPerCourse(Scanner scan) {
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
                    EnrollmentDao endao = new EnrollmentDao();
                    List<Student> students = endao.getStudentsPerCourse(c.getId());
                    System.out.println("\n\nCOURSE " + c.getCtitle());
                    Student.printStudents(students);
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
// Relate Student To Course
//* ------------------------------------------------------------------------- */        
    public static void addStudentToCourse(Scanner scan) {
        CourseDao cdao = new CourseDao();
        StudentDao sdao = new StudentDao();
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
                    EnrollmentDao edao = new EnrollmentDao();
                    List<Student> students = edao.getAvailableStudentsForCourse(c.getId());
                    boolean studentNeeded = true;
                    while(studentNeeded) {
                        System.out.println("\n\nCOURSE " + c.getCtitle() + " available students.");
                        Student.printStudents(students);
                        System.out.println("Choose the id of the Student you want to add to the Course, or write 'C<' to return to the previous menu.");
                        choise = Validation.positiveIntValidationWithReturn(scan);
                        if(choise == -1) {
                            studentNeeded = false;
                        } else {
                                Student s = sdao.getStudentById(choise);
                                if (s!=null) {
                                    boolean studentISavailable = edao.checkIfStudentISavailable(c.getId(), s.getId());
                                    if (!studentISavailable) {
                                        s = null;
                                    } 
                                }
                                
                            if(s != null) {
                                System.out.println("You chose the Student " + s.getfNameReformed() + " " + s.getlNameReformed() + ".");
                                boolean insertOK = edao.addStudentToCourse(c.getId(), s.getId());
                                if(insertOK) {
                                    studentNeeded = false;
                                    AssignmentCourseDao acdao = new AssignmentCourseDao();
                                    List<Assignment> assignments = acdao.getAssignmentsPerCourse(c.getId());
                                    Assignment.printAssignments(assignments);
                                    HomeworkDao hdao = new HomeworkDao();
                                    int st_id = s.getId();
                                    for(Assignment a : assignments) {
                                        int as_id = a.getId();
                                        hdao.addAssignmentsToStudent(st_id, as_id);
                                    }
                                }
                            }
                        }
                    }                    
                }
            }
        }

    }


    
    
    
    
    
    
}
