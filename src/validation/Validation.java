package validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Validation {
/**
 * This method checks if the given input is a positive integer.
 * While the input is not positive integer, it keeps asking for new input.
 * If the input is correct, it returns the integer value.
 * @param scan
 * @return 
 */
    public static int positiveIntValidation(Scanner scan) {
        boolean isNotValid = true;
        int value = 0;
        while (isNotValid) {
            try {
                scan.hasNextInt();
                value = scan.nextInt();
                if (value > 0) {
                    isNotValid = false;
                } else {
                    value = 0;
                    System.out.println("*** Enter a positive number. ***");
                }

            } catch (Exception e) {

                System.out.println("*** Enter a positive number. ***");

            }
            scan.nextLine();
        }
        return value;
    }
    
    public static int positiveIntValidationZeroBased(Scanner scan) {
        boolean isNotValid = true;
        int value = 0;
        while (isNotValid) {
            try {
                scan.hasNextInt();
                value = scan.nextInt();
                if (value >= 0) {
                    isNotValid = false;
                } else {
                    value = -1;
                    System.out.println("*** Enter a positive number. ***");
                }

            } catch (Exception e) {

                System.out.println("*** Enter a positive number. ***");

            }
            scan.nextLine();
        }
        return value;
    }

    /**
     * This method checks if the given input is a positive integer.
     * While the input is not positive integer, it keeps asking for new input.
     * - If the input is correct, it returns the integer value.
     * - If the input is a defined string it returns the value -1, which serves
     * as a "Return" in the menu. 
     * 
     * @param scan
     * @return 
     */
    public static int positiveIntValidationWithReturn(Scanner scan) {
        boolean isNotValid = true;
        int value = 0;
        while (isNotValid) {
            try {
                scan.hasNextInt();
                value = scan.nextInt();
                if (value > 0) {
                    isNotValid = false;
                } else {
                    value = 0;
                    System.out.println("*** Enter a positive number. ***");
                }

            } catch (Exception e) {
                if (scan.hasNext("C<")) {
                    value = -1;
                    isNotValid = false;
                } else {
                    System.out.println("*** Enter a positive number. ***");
                }
            }
            scan.nextLine();
        }
        return value;
    }
    
    /**
     * This method checks if the given input is a positive double.
     * While the input is not positive double, it keeps asking for new input.
     * If the input is correct, it returns the double value.
     * @param scan
     * @return 
     */
    public static double positiveDoubleValidation(Scanner scan) {
        boolean isNotValid = true;
        double value = 0;
        while (isNotValid) {
            try {
                scan.hasNextDouble();
                value = scan.nextDouble();
                if (value > 0) {
                    isNotValid = false;
                } else {
                    value = 0;
                    System.out.println("*** Enter a positive number. ***");
                }

            } catch (Exception e) {
                System.out.println("*** Enter a positive number. ***");
            }
            scan.nextLine();
        }
        return value;
    }
    
    
    
    /**
     * This method checks if the length of the input exceeds a max range.
     * If the input is correct, it returns the input as a string.
     * If not, it keeps asking for new input.
     * @param scan
     * @param max
     * @return 
     */
    public static String maxCharactersValidation(Scanner scan, int max) {
        boolean isNotValid = true;
        String string = "";
        while (isNotValid) {
            string = scan.nextLine();
            if (string.length() <= max) {
                isNotValid = false;
            } else {
                System.out.println("*** Maximum " + max + " characters allowed for this input. ***");
                System.out.println("*** Please give again the input. ***");
                System.out.println();
            }
        }
        return string;
    }
    
    /**
     * This method checks if the length of a string exceeds a max range
     * and returns a boolean value.
     * @param string
     * @param max
     * @return 
     */
    public static Boolean maxCharactersValidation(String string, int max) {
        if (string.length() <= max) {
            return true;
        } else {
            System.out.println("*** Maximum " + max + " characters allowed for this input. ***");
            System.out.println("*** Please give again the input. ***");
            System.out.println();
            return false;
        }
    }
    
    /**
     * This method checks if the given input is a positive integer within
     * a max range. While the input is not positive integer or exceeds the range,
     * it keeps asking for new input.
     *  If the input is correct, it returns the integer value.
     * @param max
     * @param scan
     * @return 
     */
    public static int maxValidation(int max, Scanner scan) {
        boolean isNotValid = true;
        int value = 0;
        while (isNotValid) {
            value = positiveIntValidation(scan);
            if (value <= max) {
                isNotValid = false;
            } else {
                System.out.println("*** Enter a number withing range. ***");
            }
        }
        return value;
    }
    
    /**
     * This method checks if the given input is a positive integer within
     * a max range. While the input is not positive integer or exceeds the range,
     * it keeps asking for new input.
     * - If the input is correct, it returns the integer value.
     * - If the input is a defined string it returns the value -1, which serves
     * as a "Return" in the menu.
     * @param max
     * @param scan
     * @return 
     */
    public static int maxValidationWithReturn(int max, Scanner scan) {
        boolean isNotValid = true;
        int value = 0;
        while (isNotValid) {
            value = positiveIntValidationWithReturn(scan);
            if (value <= max) {
                isNotValid = false;
            } else {
                System.out.println("*** Enter a number withing range. ***");
            }
        }
        return value;
    }
    
    /**
     * This method checks if the given input is a positive double within
     * a max range. While the input is not positive double or exceeds the range,
     * it keeps asking for new input.
     * If the input is correct, it returns the double value.
     * @param max
     * @param scan
     * @return 
     */
    public static double maxDoubleValidation(double max, Scanner scan) {
        boolean isNotValid = true;
        double value = 0;
        while (isNotValid) {
            value = positiveDoubleValidation(scan);
            if (value <= max) {
                isNotValid = false;
            } else {
                System.out.println("*** Enter a number withing range. ***");
            }
        }
        return value;
    }
    
    
    public static int maxIntValidationZeroBased(int max, Scanner scan) {
        boolean isNotValid = true;
        int value = 0;
        while (isNotValid) {
            value = positiveIntValidationZeroBased(scan);
            if (value <= max) {
                isNotValid = false;
            } else {
                System.out.println("*** Enter a number withing range. ***");
            }
        }
        return value;
    }

//////////////////////////////////////////////////////////////////////////////
//
//                      D A T E S   V A L I D A T I O N
//
///////////////////////////////////////////////////////////////////////////////    
    
    /**
     * This method checks if the input matches a date pattern. If it does, it
     * returns a LocalDate.
     * @param scan
     * @return 
     */
    public static LocalDate dateValidation(Scanner scan) {
        System.out.println("Enter a date in the format dd/mm/yyyy:");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");       // ορισμός pattern
        LocalDate dateOutput;
        while (true) {
            try {
                String input = scan.nextLine();
                LocalDate formatInput = LocalDate.parse(input, formatter);         // διαμόρφωση input σύμφωνα με το pattern  

                int year = formatInput.getYear();                                   // έτος, μήνας, ημέρα σε int
                int monthh = formatInput.getMonthValue();
                int day = formatInput.getDayOfMonth();

                dateOutput = LocalDate.of(year, monthh, day);             // δημιουργία ημερομηνίας

                System.out.println(dateOutput.format(formatter));                   // εκτύπωση ημερομηνίας
                break;
            } catch (Exception e) {
                System.out.println("*** Not valid format. ***");
            }
        }
        return dateOutput;
    }
    
    /**
     * This method turns a string into a LocalDate object.
     * No validation is made by this method.
     * @param date
     * @return 
     */
    public static LocalDate StringDate_to_LocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String rawDate = date;
        LocalDate formattedDate = LocalDate.parse(rawDate, formatter);
        int year = formattedDate.getYear();                                   // έτος, μήνας, ημέρα σε int
        int monthh = formattedDate.getMonthValue();
        int day = formattedDate.getDayOfMonth();
        LocalDate dateOutput = LocalDate.of(year, monthh, day);
        return dateOutput;
    }
    
    /**
     * This method turns a LocalDate into a string. If the LocalDate object is null
     * it returns "No date". No further validation is made by this method.
     * @param date
     * @return 
     */
    public static String LocalDate_to_String(LocalDate date) {
        String dateString = "No date";
        if(date!=null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dateString = date.format(formatter);
        }
        return dateString;
    }
    
    public static String LocalDate_to_String_for_SQL(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String dateString = date.format(formatter);
        return dateString;
    }

    
