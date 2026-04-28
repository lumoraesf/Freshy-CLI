package FreshyCLI;

import java.util.Scanner;

public class User {
    protected String username;
    protected String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }

    public boolean handleMenu(Scanner sc, InventoryManager inv, AccountManager acc) {
        System.out.println("\n=== NORMAL USER MENU ===");
        System.out.println("1. View items");
        System.out.println("2. Export CSV");
        System.out.println("3. Logout");
        System.out.print("Select: ");
        String choice = sc.nextLine().trim();
        switch (choice) {
            case "1": inv.printAll(); break;
            case "2": inv.exportCSV("export.csv"); break;
            case "3": return false;
            default: System.out.println("Invalid option.");
        }
        return true;
    }

    public String toCSV(String password) {
        return username + "," + password + "," + role;
    }
}
