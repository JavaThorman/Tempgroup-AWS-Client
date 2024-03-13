package Tempgroup.AWS.utils;

import java.util.Scanner;

public class InputScanner {

    private static Scanner scanner = new Scanner(System.in); // Make scanner static

    public static int intPut(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            System.out.print(message);
            scanner.next(); // consume the invalid input
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // consume the newline character
        return input;
    }

    public static String stringPut(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static double doublePut(String message) {
        System.out.print(message);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid double.");
            System.out.print(message);
            scanner.next(); // consume the invalid input
        }
        double input = scanner.nextDouble();
        scanner.nextLine(); // consume the newline character
        return input;
    }
}
