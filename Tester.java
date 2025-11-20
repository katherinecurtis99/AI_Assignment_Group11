import java.util.Scanner;

/**
 * Tests Classes
 * Author - Group 11
 * Version - 1.0
 */

public class Tester {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("We are gonna smash this module, " + name);
        scanner.close();
    }
}
