package co.antony.controller;

import co.antony.model.Product;
import co.antony.model.service.ProductService;
import co.antony.view.MenuView;
import co.antony.view.ProductView;
import co.antony.view.StockDisplayManager;

import java.util.List;

public class StockController {
    private final ProductService productService;
    private final MenuView menuView;
    private final ProductView productView;
    private final StockDisplayManager displayManager;

    public StockController(
            ProductService productService,
            MenuView menuView,
            ProductView productView,
            StockDisplayManager displayManager
    ) {
        this.productService = productService;
        this.menuView = menuView;
        this.productView = productView;
        this.displayManager = displayManager;
    }

    public void setupStockAndCatalogue() {
        int stockSize = productView.collectStockSize();
        int[] catalogueSizes = productView.collectStockSetupData(stockSize);

        boolean success = productService.setupStockAndCatalogue(stockSize, catalogueSizes);

        if (success) {
            menuView.displaySuccessMessage("Stock and Catalogue are set up successfully.");
            displayManager.displayStocks(productService.getAllProductNames());
        } else {
            menuView.displayErrorMessage("Failed to set up Stock and Catalogue.");
        }
    }

    public void displayStocks() {
        if (productService.isSystemInitialized()) {
            menuView.displayWarningMessage("You need to set up stock and catalogue first.");
            return;
        }

        displayManager.displayStocks(productService.getAllProductNames());
    }

    public void insertProduct() {
        if (productService.isSystemInitialized()) {
            menuView.displayWarningMessage("You need to set up stock and catalogue first.");
            return;
        }

        if (productService.areAllStocksFull()) {
            menuView.displayWarningMessage("All stocks are full. Cannot insert more products.");
            return;
        }

        List<Integer> availableStocks = productService.getAvailableStocks();
        int selectedStock = productView.selectStock(availableStocks, "insert into");

        if (!availableStocks.contains(selectedStock)) {
            menuView.displayWarningMessage("Invalid stock selection or the stock is full.");
            return;
        }

        displayManager.displaySpecificStock(productService.getAllProductNames(), selectedStock);

        int catalogueSize = productService.getCatalogueSize(selectedStock - 1);
        int selectedCatalogue = productView.selectCatalogue(catalogueSize);

        Product newProduct = productView.collectProductData();

        boolean success = productService.insertProduct(newProduct, selectedStock, selectedCatalogue);

        if (success) {
            menuView.displaySuccessMessage("Product inserted successfully.");
            displayManager.displaySpecificStock(productService.getAllProductNames(), selectedStock);
        } else {
            menuView.displayErrorMessage("Failed to insert product. The catalogue might be occupied or invalid.");
        }
    }

    public void updateProduct() {
        if (productService.isSystemInitialized()) {
            menuView.displayWarningMessage("You need to set up stock and catalogue first.");
            return;
        }

        List<Integer> stocksWithProducts = productService.getStocksWithProducts();

        if (stocksWithProducts.isEmpty()) {
            menuView.displayWarningMessage("There are no products to update.");
            return;
        }

        int selectedStock = productView.selectStockWithProducts(stocksWithProducts);

        if (!stocksWithProducts.contains(selectedStock)) {
            menuView.displayWarningMessage("Invalid stock selection or the stock has no products.");
            return;
        }

        displayManager.displaySpecificStock(productService.getAllProductNames(), selectedStock);

        String currentProductName = productView.collectCurrentProductName();
        Product updatedProduct = productView.collectProductData();

        boolean success = productService.updateProduct(currentProductName, updatedProduct, selectedStock);

        if (success) {
            menuView.displaySuccessMessage("Product updated successfully.");
            displayManager.displaySpecificStock(productService.getAllProductNames(), selectedStock);
        } else {
            menuView.displayErrorMessage("Failed to update product. The product might not exist.");
        }
    }

    public void deleteProduct() {
        if (productService.isSystemInitialized()) {
            menuView.displayWarningMessage("You need to set up stock and catalogue first.");
            return;
        }

        List<Integer> stocksWithProducts = productService.getStocksWithProducts();

        if (stocksWithProducts.isEmpty()) {
            menuView.displayWarningMessage("There are no products to delete.");
            return;
        }

        int selectedStock = productView.selectStockWithProducts(stocksWithProducts);

        if (!stocksWithProducts.contains(selectedStock)) {
            menuView.displayWarningMessage("Invalid stock selection or the stock has no products.");
            return;
        }

        displayManager.displaySpecificStock(productService.getAllProductNames(), selectedStock);

        String productName = productView.collectCurrentProductName();

        boolean success = productService.deleteProduct(productName, selectedStock);

        if (success) {
            menuView.displaySuccessMessage("Product deleted successfully.");
            displayManager.displaySpecificStock(productService.getAllProductNames(), selectedStock);
        } else {
            menuView.displayErrorMessage("Failed to delete product. The product might not exist.");
        }
    }

    public void viewInsertionHistory() {
        if (productService.isSystemInitialized()) {
            menuView.displayWarningMessage("You need to set up stock and catalogue first.");
            return;
        }

        if (!productService.hasAnyProducts()) {
            menuView.displayWarningMessage("No products have been inserted yet.");
            return;
        }

        displayManager.displayInsertionHistory(
                productService.getAllProductNames(),
                productService.getAllInsertionDates()
        );
    }

    public void runApplication() {
        menuView.displayWelcomeMessage();
        int option;

        do {
            option = menuView.displayMainMenu();

            switch (option) {
                case 1 -> {
                    menuView.displayHeader("SET UP STOCK AND CATALOGUE");
                    setupStockAndCatalogue();
                }
                case 2 -> {
                    menuView.displayHeader("VIEW PRODUCTS IN STOCK");
                    displayStocks();
                }
                case 3 -> {
                    menuView.displayHeader("INSERT PRODUCT TO STOCK CATALOGUE");
                    insertProduct();
                }
                case 4 -> {
                    menuView.displayHeader("UPDATE PRODUCT IN STOCK CATALOGUE");
                    updateProduct();
                }
                case 5 -> {
                    menuView.displayHeader("DELETE PRODUCT FROM STOCK CATALOGUE");
                    deleteProduct();
                }
                case 6 -> {
                    menuView.displayHeader("VIEW INSERTION HISTORY");
                    viewInsertionHistory();
                }
                case 7 -> menuView.displayExitMessage();
            }

            if (option != 7) {
                productView.waitForUserInput();
            }

        } while (option != 7);
    }
}