//////////////////////////////////////////////////////////////////////////////
//
//                      D A T E S   V A L I D A T I O N
//
///////////////////////////////////////////////////////////////////////////////      
    
    
    
    /**
     * This method checks if the characters of the input are within the range of
     * UTF-16 character set for English and Greek language.
     * If the characters are within range, it returns the input as a string.
     * If not, it keeps asking for new input.
     * @param scan
     * @return 
     */
    public static String namesValidation(Scanner scan) {
        boolean isNotValid = true;
        String name = "";
        while (isNotValid) {
            name = scan.nextLine();
            int condition = 0;  // 0 at start, 1 if characters NOT valid, 2 if characters valid
            while (condition == 0) {
                if(name.length() < 2) {
                    condition = 1;
                    break;
                }
                for (int i = 0; i < name.length(); i++) {
                    char c = name.charAt(i);
                    if (!((c >= 65 && c <= 90) || (c >= 97 && c <= 122) /* || ((c >= 902 && c <= 974))*/)) {
                        condition = 1;
                        break;
                    }
                }
                if (condition != 1) {
                    condition = 2;
                }
            }
            if (condition == 2) {
                isNotValid = false;
            } else {
                System.out.println("*** Enter a valid name. ***");

            }
        }
        return name;
    }
    
    public static boolean firstNameValidation( String firstName ) {
      return firstName.matches( "[A-Z][a-z]*" );
   }
    
    public static boolean lastNameValidation( String lastName ) {
      return lastName.matches( "[A-Z]+([ '-][a-zA-Z]+)*" );
   }
    
    
    
    
    
    
    
    
    
    
    
    
}



