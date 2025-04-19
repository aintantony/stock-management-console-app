package co.antony.view;

import co.antony.view.util.ColorManager;
import co.antony.view.util.InputReader;

public class MenuView {
    private final InputReader inputReader;

    public MenuView(InputReader inputReader) {
        this.inputReader = inputReader;
    }

    public int displayMainMenu() {
        clearScreen();
        System.out.println(ColorManager.yellow("=".repeat(65)));
        System.out.println(ColorManager.purple("              📦 STOCK MANAGEMENT SYSTEM 📦"));
        System.out.println(ColorManager.yellow("=".repeat(65)));
        System.out.println(ColorManager.cyan("1. Set Up Stock and Catalogue"));
        System.out.println(ColorManager.cyan("2. View Products in Stock"));
        System.out.println(ColorManager.cyan("3. Insert Product to Stock Catalogue"));
        System.out.println(ColorManager.cyan("4. Update Product in Stock Catalogue by Product name"));
        System.out.println(ColorManager.cyan("5. Delete Product from Stock Catalogue by Product name"));
        System.out.println(ColorManager.cyan("6. View Insertion History in Stock Catalogue"));
        System.out.println(ColorManager.red("7. Exit Program"));
        System.out.println(ColorManager.yellow("=".repeat(65)));

        return inputReader.readInteger("▶️ Insert your option: ",
                "❌ Invalid input. Please enter a number.", 1, 7);
    }

    public void displayHeader(String title) {
        System.out.println(ColorManager.purple("=".repeat(20) + " " + title.toUpperCase() + " " + "=".repeat(20)));
    }

    public void displaySuccessMessage(String message) {
        System.out.println(ColorManager.green("✅ " + message));
    }

    public void displayErrorMessage(String message) {
        System.out.println(ColorManager.red("❌ " + message));
    }

    public void displayWarningMessage(String message) {
        System.out.println(ColorManager.yellow("⚠️ " + message));
    }

    public void displayExitMessage() {
        System.out.println(ColorManager.green("🔻 Thank you for using Stock Management System. Goodbye!"));
    }

    public void displayWelcomeMessage() {
        System.out.println(ColorManager.green("✨ Welcome to the Stock Management System ✨"));
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
