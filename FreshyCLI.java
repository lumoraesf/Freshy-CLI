package FreshyCLI;

import java.util.Scanner;

public class FreshyCLI {

    private static final String INVENTORY_FILE = "inventory.csv";
    private static final String INVENTORY_BACKUP = "inventory_backup.csv";
    private static final String USERS_FILE = "users.csv";

    public static void main(String[] args) {
        InventoryManager inv = new InventoryManager(INVENTORY_FILE);
        AccountManager acc = new AccountManager(USERS_FILE);

        inv.backup(INVENTORY_BACKUP);
        inv.loadFromCSV();
        acc.loadFromCSV();

        System.out.println("=================================");
        System.out.println("   Welcome to Freshy CLI v1.0   ");
        System.out.println("  Perishable Food Management     ");
        System.out.println("=================================");

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Login ---");
            User user = null;
            int attempts = 0;
            while (user == null) {
                user = acc.login(sc);
                if (user == null) {
                    attempts++;
                    if (attempts >= 3) {
                        System.out.println("Too many failed attempts. Exiting.");
                        sc.close();
                        return;
                    }
                    System.out.println("Invalid credentials. Try again.");
                }
            }

            System.out.println("\nLogged in as: " + user.getUsername() + " [" + user.getRole() + "]");

            boolean loggedIn = true;
            while (loggedIn) {
                loggedIn = user.handleMenu(sc, inv, acc);
            }

            System.out.print("\nReturn to login? (y/n): ");
            String again = sc.nextLine().trim().toLowerCase();
            if (!again.equals("y")) running = false;
        }

        System.out.println("Goodbye!");
        sc.close();
    }
}
