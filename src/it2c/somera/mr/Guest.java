package it2c.somera.mr;

import java.util.Scanner;

public class Guest {

    public void gtransaction() {
        Scanner sc = new Scanner(System.in);
        String response;
        do {
            System.out.println("GUEST PANEL");
            System.out.println("1. ADD GUEST");
            System.out.println("2. VIEW GUEST");
            System.out.println("3. UPDATE GUEST");
            System.out.println("4. DELETE GUEST");
            System.out.println("5. EXIT");

            int act = 0;
            while (true) {
                System.out.print("Enter action (1-5): ");
                if (sc.hasNextInt()) {
                    act = sc.nextInt();
                    if (act >= 1 && act <= 5) break;
                }
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                sc.nextLine();
            }

            Guest cs = new Guest();
            switch (act) {
                case 1:
                    cs.addGuest();
                    cs.viewGuest();
                    break;
                case 2:
                    cs.viewGuest();
                    break;
                case 3:
                    cs.viewGuest();
                    cs.updateGuest();
                    cs.viewGuest();
                    break;
                case 4:
                    cs.viewGuest();
                    cs.deleteGuest();
                    cs.viewGuest();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid action. Please try again.");
                    break;
            }

            System.out.print("Continue in Guest Panel? (yes/No): ");
            response = sc.next();

            if (!response.equalsIgnoreCase("yes")) {
                System.out.println("Going back to main menu...");
                return;
            }
        } while (response.equalsIgnoreCase("yes"));
        System.out.println("Thank you.");
    }

    public void addGuest() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        String fname = getValidatedStringInput("Enter First Name: ", sc);
        String lname = getValidatedStringInput("Enter Last Name: ", sc);

        String email;
        while (true) {
            System.out.print("Enter Email: ");
            email = sc.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("Email cannot be empty. Please try again.");
                continue;
            }
            if (email.contains("@") && email.contains(".")) break;
            System.out.println("Invalid email format. Please try again.");
        }

        String contact;
        while (true) {
            System.out.print("Enter Contact No.: ");
            contact = sc.nextLine().trim();
            if (contact.isEmpty()) {
                System.out.println("Contact number cannot be empty. Please try again.");
                continue;
            }
            if (contact.matches("\\d+")) break;
            System.out.println("Invalid contact number. Please enter only digits.");
        }

        String sql = "INSERT INTO Renting (r_fname, r_lname, r_email, r_contact) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, email, contact);
    }

    public void viewGuest() {
        config conf = new config();
        String cqry = "SELECT * FROM Renting";
        String[] hrds = {"ID", "First Name", "Last Name", "Email", "Contact"};
        String[] clmns = {"r_id", "r_fname", "r_lname", "r_email", "r_contact"};
        conf.viewRecords(cqry, hrds, clmns);
    }

    private void updateGuest() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        while (true) {
            System.out.print("Enter ID to update: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT r_id FROM Renting WHERE r_id = ?", id) != 0) break;
                System.out.println("Selected ID does not exist. Please try again.");
            } else {
                System.out.println("Invalid input. Please enter a valid ID.");
                sc.nextLine(); 
            }
        }

        String nfname = getValidatedStringInput("Enter new first name: ", sc);
        String nlname = getValidatedStringInput("Enter new last name: ", sc);

        String nemail;
        while (true) {
            System.out.print("Enter new email: ");
            nemail = sc.nextLine().trim();
            if (nemail.isEmpty()) {
                System.out.println("Email cannot be empty. Please try again.");
                continue;
            }
            if (nemail.contains("@") && nemail.contains(".")) break;
            System.out.println("Invalid email format. Please try again.");
        }

        String ncontact;
        while (true) {
            System.out.print("Enter new contact: ");
            ncontact = sc.nextLine().trim();
            if (ncontact.isEmpty()) {
                System.out.println("Contact number cannot be empty. Please try again.");
                continue;
            }
            if (ncontact.matches("\\d+")) break;
            System.out.println("Invalid contact number. Please enter only digits.");
        }

        String qry = "UPDATE Renting SET r_fname = ?, r_lname = ?, r_email = ?, r_contact = ? WHERE r_id = ?";
        conf.updateRecord(qry, nfname, nlname, nemail, ncontact, id);
    }

    public void deleteGuest() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        while (true) {
            System.out.print("Enter ID to delete: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT r_id FROM Renting WHERE r_id = ?", id) != 0) break;
                System.out.println("Selected ID does not exist. Please try again.");
            } else {
                System.out.println("Invalid input. Please enter a valid ID.");
                sc.nextLine();
            }
        }

        String qry = "DELETE FROM Renting WHERE r_id = ?";
        conf.deleteRecord(qry, String.valueOf(id));
    }

    
    private String getValidatedStringInput(String prompt, Scanner sc) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }
}
