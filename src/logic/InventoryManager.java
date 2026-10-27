package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.Chair;
import model.ExecutiveChair;
import model.PresidentialChair;
import model.SecretarialChair;
import model.Traction;
import model.Wheelchair;
import model.Massager;
import model.Tandem;

public class InventoryManager {
    private List<Chair> chairs;

    public InventoryManager() {
        chairs = new ArrayList<>();
    }

    public List<Chair> getChairs() {
        return List.copyOf(chairs);
    }

    public void loadChairs(String filePath) throws FileNotFoundException {
        List<Chair> loadedChairs = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(new File(filePath), "UTF-8")) {
            int lineNumber = 0;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                lineNumber++;

                if (line.isEmpty()) {
                    continue;
                }

                try {
                    loadedChairs.add(parseChair(line));
                } catch (IllegalArgumentException exception) {
                    throw new IllegalArgumentException(
                            "Invalid data on line " + lineNumber + ": " + exception.getMessage(),
                            exception);
                }
            }
        }
        chairs = loadedChairs;
    }

    private Chair parseChair(String line) {
        try (Scanner lineScanner = new Scanner(line)) {
            lineScanner.useDelimiter("\\s*,\\s*");
            lineScanner.useLocale(Locale.US);

            String reference = lineScanner.next().trim();
            String category = lineScanner.next().trim();
            int price = lineScanner.nextInt();
            double rating = lineScanner.nextDouble();

            Chair chair;
            switch (category) {
                case "Presidencial":
                    String origin = lineScanner.next().trim();
                    boolean imported;

                    if (origin.equalsIgnoreCase("si")) {
                        imported = true;
                    } else if (origin.equalsIgnoreCase("no")) {
                        imported = false;
                    } else {
                        throw new IllegalArgumentException(
                                "Invalid origin: " + origin);
                    }

                    chair = new PresidentialChair(
                            reference, price, rating, imported);
                    break;

                case "Gerencial":
                    chair = new ExecutiveChair(reference, price, rating);
                    break;

                case "Secretarial":
                    chair = new SecretarialChair(reference, price, rating);
                    break;

                case "De Ruedas":
                    Traction traction = Traction.valueOf(
                            lineScanner.next().trim().toUpperCase(Locale.ROOT));

                    chair = new Wheelchair(reference, price, rating, traction);
                    break;

                case "Tándem":
                    int numberOfSeats = lineScanner.nextInt();

                    chair = new Tandem(reference, price, rating, numberOfSeats);
                    break;

                case "Masajeadora":
                    int usageModes = lineScanner.nextInt();
                    String calefaction = lineScanner.next().trim();

                    chair = new Massager(reference, price, rating, usageModes, calefaction);
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Unknown category: " + category);
            }

            if (lineScanner.hasNext()) {
                throw new IllegalArgumentException("Unexpected extra fields.");
            }
            return chair;
        } catch (java.util.NoSuchElementException exception) {
            throw new IllegalArgumentException(
                    "Missing fields or invalid numeric values.", exception);
        }
    }
}