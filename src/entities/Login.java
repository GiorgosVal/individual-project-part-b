/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author giorgos
 */
public class Login {

    private String username;
    private String pass;

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Login(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.username);
        hash = 43 * hash + Objects.hashCode(this.pass);
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
        final Login other = (Login) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.pass, other.pass)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Login{" + "username=" + username + ", pass=" + pass + '}';
    }

    public static boolean login(Scanner scan) {
        int numOfTries = 0;
        boolean credentialsNeeded = true;
        boolean loginOK = false;
        while (credentialsNeeded) {
            System.out.print("Enter your credentials to continue. You have " + (3-numOfTries));
            if((3-numOfTries) == 1) {
                System.out.println(" try.");
            } else
                System.out.println(" tries.");
            System.out.println("Example: admin 1234");
            String username = scan.next();
            String pass = scan.next();

            LoginDao ldao = new LoginDao();
            Login login = ldao.getUser(username);
            if (login == null) {
                numOfTries++;
                if (numOfTries == 3) {
                    System.out.println("\n\nChuck Norris is comming for you...");
                    System.out.println("\n\n...and he'll find you...");
                    credentialsNeeded = false;
                }
            } else {
                boolean passMatches = pass.equals(login.pass);
                if (!passMatches) {
                    System.out.println("*** Not correct password. ***");
                    numOfTries++;
                    if (numOfTries == 3) {
                        System.out.println("\n\nChuck Norris is comming for you...");
                        System.out.println("\n\n...and he'll find you...");
                        credentialsNeeded = false;
                    }
                } else {
                    loginOK = true;
                    credentialsNeeded = false;
                }
            }
        }
        return loginOK;
    }

}
