package ui;

import java.io.FileNotFoundException;
import logic.InventoryManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        String filePath = args.length > 0 ? args[0] : "data/sillas_completo.txt";

        InventoryManager inventory = new InventoryManager();
        InventoryConsole console = new InventoryConsole();
        try {
            inventory.loadChairs(filePath);
            console.displayResults(inventory.getChairs());
        } catch (FileNotFoundException exception) {
            System.err.println("File not found: " + filePath);
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
