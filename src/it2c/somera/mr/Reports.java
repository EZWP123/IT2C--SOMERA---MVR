package it2c.somera.mr;

import java.util.Scanner;

public class Reports {

    // Method to generate the general report
    public void generateGeneralReport() {
        config conf = new config();

        String qry = "SELECT "
                   + "COUNT(*) AS TotalReservations, "
                   + "SUM(CASE WHEN s_status = 'Done' THEN 1 ELSE 0 END) AS CompletedReservations, "
                   + "SUM(CASE WHEN s_status = 'Pending' THEN 1 ELSE 0 END) AS PendingReservations, "
                   + "SUM(s_rcash) AS TotalEarnings "
                   + "FROM tbl_reservation";

        System.out.println("=========================================");
        System.out.println("General Report: Summary of Reservations");
        System.out.println("=========================================");
        String[] headers = {"Total Reservations", "Completed Reservations", "Pending Reservations", "Total Earnings"};
        String[] columns = {"TotalReservations", "CompletedReservations", "PendingReservations", "TotalEarnings"};
        conf.viewRecords(qry, headers, columns);
        System.out.println("-----------------------------------------");
    }

   public void generateSpecificGuestReport() {
    config conf = new config();
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter Guest ID: ");
    int guestId = sc.nextInt();

    // Query to fetch guest details
    String guestQry = "SELECT r_id, r_fname, r_lname, r_email, r_contact "
                    + "FROM Renting "
                    + "WHERE r_id = ?";
    String[] guestFields = {"r_id", "r_fname", "r_lname", "r_email", "r_contact"};
    String[] guestDetails = conf.getSingleRecord(guestQry, guestFields, guestId);

    if (guestDetails == null) {
        System.out.println("No guest found with ID: " + guestId);
        return;
    }

    // Display guest details
    System.out.println("===================================================");
    System.out.println("Individual Report for Customer: " + guestDetails[0]);
    System.out.println("===================================================");
    System.out.println("Name           : " + guestDetails[2] + ", " + guestDetails[1]);
    System.out.println("Email        : " + guestDetails[3]);
    System.out.println("Contact        : " + guestDetails[4]);
    
    System.out.println("===================================================");

    // Query to fetch guest transactions
    String transactionsQry = "SELECT s_id, m_name, s_date, s_rcash, s_status "
                           + "FROM tbl_reservation "
                           + "LEFT JOIN tbl_movies ON tbl_movies.m_id = tbl_reservation.m_id "
                           + "WHERE tbl_reservation.r_id = ?";
    String[] transactionFields = {"s_id", "m_name", "s_date", "s_rcash", "s_status"};
    String[][] transactions = conf.getMultipleRecords(transactionsQry, transactionFields, guestId);

    if (transactions == null || transactions.length == 0) {
        System.out.println("No transactions found for Guest ID: " + guestId);
    } else {
        // Display transactions in a formatted table
        System.out.println("Transactions for Customer ID: " + guestId);
        System.out.println("========================================================================================");
        System.out.printf("| %-15s | %-20s | %-12s | %-12s | %-12s |\n",
                "Transaction ID", "Movie", "Date", "Cash Received", "Status");
        System.out.println("----------------------------------------------------------------------------------------");

        for (String[] transaction : transactions) {
            System.out.printf("| %-15s | %-20s | %-12s | %-12s | %-12s |\n",
                    transaction[0], transaction[1], transaction[2], transaction[3], transaction[4]);
        }

        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("End of Report");
        System.out.println("========================================================================================");
    }
}

    public void reportMenu() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("===================================");
            System.out.println("          REPORT MENU              ");
            System.out.println("===================================");
            System.out.println("1. Generate General Report");
            System.out.println("2. Generate Specific Guest Report");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    generateGeneralReport();
                    break;
                case 2:
                    generateSpecificGuestReport();
                    break;
   
                case 3:
                    System.out.println("Exiting Report Generation Panel...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println("Do you want to generate another report? (yes/no)");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));
    }
}
