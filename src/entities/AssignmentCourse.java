package entities;

import dao.AssignmentCourseDao;
import dao.CourseDao;
import dao.EnrollmentDao;
import dao.HomeworkDao;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import validation.Validation;

public class AssignmentCourse {

    private Course course;
    private List<Assignment> assignments;

    // GETTERS
    public Course getCourse() {
        return course;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    // SETTERS
    public void setCourse(Course course) {
        this.course = course;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    // CONSTRUCTORS
    public AssignmentCourse() {
    }

    public AssignmentCourse(Course course) {
        this.course = course;
    }

    public AssignmentCourse(Course course, List<Assignment> assignments) {
        this.course = course;
        this.assignments = assignments;
    }

    // EQUALS
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.course);
        hash = 79 * hash + Objects.hashCode(this.assignments);
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
        final AssignmentCourse other = (AssignmentCourse) obj;
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        if (!Objects.equals(this.assignments, other.assignments)) {
            return false;
        }
        return true;
    }

    // TO STRING
    @Override
    public String toString() {
        return "AssignmentCourse{" + "course=" + course + ", assignments=" + assignments + '}';
    }

//* ------------------------------------------------------------------------- */
// Assignments Per Course Print
//* ------------------------------------------------------------------------- */        
    public static void printAssignmentsPerCourse(Scanner scan) {
        CourseDao cdao = new CourseDao();
        boolean courseNeeded = true;
        while (courseNeeded) {
            List<Course> courses = cdao.getCourses();
            if(courses.isEmpty()) {
                System.out.println("*** The Courses list is empty. ***\n\n");
                courseNeeded = false;
            } else {
                Course.printCourses(courses);
                System.out.println("Select the id of the Course.");
                System.out.println("Write 'C<' if you want to return to the previous menu.");
                int choise = Validation.positiveIntValidationWithReturn(scan);
                if (choise == -1) {
                    courseNeeded = false;
                } else {
                    Course c = cdao.getCourseById(choise);
                    if (c != null) {
                        System.out.println("You chose the Course '" + c.getCtitle() + "'.");
                        AssignmentCourseDao acdao = new AssignmentCourseDao();
                        List<Assignment> assignments = acdao.getAssignmentsPerCourse(c.getId());
                        System.out.println("\n\nCOURSE " + c.getCtitle());
                        Assignment.printAssignments(assignments);
                        System.out.println("Press 1 to repeat or 'C<' to return the previous menu.");
                        choise = Validation.maxValidationWithReturn(1, scan);
                        if (choise == -1) {
                            courseNeeded = false;
                        }
                    }
                }
            }

        }

    }
    
//* ------------------------------------------------------------------------- */
// Relate Assignment To Course
//* ------------------------------------------------------------------------- */        

    public static void addAssignmentToCourse(Scanner scan) {
        CourseDao cdao = new CourseDao();
        /* Getting all Courses */
        boolean courseNeeded = true;
        while (courseNeeded) {
            Course.printCourses(cdao.getCourses());
            System.out.println("Select the id of the Course.");
            System.out.println("Write 'C<' if you want to return to the previous menu.");
            int choise = Validation.positiveIntValidationWithReturn(scan);
            if (choise == -1) {
                courseNeeded = false;
            } else {
                Course c = cdao.getCourseById(choise);          // if wrong co_id, Course == null
                if (c != null) {
                    System.out.println("You chose the Course '" + c.getCtitle() + "'.");
                    
                    // Checking if the selected Course has less than 6 assignments
                    AssignmentCourseDao acdao = new AssignmentCourseDao();
                    int numOfAssignments = acdao.getNumOfAssignmentsOfCourse(c.getId());
                    if(numOfAssignments >= 6) {
                        System.out.println("You cannot add more Assignments to this Course.");
                    } else if (numOfAssignments >= 0 && numOfAssignments < 6) {
                        
                        // Getting available assignments
                        boolean assignmentNeeded = true;
                        while (assignmentNeeded) {
                            System.out.println("\n\nThe available assignments are the following.");
                            Assignment.printAssignments(acdao.getAvailableAssignments());
                            System.out.println("Select the id of the Assignment.");
                            System.out.println("Write 'C<' if you want to return to the previous menu.");
                            choise = Validation.positiveIntValidationWithReturn(scan);
                            if(choise == -1) {
                                assignmentNeeded = false;
                            } else {
                                Assignment a = acdao.getAvailableAssignmentById(choise);    // if wrong as_id, a == null
                                if(a != null) {
                                    // initialization of Course and subDate of assignment object
                                    a.setAcourse(c);
                                    a.setAsubDateWithValidation(scan, c.getCstart(), c.getCend());
                                    // updating the assignment to the database
                                    boolean insertOK = acdao.relateAssignmentToCourse(a);
                                    if(insertOK) {
                                        // Getting students from Enrolments that enrolled the selected course
                                        EnrollmentDao edao = new EnrollmentDao();
                                        List<Student> students = edao.getStudentsEnrolledToCourse(a.getAcourse().getId());
                                        // Adding students/assignments to Homework table
                                        HomeworkDao hdao = new HomeworkDao();
                                        for(Student s : students) {
                                            hdao.addAssignmentsToStudent(s.getId(), a.getId());
                                        }
                                        assignmentNeeded = false;
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
