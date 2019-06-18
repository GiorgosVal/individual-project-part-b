package individualprojectb;


import dao.AssignmentDao;
import dao.CourseDao;
import dao.EnrollmentDao;
import dao.StudentDao;
import dao.SyntheticDao;
import dao.TrainerDao;
import entities.Assignment;
import entities.AssignmentCourse;
import entities.Course;
import entities.Enrollment;
import entities.Homework;
import entities.Login;
import entities.Personnel;
import entities.Student;
import entities.Trainer;
import java.util.Scanner;
import validation.Validation;

public class IndividualProjectB {
    
    
    static String howManyMessage (String s) {
        return "\nHow many " + s + " do you want to create?" +
                returnToPrevius();
    }
    
    static String returnToPrevius () {
        return "\nWrite 'C<' if you want to return to the previous menu.";
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        CourseDao cdao = new CourseDao();
        StudentDao sdao = new StudentDao();
        TrainerDao tdao = new TrainerDao();
        AssignmentDao adao = new AssignmentDao();
        EnrollmentDao edao = new EnrollmentDao();
        int choise = 0;
        boolean start=Login.login(scan);
        
        if (start) {
            SyntheticDao syndao = new SyntheticDao();
            System.out.println("Do you want to continue with synthetic data?\n"
                    + "1. Yes\n2. No (This will reset all tables)\n3. Continue anyway (may include synthetic data)");
            choise = Validation.maxValidation(3, scan);
            if (choise == 1) {
                syndao.getSyntheticData();
            } else if (choise == 2) {
                syndao.resetTables();
            } else {
                
            }
        }
        
        
        while(start) {
            System.out.println("W E L C O M E   S C R E E N");
            System.out.println("Welcome to this app.\n\n"
                    + "This CRUD console app simulates a private school's data handling program.\n"
                    + "You can insert courses, trainers, students and assignments.\n"
                    + "You also can correlate the data given and output data.\n\n\n"
                    + "You have the following options:\n"
                    + "(Choose 1 or 3 respectively)\n\n"
                    + "1. Insert data to the database.\n2. Read data from the database.\n"
                    + "3. Update data to the database.\n4. Delete data from the database.\n"
                    + "\nWrite 'C<' if you want to EXIT the program.");

            choise = Validation.maxValidationWithReturn(4, scan);
            switch(choise) {
                case -1:
                    start=false;
                    break;
                case 1:
                    boolean inputNeeded = true;
                    while (inputNeeded) {
                        System.out.println("\nI N S E R T    D A T A    M E N U\n");
                        System.out.println("You have the following options:\n");
                        System.out.println("1. Create Courses.");
                        System.out.println("2. Create Student profiles.");
                        System.out.println("3. Create Trainer profiles.");
                        System.out.println("4. Create Assignments.\n");
                        System.out.println("5. Add a Student to a Course.");
                        System.out.println("6. Add an Assignment to a Course.");
                        System.out.println("7. Add a Trainer to a Course.\n");
                        System.out.println(returnToPrevius());

                        choise = Validation.maxValidationWithReturn(7, scan);

                        switch (choise) {
                            case -1:
                                inputNeeded=false;
                                break;
                            case 1:
                                
                                System.out.println(howManyMessage("Courses"));
                                choise = Validation.positiveIntValidationWithReturn(scan);
                                if(choise !=-1) {
                                    Course.createCourse(scan, choise);
                                }
                                break;
                            case 2:
                                System.out.println(howManyMessage("Student profiles"));
                                choise = Validation.positiveIntValidationWithReturn(scan);
                                if (choise != -1) {
                                    Student.createStudent(scan, choise);
                                }
                                break;
                            case 3:
                                System.out.println(howManyMessage("Trainer profiles"));
                                choise = Validation.positiveIntValidationWithReturn(scan);
                                if(choise !=-1) {
                                    Trainer.createTrainer(scan, choise);
                                }
                                break;
                            case 4:
                                System.out.println(howManyMessage("Assignments"));
                                choise = Validation.positiveIntValidationWithReturn(scan);
                                if(choise !=-1) {
                                    Assignment.createAssignment(scan, choise);
                                }
                                break;
                            case 5:
                                if(choise !=-1) {
                                    Enrollment.addStudentToCourse(scan);
                                }
                                break;
                            case 6:
                                if(choise !=-1) {
                                    AssignmentCourse.addAssignmentToCourse(scan);
                                }
                                break;
                            case 7:
                                if(choise !=-1) {
                                    Personnel.addTrainerToCourse(scan);
                                }
                                break;
                        }
                    }
                    break;
                case 2:
                    boolean readNeeded=true;
                    while(readNeeded) {
                        System.out.println("\nR E A D    D A T A    M E N U\n");
                        System.out.println("You have the following options:\n");
                        System.out.println(" 1. List all Courses.");
                        System.out.println(" 2. List all Students."); 
                        System.out.println(" 3. List all Trainers.");
                        System.out.println(" 4. List all Assignments.\n");
                        System.out.println(" 5. List all Trainers per Course.");
                        System.out.println(" 6. List all Students per Course.");
                        System.out.println(" 7. List all Assignments per Course.");
                        System.out.println(" 8. List all Assignments per Course per Student.");
                        System.out.println(" 9. List all Students who enrolled multiple Courses.");
                        System.out.println("10. List all Assignments that need to be submitted at a given week.\n");
                        System.out.println(returnToPrevius());
                        choise = Validation.maxValidationWithReturn(10, scan);
                        
                        switch(choise) {
                            case -1:
                                readNeeded=false;
                                break;
                            case 1:
                                Course.printCourses(cdao.getCourses());
                                break;
                            case 2:
                                Student.printStudents(sdao.getStudents());
                                break;
                            case 3:
                                Trainer.printTrainers(tdao.getTrainers());
                                break;
                            case 4:
                                Assignment.printAssignments(adao.getAssignments());
                                break;
                            case 5:
                                Personnel.printTrainersPerCourse(scan);
                                break;
                            case 6:
                                Enrollment.printStudentsPerCourse(scan);
                                break;
                            case 7:
                                AssignmentCourse.printAssignmentsPerCourse(scan);
                                break;
                            case 8:
                                Homework.printHomeworkOfStudent(scan);
                                break;
                            case 9:
                                Student.printStudents(edao.getStudentsWithMultipleEnrollments());
                                break;
                            case 10:
                                Homework.weeksAssinments(scan);
                                break;
                        }
                    }
                    break;
                case 3:
                    boolean updateNeeded = true;
                    while(updateNeeded) {
                        System.out.println("\nU P D A T E    D A T A    M E N U\n");
                        System.out.println("Currently you have the following option:\n");
                        System.out.println(" 1. Update marks of Students assignments.");
                        System.out.println(returnToPrevius());
                        choise = Validation.maxValidationWithReturn(10, scan);
                        switch(choise) {
                            case -1:
                                updateNeeded=false;
                                break;
                            case 1:
                                Homework.updateMarksOfStudent(scan);
                                break;
                            
                        }
                    }
                    
                    break;
                case 4:
                    boolean deletionNeeded = true;
                    while(deletionNeeded) {
                        System.out.println("\nD E L E T E    D A T A    M E N U\n");
                        System.out.println("Currently you have the following options:\n");
                        System.out.println("1. Delete Courses.");
                        System.out.println("2. Delete Students.");
                        System.out.println("3. Delete Trainers.");
                        System.out.println("4. Delete Assignments.");
                        System.out.println(returnToPrevius());
                        choise = Validation.maxValidationWithReturn(4, scan);
                        switch (choise) {
                            case -1:
                                deletionNeeded = false;
                                break;
                            case 1:
                                Course.deleteCourses(scan);
                                break;
                            case 2:
                                Student.deleteStudents(scan);
                                break;
                            case 3:
                                Trainer.deleteTrainers(scan);
                                break;
                            case 4:
                                Assignment.deleteAssignments(scan);
                                break;
                        }
                    }
                    
                    break;
            }               
        }
        scan.close();
        System.out.println("\n\nThanks for choosing our app!");
        System.out.println("Follow us on fb: TheBestEverApps4You");
    }
}
