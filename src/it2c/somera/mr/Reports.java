package it2c.somera.mr;
import java.util.Scanner;
public class Reports {
    public void generateReport() {
        Scanner sc = new Scanner(System.in);
        String response;
        
        do {
            System.out.println("REPORT PANEL");
            System.out.println("1. General Report (Overall Summary)");
            System.out.println("2. Specific Report (Detailed Records)");
            System.out.println("3. EXIT");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            
            switch (choice) {
                case 1:
                    generalReport();
                    break;
                case 2:
                    specificReport();
                    break;
                case 3:
                    System.out.println("Exiting the report panel...");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
            
            System.out.println("Do you want to generate another report? (yes/no)");
            response = sc.next();
            
        } while (response.equalsIgnoreCase("yes"));
        
        System.out.println("Thank you for using the report panel!");
    }
   
    private void generalReport() {
        config conf = new config();
        System.out.println("\n---------------------- General Report ----------------------");
       
        String totalReservationsQry = "SELECT COUNT(*) FROM tbl_reservation";
        double totalReservations = conf.getSingleValue(totalReservationsQry);
        System.out.println("Total Reservations: " + (int) totalReservations);
        
        String completedReservationsQry = "SELECT COUNT(*) FROM tbl_reservation WHERE s_status = 'Done'";
        double completedReservations = conf.getSingleValue(completedReservationsQry);
        System.out.println("Completed Reservations: " + (int) completedReservations);
   
        String pendingReservationsQry = "SELECT COUNT(*) FROM tbl_reservation WHERE s_status = 'Pending'";
        double pendingReservations = conf.getSingleValue(pendingReservationsQry);
        System.out.println("Pending Reservations: " + (int) pendingReservations);
     
        String totalEarningsQry = "SELECT SUM(s_rcash) FROM tbl_reservation";
        double totalEarnings = conf.getSingleValue(totalEarningsQry);
        System.out.println("Total Earnings: PHP " + totalEarnings);
        System.out.println("-----------------------------------------------------------\n");
    }
    
     
    private void specificReport() {
        config conf = new config();
        Scanner sc = new Scanner(System.in);
        System.out.println("\n---------------------- Specific Report ----------------------");
        System.out.println("1. View Detailed Reservation List");
        System.out.println("2. View Reservation by Guest ID");
        System.out.println("3. View Reservation by Date");
        System.out.print("Choose a specific report type: ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                String qry = "SELECT s_id, r_lname, m_name, m_price, s_rcash, s_date, s_status FROM tbl_reservation " +
                             "LEFT JOIN Renting ON Renting.r_id = tbl_reservation.r_id " +
                             "LEFT JOIN tbl_movies ON tbl_movies.m_id = tbl_reservation.m_id";
                String[] headers = {"Reservation ID", "Guest Name", "Movie", "Price", "Cash Received", "Date", "Status"};
                String[] columns = {"s_id", "r_lname", "m_name", "m_price", "s_rcash", "s_date", "s_status"};
                conf.viewRecords(qry, headers, columns);
                break;
            case 2:
                System.out.print("Enter Guest ID: ");
                int guestId = sc.nextInt();
                String guestQry = "SELECT s_id, r_lname, m_name, m_price, s_rcash, s_date, s_status FROM tbl_reservation " +
                                  "LEFT JOIN Renting ON Renting.r_id = tbl_reservation.r_id " +
                                  "LEFT JOIN tbl_movies ON tbl_movies.m_id = tbl_reservation.m_id " +
                                  "WHERE tbl_reservation.r_id = ?";
                String[] guestHeaders = {"Reservation ID", "Guest Name", "Movie", "Price", "Cash Received", "Date", "Status"};
                String[] guestColumns = {"s_id", "r_lname", "m_name", "m_price", "s_rcash", "s_date", "s_status"};
                conf.viewRecords(guestQry, guestHeaders, guestColumns, guestId);
                break;
            case 3:
              
                System.out.print("Enter Date (MM/dd/yyyy): ");
                sc.nextLine(); 
                String date = sc.nextLine();
                String dateQry = "SELECT s_id, r_lname, m_name, m_price, s_rcash, s_date, s_status FROM tbl_reservation " +
                                 "LEFT JOIN Renting ON Renting.r_id = tbl_reservation.r_id " +
                                 "LEFT JOIN tbl_movies ON tbl_movies.m_id = tbl_reservation.m_id " +
                                 "WHERE s_date = ?";
                String[] dateHeaders = {"Reservation ID", "Guest Name", "Movie", "Price", "Cash Received", "Date", "Status"};
                String[] dateColumns = {"s_id", "r_lname", "m_name", "m_price", "s_rcash", "s_date", "s_status"};
                conf.viewRecords(dateQry, dateHeaders, dateColumns, date);
                break;
            default:
                System.out.println("Invalid choice! Returning to main menu.");
        }
        System.out.println("------------------------------------------------------------\n");
    }  
}
