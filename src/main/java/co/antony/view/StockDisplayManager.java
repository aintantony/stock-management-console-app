package co.antony.view;

import co.antony.view.util.ColorManager;

import java.util.Date;
import java.util.List;

public class StockDisplayManager {
    public void displayStocks(String[][] productNames) {
        if (productNames == null || productNames.length == 0) {
            System.out.println(ColorManager.yellow("⚠️ The stock is not set up yet."));
            return;
        }

        for (int i = 0; i < productNames.length; i++) {
            System.out.print("Stock [" + (i + 1) + "]: ");

            for (int j = 0; j < productNames[i].length; j++) {
                String display = (productNames[i][j] == null || productNames[i][j].isEmpty()) ?
                        "[ " + (j + 1) + " - Empty ]" :
                        "[ " + (j + 1) + " - " + productNames[i][j] + " ]";
                System.out.print(display + " ");
            }

            boolean hasEmptySlot = false;
            for (String product : productNames[i]) {
                if (product == null) {
                    hasEmptySlot = true;
                    break;
                }
            }

            String status = hasEmptySlot ?
                    ColorManager.green(" - Still Available") :
                    ColorManager.yellow(" - Stock is full");
            System.out.println(status);
        }
    }

    public void displaySpecificStock(String[][] productNames, int stockNum) {
        int stockIndex = stockNum - 1;

        if (stockIndex < 0 || stockIndex >= productNames.length) {
            System.out.println(ColorManager.red("❌ Invalid stock number."));
            return;
        }

        System.out.print("Stock [" + stockNum + "]: ");

        for (int i = 0; i < productNames[stockIndex].length; i++) {
            String display = (productNames[stockIndex][i] == null || productNames[stockIndex][i].isEmpty()) ?
                    "[ " + (i + 1) + " - Empty ]" :
                    "[ " + (i + 1) + " - " + productNames[stockIndex][i] + " ]";
            System.out.print(display + " ");
        }

        boolean hasEmptySlot = false;
        for (String product : productNames[stockIndex]) {
            if (product == null) {
                hasEmptySlot = true;
                break;
            }
        }

        String status = hasEmptySlot ?
                ColorManager.green(" - Still Available") :
                ColorManager.yellow(" - Stock is full");
        System.out.println(status);
    }

    public void displayInsertionHistory(String[][] productNames, Date[][] insertionDates) {
        boolean hasHistory = false;

        for (int i = 0; i < productNames.length; i++) {
            for (int j = 0; j < productNames[i].length; j++) {
                if (insertionDates[i][j] != null) {
                    System.out.println("Product '" + productNames[i][j] +
                                       "' inserted at " + insertionDates[i][j] +
                                       " in Stock [" + (i+1) + "], Catalogue [" + (j+1) + "]");
                    hasHistory = true;
                }
            }
        }

        if (!hasHistory) {
            System.out.println(ColorManager.yellow("⚠️ No insertion history available."));
        }
    }

    public void displayAvailableStocks(List<Integer> availableStocks) {
        if (availableStocks.isEmpty()) {
            System.out.println(ColorManager.yellow("⚠️ No available stocks."));
            return;
        }

        System.out.print("Available Stocks: ");
        for (Integer stockNum : availableStocks) {
            System.out.print("| " + stockNum + " |");
        }
        System.out.println();
    }

    public void displayStocksWithProducts(List<Integer> stocksWithProducts) {
        if (stocksWithProducts.isEmpty()) {
            System.out.println(ColorManager.yellow("⚠️ No stocks with products."));
            return;
        }

        System.out.print("Stocks with Products: ");
        for (Integer stockNum : stocksWithProducts) {
            System.out.print("| " + stockNum + " |");
        }
        System.out.println();
    }
}
