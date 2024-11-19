package it2c.somera.mr;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Reservation {

    public void rtransaction() {
        Scanner sc = new Scanner(System.in);
        String response;
        do {
            System.out.println("TRANSACTION PANEL");
            System.out.println("1. ADD RESERVATION");
            System.out.println("2. VIEW RESERVATION");
            System.out.println("3. UPDATE RESERVATION");
            System.out.println("4. DELETE RESERVATION");
            System.out.println("5. EXIT");
            System.out.print("Enter action: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                sc.next();
            }
            int act = sc.nextInt();

            Reservation rs = new Reservation();
            switch (act) {
                case 1:
                    rs.addReservation();
                    rs.viewReservation();
                    break;

                case 2:
                    rs.viewReservation();
                    break;

                case 3:
                    rs.viewReservation();
                    rs.updateReservation();
                    rs.viewReservation();
                    break;

                case 4:
                    rs.viewReservation();
                    rs.deleteReservation();
                    rs.viewReservation();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid action. Please try again.");
                    break;
            }

            System.out.print("Continue? (yes/No): ");
            response = sc.next();

        } while (response.equalsIgnoreCase("yes"));
        System.out.println("Thank you!");
    }

   private void addReservation() {
    config conf = new config();
    Scanner sc = new Scanner(System.in);
    Guest cs = new Guest();
    cs.viewGuest();

    int gid;
    while (true) {
        System.out.print("Select the ID of Guest: ");
        String input = sc.nextLine().trim();
        if (!input.isEmpty() && input.matches("\\d+")) {
            gid = Integer.parseInt(input);
            if (conf.getSingleValue("SELECT r_id FROM Renting WHERE r_id = ?", gid) != 0) {
                break;
            }
        }
        System.out.println("Invalid or non-existent Guest ID. Please try again.");
    }

    Movies mv = new Movies();
    mv.viewAvailableMovies();

    int mid;
    while (true) {
        System.out.print("Enter ID of Movies: ");
        String input = sc.nextLine().trim();
        if (!input.isEmpty() && input.matches("\\d+")) {
            mid = Integer.parseInt(input);
            String availabilityQuery = "SELECT m_id FROM tbl_movies WHERE m_id = ? AND m_status = 'available'";
            if (conf.getSingleValue(availabilityQuery, mid) != 0) {
                break;
            }
        }
        System.out.println("Invalid Movie ID or Movie is not available. Please try again.");
    }

    double rcash;
    double price = conf.getSingleValue("SELECT m_price FROM tbl_movies WHERE m_id = ?", mid);
    while (true) {
        System.out.print("Enter Cash Received: ");
        String input = sc.nextLine().trim();
        if (!input.isEmpty() && input.matches("\\d+(\\.\\d+)?")) {
            rcash = Double.parseDouble(input);
            if (rcash >= price) {
                break;
            }
        }
        System.out.println("Invalid amount. Cash received must be at least " + price + ". Please try again.");
    }

    System.out.println("Cash Received: " + rcash);
    double change = rcash - price;
    System.out.println("Change: " + change);

    LocalDate currdate = LocalDate.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String date = currdate.format(format);

    String status = "PENDING";
    String reservationqry = "INSERT INTO tbl_reservation (r_id, m_id, s_rcash, s_date, s_status) VALUES (?, ?, ?, ?, ?)";
    conf.addRecord(reservationqry, gid, mid, rcash, date, status);
}


    public void viewReservation() {
        String qry = "SELECT s_id, r_lname, m_name, m_price, s_rcash - m_price AS change, m_duration, s_date, s_status " +
                     "FROM tbl_reservation " +
                     "LEFT JOIN Renting ON Renting.r_id = tbl_reservation.r_id " +
                     "LEFT JOIN tbl_movies ON tbl_movies.m_id = tbl_reservation.m_id";
        String[] hrds = {"Reservation ID", "Last Name", "Movies", "Price", "Change", "Duration", "Date", "Status"};
        String[] clmns = {"s_id", "r_lname", "m_name", "m_price", "change", "m_duration", "s_date", "s_status"};
        config conf = new config();
        conf.viewRecords(qry, hrds, clmns);
    }
    private void updateStatusesAutomatically() {
    config conf = new config();
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

   
    String fetchPendingQuery = "SELECT s_id, s_date FROM tbl_reservation WHERE s_status = 'Pending'";
    List<Map<String, String>> pendingReservations = conf.fetchRecords(fetchPendingQuery);

    for (Map<String, String> reservation : pendingReservations) {
        int reservationId = Integer.parseInt(reservation.get("s_id"));
        String reservationDateStr = reservation.get("s_date");
        LocalDate reservationDate = LocalDate.parse(reservationDateStr, formatter);

        
        if (reservationDate.isBefore(currentDate.minusDays(1))) {
            String updateQuery = "UPDATE tbl_reservation SET s_status = 'Complete' WHERE s_id = ?";
            conf.updateRecord(updateQuery, reservationId);
        }
    }
}
    private void updateReservation() {
        config conf = new config();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Reservation ID to update: ");
        int resId = validateIntegerInput(sc);

        String checkQuery = "SELECT s_id FROM tbl_reservation WHERE s_id = ?";
        while (conf.getSingleValue(checkQuery, resId) == 0) {
            System.out.print("Reservation ID does not exist. Enter again: ");
            resId = validateIntegerInput(sc);
        }

        String dateQuery = "SELECT s_date FROM tbl_reservation WHERE s_id = ?";
        String reservationDateStr = conf.getStringValue(dateQuery, resId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate reservationDate = LocalDate.parse(reservationDateStr, formatter);
        LocalDate currentDate = LocalDate.now();

        if (reservationDate.isBefore(currentDate.minusDays(1))) {
            String statusUpdateQuery = "UPDATE tbl_reservation SET s_status = 'Done' WHERE s_id = ?";
            conf.updateRecord(statusUpdateQuery, resId);
            System.out.println("Reservation status has been updated to 'Done' as it is older than 1 day.");
            return;
        }

        Movies mv = new Movies();
        mv.viewAvailableMovies();

        System.out.print("Enter new Movie ID: ");
        int newMid = validateIntegerInput(sc);

        String movieCheck = "SELECT m_id FROM tbl_movies WHERE m_id = ?";
        while (conf.getSingleValue(movieCheck, newMid) == 0) {
            System.out.print("Movie ID does not exist. Enter again: ");
            newMid = validateIntegerInput(sc);
        }

        System.out.print("Enter new Cash Received: ");
        double newCash = validateDoubleInput(sc);
        String priceQuery = "SELECT m_price FROM tbl_movies WHERE m_id = ?";
        double price = conf.getSingleValue(priceQuery, newMid);

        while (newCash < price) {
            System.out.println("Invalid amount. Cash received must be at least the movie price (" + price + "). Enter again:");
            newCash = validateDoubleInput(sc);
        }

        double change = newCash - price;
        System.out.println("Cash Received: " + newCash);
        System.out.println("Change: " + change);

        String updateQuery = "UPDATE tbl_reservation SET m_id = ?, s_rcash = ? WHERE s_id = ?";
        conf.updateRecord(updateQuery, newMid, newCash, resId);
        System.out.println("Reservation details have been updated.");
    }

    public void deleteReservation() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Reservation ID to delete: ");
        int id = validateIntegerInput(sc);

        String checkQuery = "SELECT s_id FROM tbl_reservation WHERE s_id = ?";
        while (conf.getSingleValue(checkQuery, id) == 0) {
            System.out.println("SELECTED ID DOES NOT EXIST. PLEASE TRY AGAIN:");
            id = validateIntegerInput(sc);
        }

        String qry = "DELETE FROM tbl_reservation WHERE s_id = ?";
        conf.deleteRecord(qry, String.valueOf(id));
        System.out.println("Reservation successfully deleted.");
    }

    private int validateIntegerInput(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            sc.next();
        }
        return sc.nextInt();
    }

    private double validateDoubleInput(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            sc.next();
        }
        return sc.nextDouble();
    }
}
