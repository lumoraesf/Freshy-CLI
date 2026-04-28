package FreshyCLI;

import java.util.Scanner;

public class PowerUser extends User {

    public PowerUser(String username, String role) {
        super(username, role);
    }

    @Override
    public boolean handleMenu(Scanner sc, InventoryManager inv, AccountManager acc) {
        System.out.println("\n=== POWER USER MENU ===");
        System.out.println("1. View items");
        System.out.println("2. Export CSV");
        System.out.println("3. Create item");
        System.out.println("4. Modify item");
        System.out.println("5. Delete item");
        System.out.println("6. View metrics");
        System.out.println("7. Logout");
        System.out.print("Select: ");
        String choice = sc.nextLine().trim();
        switch (choice) {
            case "1": inv.printAll(); break;
            case "2": inv.exportCSV("export.csv"); break;
            case "3": inv.create(sc); break;
            case "4": inv.modify(sc); break;
            case "5": inv.delete(sc); break;
            case "6": inv.printMetrics(); break;
            case "7": return false;
            default: System.out.println("Invalid option.");
        }
        return true;
    }
}
