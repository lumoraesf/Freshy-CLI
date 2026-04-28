package FreshyCLI;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryManager {
    private List<FoodItem> items = new ArrayList<>();
    private String csvPath;

    public InventoryManager(String csvPath) {
        this.csvPath = csvPath;
    }

    public void loadFromCSV() {
        File file = new File(csvPath);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; } // skip header
                FoodItem item = FoodItem.fromCSV(line);
                if (item != null) items.add(item);
            }
        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }

    public void saveToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(csvPath))) {
            pw.println("name,quantity,expirationDate");
            for (FoodItem item : items) {
                pw.println(item.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    public void backup(String backupPath) {
        File src = new File(csvPath);
        if (!src.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(src));
             PrintWriter pw = new PrintWriter(new FileWriter(backupPath))) {
            String line;
            while ((line = br.readLine()) != null) pw.println(line);
        } catch (IOException e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }

    public void printAll() {
        if (items.isEmpty()) {
            System.out.println("No items in inventory.");
            return;
        }
        System.out.println("\n--- Inventory ---");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
    }

    public void create(Scanner sc) {
        System.out.print("Item name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }

        int qty = promptInt(sc, "Quantity: ");
        if (qty < 0) return;

        LocalDate date = promptDate(sc, "Expiration date (yyyy-MM-dd): ");
        if (date == null) return;

        items.add(new FoodItem(name, qty, date));
        saveToCSV();
        System.out.println("Item added.");
    }

    public void modify(Scanner sc) {
        printAll();
        if (items.isEmpty()) return;
        int idx = promptInt(sc, "Item number to modify: ") - 1;
        if (idx < 0 || idx >= items.size()) { System.out.println("Invalid number."); return; }

        FoodItem item = items.get(idx);
        System.out.println("Editing: " + item.getName());

        System.out.print("New name (enter to keep '" + item.getName() + "'): ");
        String name = sc.nextLine().trim();
        if (!name.isEmpty()) item.setName(name);

        System.out.print("New quantity (enter to keep " + item.getQuantity() + "): ");
        String qtyStr = sc.nextLine().trim();
        if (!qtyStr.isEmpty()) {
            try { item.setQuantity(Integer.parseInt(qtyStr)); }
            catch (NumberFormatException e) { System.out.println("Invalid quantity, keeping old value."); }
        }

        System.out.print("New expiration date (enter to keep " + item.getExpirationDate() + "): ");
        String dateStr = sc.nextLine().trim();
        if (!dateStr.isEmpty()) {
            try { item.setExpirationDate(LocalDate.parse(dateStr)); }
            catch (DateTimeParseException e) { System.out.println("Invalid date, keeping old value."); }
        }

        saveToCSV();
        System.out.println("Item updated.");
    }

    public void delete(Scanner sc) {
        printAll();
        if (items.isEmpty()) return;
        int idx = promptInt(sc, "Item number to delete: ") - 1;
        if (idx < 0 || idx >= items.size()) { System.out.println("Invalid number."); return; }
        System.out.println("Deleted: " + items.get(idx).getName());
        items.remove(idx);
        saveToCSV();
    }

    public void exportCSV(String outputPath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(outputPath))) {
            pw.println("name,quantity,expirationDate");
            for (FoodItem item : items) pw.println(item.toCSV());
            System.out.println("Exported to " + outputPath);
        } catch (IOException e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    public void printMetrics() {
        long total = items.size();
        long expiringSoon = items.stream().filter(i -> i.expiresWithinDays(3)).count();
        long expired = items.stream().filter(FoodItem::isExpired).count();
        System.out.println("\n--- Metrics ---");
        System.out.println("Total items in stock : " + total);
        System.out.println("Expiring in <= 3 days: " + expiringSoon);
        System.out.println("Already expired      : " + expired);
    }

    private int promptInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return -1;
        }
    }

    private LocalDate promptDate(Scanner sc, String prompt) {
        System.out.print(prompt);
        try {
            return LocalDate.parse(sc.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
            return null;
        }
    }
}
