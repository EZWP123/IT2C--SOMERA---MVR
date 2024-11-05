package it2c.somera.mr;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class Reservation {
   
    
    public void rtransaction(){
         Scanner sc = new Scanner(System.in);
     String response;
     do{
          System.out.println("TRANSACTION PANEL");
            
            System.out.println("1. ADD RESERVATION");
            System.out.println("2. VIEW RESERVATION ");
            System.out.println("3. UPDATE RESERVATION");      
            System.out.println("4. DELETE RESERVATION");
             System.out.println("5. EXIT");
            System.out.println("Enter action");           
            int  act = sc.nextInt();
           Reservation rs = new Reservation ();
            switch(act){
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
                
                case 5: System.out.println("Go backk to main menu?");
                return;  

                default:
                System.out.println("Invalid action. Please try again.");
                break;
        }

                 System.out.println("Continue? (yes/No)");
                response = sc.next();
   
                 }while(response.equals("yes"));
                 System.out.println("Thankyou");
   
    }
    private void addReservation(){
        config conf = new config();
      Scanner sc = new Scanner(System.in);
        Guest cs = new Guest();
      cs.viewGuest();
      
        System.out.print("SELECT THE ID OF GUEST");
        int gid = sc.nextInt();
        
        String sql= "SELECT r_id FROM Renting WHERE r_id = ?";
        while(conf.getSingleValue(sql,gid)  == 0){
            System.out.print("SELECTED ID DOESNT EXIST");
            System.out.println("PLEASE TRY AGAIN:");
            gid = sc.nextInt();         
  }
           Movies mv = new Movies();
        mv.viewAvailableMovies();
        
        System.out.println("ENTERR ID OF MOVIES:");
      int mid = sc.nextInt();
      
      String pslq = "SELECT m_id FROM tbl_movies WHERE m_id = ?";
      while(conf.getSingleValue(pslq, mid) == 0 ){
          System.out.println("MOVIES DOES NOT EXIST!! SELECT AGAIN AHHH!!");
          mid = sc.nextInt();   
          
      }
        
      
        
        String priceqry = "SELECT m_price FROM tbl_movies WHERE m_id = ?";
        double price  = conf.getSingleValue( priceqry, mid);
       
        
       
        
        System.out.println("Enter Cash Recieved:");
        double rcash = sc.nextDouble();
        
        while(rcash < price){
            System.out.println("Invalid amount, TRY AGAIN!!");
            rcash = sc.nextDouble();
        }   
            System.out.println("Cash Received:"+rcash);
            double change = rcash - price;
            System.out.println("change:"+change);
        
        LocalDate currdate = LocalDate.now();
        DateTimeFormatter format =  DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = currdate.format(format);
        
        String status = "PENDING";
        
        String reservationqry = "INSERT INTO tbl_reservation ( r_id, m_id, s_rcash, s_date, s_status) " 
                + "VALUES (?, ?, ?, ?, ?)";
        
        
        conf.addRecord(reservationqry, gid, mid, rcash, date , status);
                
    }
        
        
        public void viewReservation(){
            
             String qry = "SELECT s_id, r_lname, m_name, m_price, m_duration, s_date, s_status FROM tbl_reservation"
             + " LEFT JOIN Renting ON Renting.r_id = tbl_reservation.r_id"
             + " LEFT JOIN tbl_movies ON tbl_movies.m_id = tbl_reservation.m_id";
                String[] hrds = {"Reservation ID", "Last name", "Movies", "PRICE ", "Duration", "Date", "Status"};
                String[] clmns = {"s_id", "r_lname", "m_name", "m_price","m_duration", "s_date", "s_status"};
                config conf = new config();
                conf.viewRecords(qry, hrds, clmns);
        
        
            
            
            
        }
        
        private void updateReservation() {
    config conf = new config();
    Scanner sc = new Scanner(System.in);

   
    System.out.print("Enter Reservation ID to update: ");
    int resId = sc.nextInt();

    
    String checkQuery = "SELECT s_id FROM tbl_reservation WHERE s_id = ?";
    if (conf.getSingleValue(checkQuery, resId) == 0) {
        System.out.println("Reservation ID does not exist.");
        return;
    }

     Movies mv = new Movies();
        mv.viewAvailableMovies();
    System.out.print("Enter new Movie ID: ");
    int newMid = sc.nextInt();

   
    String movieCheck = "SELECT m_id FROM tbl_movies WHERE m_id = ?";
    while (conf.getSingleValue(movieCheck, newMid) == 0) {
        System.out.print("Movie ID does not exist. Enter again: ");
        newMid = sc.nextInt();
    }

    
    System.out.print("Enter new Cash Received : ");
    double newcash = sc.nextDouble();

   
    String priceQuery = "SELECT m_price FROM tbl_movies WHERE m_id = ?";
    double price = conf.getSingleValue(priceQuery, newMid);

    
    while (newcash < price) {
        System.out.println("Invalid amount. Cash received must be at least the movie price (" + price + "). Enter again:");
        newcash = sc.nextDouble();
    }
 System.out.println("Cash Received:"+newcash);
            double change = newcash - price;
            
    
    String updateQuery = "UPDATE tbl_reservation SET m_id = ?, s_rcash = ? WHERE s_id = ?";
    conf.updateRecord(updateQuery, newMid, newcash, resId); 
    System.out.println("change:"+change);
}
       
        
        public void deleteReservation(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
       System.out.print("Enter Reservation ID to delete: ");
        int id = sc.nextInt();
       while(conf.getSingleValue("SELECT s_id FROM tbl_reservation WHERE s_id = ?",  id)  == 0){
            System.out.println("SELECTED ID DOESNT EXIST");
            System.out.println("PLEASE TRY AGAIN:");
            id = sc.nextInt();
    }
     String qry = "DELETE FROM tbl_reservation WHERE s_id = ?";
    conf.deleteRecord(qry,String.valueOf(id));
        
}
        
        
        
        
        
    }
    