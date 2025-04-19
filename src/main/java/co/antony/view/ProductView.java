package co.antony.view;

import co.antony.model.Product;
import co.antony.view.util.InputReader;

import java.util.List;

public class ProductView {
    private final InputReader inputReader;
    private final StockDisplayManager displayManager;
    private final MenuView menuView;

    public ProductView(InputReader inputReader, StockDisplayManager displayManager, MenuView menuView) {
        this.inputReader = inputReader;
        this.displayManager = displayManager;
        this.menuView = menuView;
    }

    public int[] collectStockSetupData(int stockSize) {
        menuView.displayHeader("CONFIGURING CATALOGUE SIZES");
        int[] catalogueSizes = new int[stockSize];

        for (int i = 0; i < stockSize; i++) {
            catalogueSizes[i] = inputReader.readInteger(
                    "▶️ Insert the number of catalogues in stock [" + (i + 1) + "]: ",
                    "❌ Please insert a valid number.",
                    1, Integer.MAX_VALUE);
        }

        return catalogueSizes;
    }

    public int collectStockSize() {
        return inputReader.readInteger(
                "▶️ Insert the number of stocks: ",
                "❌ Please insert a valid number.",
                1, Integer.MAX_VALUE);
    }

    public int selectStock(List<Integer> availableStocks, String purpose) {
        displayManager.displayAvailableStocks(availableStocks);
        return inputReader.readInteger(
                "▶️ Insert the number of stock you want to " + purpose + ": ",
                "❌ Please insert a valid stock number.",
                1, Integer.MAX_VALUE);
    }

    public int selectStockWithProducts(List<Integer> stocksWithProducts) {
        displayManager.displayStocksWithProducts(stocksWithProducts);
        return inputReader.readInteger(
                "▶️ Insert the number of stock containing products: ",
                "❌ Please insert a valid stock number.",
                1, Integer.MAX_VALUE);
    }

    public int selectCatalogue(int maxCatalogue) {
        return inputReader.readInteger(
                "▶️ Insert the catalogue number: ",
                "❌ Please insert a valid catalogue number.",
                1, maxCatalogue);
    }

    public Product collectProductData() {
        String productName = inputReader.readValidProductName("▶️ Insert the product name: ");
        Product product = new Product();
        product.setProductName(productName);
        return product;
    }

    public String collectCurrentProductName() {
        return inputReader.readString("▶️ Insert the current product name: ").trim();
    }

    public void waitForUserInput() {
        inputReader.waitForEnter();
    }
}
