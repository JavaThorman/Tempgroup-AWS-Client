package Tempgroup.AWS.utils;

import java.util.Scanner;

public class Menu {



    // Method for Menu 1: Authentication
    public static void authenticationMenu() {
        System.out.println("********************");
        System.out.println("*      MENU 1      *");
        System.out.println("********************");
        System.out.println("1. Login");
        System.out.println("2. Register");
    }

    // Method for Menu 2: View Data
    public static void viewDataMenu() {
        System.out.println("********************");
        System.out.println("*      MENU 2      *");
        System.out.println("********************");
        System.out.println("1. View All Students");
        System.out.println("2. View All Courses");
        System.out.println("3. View One Student");
        System.out.println("4. View One Course");
    }

    // Method for Menu 3: Data Management
    public static void dataManagementMenu() {
        System.out.println("********************");
        System.out.println("*      MENU 3      *");
        System.out.println("********************");
        System.out.println("1. Add Student");
        System.out.println("2. Add Course");
        System.out.println("3. Add Course to Student");
        System.out.println("4. Upgrade User to Admin");
    }


}

