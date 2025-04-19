package co.antony;

import co.antony.controller.StockController;
import co.antony.model.repository.ProductRepository;
import co.antony.model.service.ProductService;
import co.antony.view.MenuView;
import co.antony.view.ProductView;
import co.antony.view.StockDisplayManager;
import co.antony.view.util.InputReader;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);

        ProductRepository repository = new ProductRepository();
        ProductService service = new ProductService(repository);

        InputReader inputReader = new InputReader(scanner);
        StockDisplayManager displayManager = new StockDisplayManager();
        MenuView menuView = new MenuView(inputReader);
        ProductView productView = new ProductView(inputReader, displayManager, menuView);

        StockController controller = new StockController(service, menuView, productView, displayManager);

        try {
            controller.runApplication();
        } finally {
            scanner.close();
        }
    }
}