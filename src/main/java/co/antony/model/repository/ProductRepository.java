package co.antony.model.repository;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@ToString
public class ProductRepository {
    private int stockSize;
    private String[][] productNames;
    private Date[][] insertionDate;
    private static int count = 0;

    public void initializeStorage(int stockSize, int[] catalogueSizes) {
        this.stockSize = stockSize;
        productNames = new String[stockSize][];
        insertionDate = new Date[stockSize][];

        for (int i = 0; i < stockSize; i++) {
            productNames[i] = new String[catalogueSizes[i]];
            insertionDate[i] = new Date[catalogueSizes[i]];
        }
    }

    public void addProduct(String productName, int stockIndex, int catalogueIndex) {
        productNames[stockIndex][catalogueIndex] = productName;
        insertionDate[stockIndex][catalogueIndex] = Date.from(Instant.now());
        count++;
    }

    public void updateProduct(String oldProductName, String newProductName, int stockIndex) {
        for (int i = 0; i < productNames[stockIndex].length; i++) {
            if (productNames[stockIndex][i] != null &&
                productNames[stockIndex][i].equalsIgnoreCase(oldProductName)) {
                productNames[stockIndex][i] = newProductName;
                break;
            }
        }
    }

    public void deleteProduct(String productName, int stockIndex) {
        for (int i = 0; i < productNames[stockIndex].length; i++) {
            if (productNames[stockIndex][i] != null &&
                productNames[stockIndex][i].equalsIgnoreCase(productName)) {
                productNames[stockIndex][i] = null;
                insertionDate[stockIndex][i] = null;
                break;
            }
        }
    }

    // Data access methods
    public boolean isProductExists(int stockIndex, String productName) {
        for (int i = 0; i < productNames[stockIndex].length; i++) {
            if (productNames[stockIndex][i] != null &&
                productNames[stockIndex][i].equalsIgnoreCase(productName)) {
                return false;
            }
        }
        return true;
    }

    public boolean isCatalogueOccupied(int stockIndex, int catalogueIndex) {
        return productNames[stockIndex][catalogueIndex] != null;
    }

    public boolean isStockFull(int stockIndex) {
        for (int i = 0; i < productNames[stockIndex].length; i++) {
            if (productNames[stockIndex][i] == null) {
                return true;
            }
        }
        return false;
    }

    public boolean hasProducts(int stockIndex) {
        for (int i = 0; i < productNames[stockIndex].length; i++) {
            if (productNames[stockIndex][i] != null) {
                return true;
            }
        }
        return false;
    }

    public int getCatalogueSize(int stockIndex) {
        return productNames[stockIndex].length;
    }

    public String[][] getAllProductNames() {
        return productNames;
    }

    public Date[][] getAllInsertionDates() {
        return insertionDate;
    }

    public boolean hasAnyProducts() {
        return count > 0;
    }
}