package it2c.somera.mr;

import java.util.Scanner;

public class Movies {
    public void mtransaction() {
        Scanner sc = new Scanner(System.in);
        String response;
        do {
            System.out.println("\n.......................................................................");
            System.out.println("MOVIE PANEL");
            System.out.println("1. ADD MOVIES");
            System.out.println("2. VIEW MOVIES ");
            System.out.println("3. UPDATE MOVIES");
            System.out.println("4. DELETE MOVIES");
            System.out.println("5. EXIT");
            System.out.println("Enter action:");
            
            int act = getValidatedIntInput("Enter action (1-5): ", sc);

            Movies mv = new Movies();
            switch (act) {
                case 1:
                    mv.addMovies();
                    mv.viewMovies();
                    break;
                case 2:
                    mv.viewMovies();
                    break;
                case 3:
                    mv.viewMovies();
                    mv.updateMovies();
                    mv.viewMovies();
                    break;
                case 4:
                    mv.viewMovies();
                    mv.deleteMovies();
                    mv.viewMovies();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid action. Please try again.");
                    break;
            }

            System.out.print("Continue in Movie Panel? (yes/No): ");
            response = sc.next().trim();

        } while (response.equalsIgnoreCase("yes"));
        System.out.println("Thank you!");
    }

    public void addMovies() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        String mname = getValidatedStringInput("Enter Movie Name: ", sc);
        String mgenre = getValidatedStringInput("Enter Movie Genre: ", sc);
        String mduration = getValidatedIntInput("Enter Movie Duration (in minutes): ", sc) + "";
        String price = getValidatedIntInput("Enter Movie Price: ", sc) + "";

        String mstatus;
        while (true) {
            System.out.println("Enter Movie Status (available/not available): ");
            mstatus = sc.nextLine().trim().toLowerCase();
            if (mstatus.equals("available") || mstatus.equals("not available")) {
                break;
            }
            System.out.println("Invalid input! Please type 'available' or 'not available'.");
        }

        String qry = "INSERT INTO tbl_movies (m_name, m_genre, m_duration, m_price, m_status) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(qry, mname, mgenre, mduration, price, mstatus);
        System.out.println("Movie added successfully!");
    }

    public void viewMovies() {
        config conf = new config();
        String cqry = "SELECT * FROM tbl_movies";
        String[] hrds = {"ID", "Movie Name", "Movie Genre", "Movie Duration", "Movie Price", "Movie Status"};
        String[] clmns = {"m_id", "m_name", "m_genre", "m_duration", "m_price", "m_status"};
        conf.viewRecords(cqry, hrds, clmns);
    }

    public void viewAvailableMovies() {
        config conf = new config();
        String cqry = "SELECT * FROM tbl_movies WHERE m_status = 'available'";
        String[] hrds = {"ID", "Movie Name", "Movie Genre", "Movie Duration", "Movie Price", "Movie Status"};
        String[] clmns = {"m_id", "m_name", "m_genre", "m_duration", "m_price", "m_status"};
        conf.viewRecords(cqry, hrds, clmns);
    }

    private void updateMovies() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int id = getValidatedIntInput("Enter ID to update: ", sc);
        while (conf.getSingleValue("SELECT m_id FROM tbl_movies WHERE m_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist. Please try again.");
            id = getValidatedIntInput("Enter ID to update: ", sc);
        }

        String price = getValidatedIntInput("Enter new Movie Price: ", sc) + "";
        String mstatus;
        while (true) {
            System.out.println("Enter new Movie Status (available/not available): ");
            mstatus = sc.nextLine().trim().toLowerCase();
            if (mstatus.equals("available") || mstatus.equals("not available")) {
                break;
            }
            System.out.println("Invalid input! Please type 'available' or 'not available'.");
        }

        String qry = "UPDATE tbl_movies SET m_price = ?, m_status = ? WHERE m_id = ?";
        conf.updateRecord(qry, price, mstatus, id);
        System.out.println("Movie updated successfully!");
    }

    public void deleteMovies() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int id = getValidatedIntInput("Enter ID to delete: ", sc);
        while (conf.getSingleValue("SELECT m_id FROM tbl_movies WHERE m_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist. Please try again.");
            id = getValidatedIntInput("Enter ID to delete: ", sc);
        }

        String qry = "DELETE FROM tbl_movies WHERE m_id = ?";
        conf.deleteRecord(qry, String.valueOf(id));
        System.out.println("Movie deleted successfully!");
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

   
    private int getValidatedIntInput(String prompt, Scanner sc) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (!input.isEmpty() && input.matches("\\d+")) {
                return Integer.parseInt(input);
            }
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }
}
