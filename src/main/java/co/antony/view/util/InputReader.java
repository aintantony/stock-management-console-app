package co.antony.view.util;

import java.util.Scanner;

public class InputReader {
    private final Scanner scanner;

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInteger(String prompt, String errorMessage) {
        while (true) {
            System.out.print(ColorManager.blue(prompt));
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println(ColorManager.red(errorMessage));
                scanner.nextLine();
            }
        }
    }

    public int readInteger(String prompt, String errorMessage, int min, int max) {
        while (true) {
            int value = readInteger(prompt, errorMessage);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println(ColorManager.yellow("⚠️ Value must be between " + min + " and " + max));
        }
    }

    public String readString(String prompt) {
        scanner.nextLine();
        System.out.print(ColorManager.blue(prompt));
        return scanner.nextLine();
    }

    public String readValidProductName(String prompt) {
        while (true) {
            String input = readString(prompt).trim();

            if (input.isEmpty()) {
                System.out.println(ColorManager.red("❌ Product name cannot be empty."));
                continue;
            }

            if (!input.matches("[a-zA-Z0-9\\s]{2,30}")) {
                System.out.println(ColorManager.red("❌ Product name must be 2–30 characters with letters, numbers, and spaces only."));
                continue;
            }

            if (input.matches("\\d+")) {
                System.out.println(ColorManager.red("❌ Product name cannot be numbers only."));
                continue;
            }

            return input;
        }
    }

    public void waitForEnter() {
        System.out.print(ColorManager.yellow("\nPress Enter to continue...\n"));
        scanner.nextLine();
    }
}
