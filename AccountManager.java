package FreshyCLI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountManager {
    private List<String[]> accounts = new ArrayList<>(); // [username, password, role]
    private String csvPath;

    public AccountManager(String csvPath) {
        this.csvPath = csvPath;
    }

    public void loadFromCSV() {
        File file = new File(csvPath);
        if (!file.exists()) {
            seedDefaultAdmin();
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                String[] parts = line.split(",");
                if (parts.length == 3) accounts.add(parts);
            }
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
        if (accounts.isEmpty()) seedDefaultAdmin();
    }

    private void seedDefaultAdmin() {
        accounts.add(new String[]{"admin", "admin123", "ADMIN"});
        saveToCSV();
    }

    public void saveToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(csvPath))) {
            pw.println("username,password,role");
            for (String[] a : accounts) pw.println(a[0] + "," + a[1] + "," + a[2]);
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public User login(Scanner sc) {
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        for (String[] a : accounts) {
            if (a[0].equals(username) && a[1].equals(password)) {
                return buildUser(a[0], a[2]);
            }
        }
        return null;
    }

    private User buildUser(String username, String role) {
        switch (role.toUpperCase()) {
            case "ADMIN": return new AdminUser(username, role);
            case "POWER": return new PowerUser(username, role);
            default: return new User(username, role);
        }
    }

    public void manageAccounts(Scanner sc) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Manage Accounts ---");
            System.out.println("1. List accounts");
            System.out.println("2. Create account");
            System.out.println("3. Delete account");
            System.out.println("4. Back");
            System.out.print("Select: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": listAccounts(); break;
                case "2": createAccount(sc); break;
                case "3": deleteAccount(sc); break;
                case "4": running = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void listAccounts() {
        System.out.println("\n--- Accounts ---");
        for (int i = 0; i < accounts.size(); i++) {
            String[] a = accounts.get(i);
            System.out.println((i + 1) + ". " + a[0] + " [" + a[2] + "]");
        }
    }

    private void createAccount(Scanner sc) {
        System.out.print("New username: ");
        String username = sc.nextLine().trim();
        if (username.isEmpty()) { System.out.println("Username cannot be empty."); return; }
        for (String[] a : accounts) {
            if (a[0].equals(username)) { System.out.println("Username already exists."); return; }
        }
        System.out.print("Password: ");
        String password = sc.nextLine().trim();
        if (password.isEmpty()) { System.out.println("Password cannot be empty."); return; }
        System.out.print("Role (NORMAL/POWER/ADMIN): ");
        String role = sc.nextLine().trim().toUpperCase();
        if (!role.equals("NORMAL") && !role.equals("POWER") && !role.equals("ADMIN")) {
            System.out.println("Invalid role. Must be NORMAL, POWER, or ADMIN.");
            return;
        }
        accounts.add(new String[]{username, password, role});
        saveToCSV();
        System.out.println("Account created.");
    }

    private void deleteAccount(Scanner sc) {
        listAccounts();
        if (accounts.isEmpty()) return;
        System.out.print("Account number to delete: ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (idx < 0 || idx >= accounts.size()) { System.out.println("Invalid number."); return; }
            System.out.println("Deleted account: " + accounts.get(idx)[0]);
            accounts.remove(idx);
            saveToCSV();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
}
