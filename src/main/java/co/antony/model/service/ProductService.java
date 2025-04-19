package co.antony.model.service;

import co.antony.model.Product;
import co.antony.model.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public boolean setupStockAndCatalogue(int stockSize, int[] catalogueSizes) {
        if (stockSize <= 0) {
            return false;
        }

        for (int size : catalogueSizes) {
            if (size <= 0) {
                return false;
            }
        }

        repository.initializeStorage(stockSize, catalogueSizes);
        return true;
    }

    public boolean insertProduct(Product product, int stockNum, int catalogueNum) {
        int stockIndex = stockNum - 1;
        int catalogueIndex = catalogueNum - 1;

        if (stockIndex < 0 || stockIndex >= repository.getStockSize()) {
            return false;
        }

        if (catalogueIndex < 0 || catalogueIndex >= repository.getCatalogueSize(stockIndex)) {
            return false;
        }

        if (repository.isCatalogueOccupied(stockIndex, catalogueIndex)) {
            return false;
        }

        if (validateProductName(product.getProductName())) {
            return false;
        }

        repository.addProduct(product.getProductName(), stockIndex, catalogueIndex);
        return true;
    }

    public boolean updateProduct(String currentName, Product updatedProduct, int stockNum) {
        int stockIndex = stockNum - 1;

        if (stockIndex < 0 || stockIndex >= repository.getStockSize()) {
            return false;
        }

        if (repository.isProductExists(stockIndex, currentName)) {
            return false;
        }

        if (validateProductName(updatedProduct.getProductName())) {
            return false;
        }

        repository.updateProduct(currentName, updatedProduct.getProductName(), stockIndex);
        return true;
    }

    public boolean deleteProduct(String productName, int stockNum) {
        int stockIndex = stockNum - 1;

        if (stockIndex < 0 || stockIndex >= repository.getStockSize()) {
            return false;
        }

        if (repository.isProductExists(stockIndex, productName)) {
            return false;
        }

        repository.deleteProduct(productName, stockIndex);
        return true;
    }

    public List<Integer> getAvailableStocks() {
        List<Integer> availableStocks = new ArrayList<>();
        for (int i = 0; i < repository.getStockSize(); i++) {
            if (repository.isStockFull(i)) {
                availableStocks.add(i + 1);
            }
        }
        return availableStocks;
    }

    public List<Integer> getStocksWithProducts() {
        List<Integer> stocksWithProducts = new ArrayList<>();
        for (int i = 0; i < repository.getStockSize(); i++) {
            if (repository.hasProducts(i)) {
                stocksWithProducts.add(i + 1);
            }
        }
        return stocksWithProducts;
    }

    public boolean isSystemInitialized() {
        return repository.getStockSize() <= 0;
    }

    public boolean areAllStocksFull() {
        for (int i = 0; i < repository.getStockSize(); i++) {
            if (repository.isStockFull(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateProductName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return true;
        }
        if (!name.matches("[a-zA-Z0-9\\s]{2,30}")) {
            return true;
        }
        if (name.matches("\\d+")) {
            return true;
        }
        return false;
    }

    public String[][] getAllProductNames() {
        return repository.getAllProductNames();
    }

    public java.util.Date[][] getAllInsertionDates() {
        return repository.getAllInsertionDates();
    }

    public int getStockSize() {
        return repository.getStockSize();
    }

    public int getCatalogueSize(int stockIndex) {
        return repository.getCatalogueSize(stockIndex);
    }

    public boolean hasAnyProducts() {
        return repository.hasAnyProducts();
    }
}