package FreshyCLI;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FoodItem {
    private String name;
    private int quantity;
    private LocalDate expirationDate;

    public FoodItem(String name, int quantity, LocalDate expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public LocalDate getExpirationDate() { return expirationDate; }

    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    public boolean isExpired() {
        return expirationDate.isBefore(LocalDate.now());
    }

    public boolean expiresWithinDays(int days) {
        long daysUntil = ChronoUnit.DAYS.between(LocalDate.now(), expirationDate);
        return daysUntil >= 0 && daysUntil <= days;
    }

    public String toCSV() {
        return name + "," + quantity + "," + expirationDate.toString();
    }

    public static FoodItem fromCSV(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) return null;
        try {
            String name = parts[0].trim();
            int quantity = Integer.parseInt(parts[1].trim());
            LocalDate date = LocalDate.parse(parts[2].trim());
            return new FoodItem(name, quantity, date);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        String status = isExpired() ? "[EXPIRED]" : expiresWithinDays(3) ? "[EXPIRING SOON]" : "";
        return String.format("%-20s qty:%-5d exp:%s %s", name, quantity, expirationDate, status);
    }
}
