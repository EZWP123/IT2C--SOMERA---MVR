
package it2c.somera.mr;

import java.util.Scanner;

public class Movies {
    public void mtransaction() {
        Scanner sc = new Scanner(System.in);
        String id;
        String response;
        do {
            System.out.println("\n.......................................................................");
            System.out.println("MOVIE PANEL");
            System.out.println("1. ADD MOVIES");
            System.out.println("2. VIEW MOVIES ");
            System.out.println("3. UPDATE MOVIES");
            System.out.println("4. DELETE MOVIES");
            System.out.println("5. EXIT");
            System.out.println("Enter action");
            int act = sc.nextInt();
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
                    
                case 5:           System.out.println("Go backk to main menu?");
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

          
                 System.out.println("Continue? (yes/No)");
                response = sc.next();
   
                 }while(response.equals("yes"));
                 System.out.println("Thankyou");
   
    }

    public void addMovies() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Movie Name");
        String mname = sc.nextLine();
        
        System.out.println("Movie genre");
        String mgenre = sc.nextLine();
        
        System.out.println("Movie Duration");
        String mduration = sc.nextLine();
        
        System.out.println("Movie Price");
        Double price = sc.nextDouble();
        sc.nextLine();
        
        System.out.println("Movie Status (available/not available)");
        String mstatus = sc.nextLine();
        
        String qry = "INSERT INTO tbl_movies (m_name, m_genre, m_duration, m_price, m_status) VALUES (?, ?, ?, ?, ?)";
        config conf = new config();
        conf.addRecord(qry, mname, mgenre, mduration, price, mstatus);
    }

    public void viewMovies() {
        config conf = new config();
        String cqry = "SELECT * FROM tbl_movies";
        String[] hrds = {"ID", "Movie name", "Movie Genre", "Movie Duration", "Movie Price", "Movie Status"};
        String[] clmns = {"m_id", "m_name", "m_genre", "m_duration", "m_price", "m_status"};
        conf.viewRecords(cqry, hrds, clmns);
    }

    public void viewAvailableMovies() {
        config conf = new config();
        String cqry = "SELECT * FROM tbl_movies WHERE m_status = 'available'"; 
        String[] hrds = {"ID", "Movie name", "Movie Genre", "Movie Duration", "Movie Price", "Movie Status"};
        String[] clmns = {"m_id", "m_name", "m_genre", "m_duration", "m_price", "m_status"};
        conf.viewRecords(cqry, hrds, clmns);
    }
    
    
        
        
        private void updateMovies(){
            
        Scanner sc = new Scanner(System.in);
        config conf = new config();
            System.out.println("Enter ID to update");     
            int id = sc.nextInt();
            
            while(conf.getSingleValue("SELECT m_id FROM tbl_movies WHERE m_id = ?", id)==0){
            System.out.println("SELECTED ID DOESNT EXIST");
            System.out.println("PLEASE TRY AGAIN:");
            id = sc.nextInt();
            
        }
    
            System.out.println("Enter new movie name");
            String mname = sc.next();      
             System.out.println("Enter new movie genre");
            String mgenre = sc.next();          
             System.out.println("Enter new movie duration");
            String mduration = sc.next();           
             System.out.println("Movie new Price");
            Double price = sc.nextDouble();
             System.out.println("Enter new movie status");
            String mstatus = sc.next();
            String qry = "UPDATE tbl_movies SET m_name = ?, m_genre = ?, m_duration = ?,  m_price = ?, m_status = ? WHERE m_id = ?";        
            conf.updateRecord(qry, mname , mgenre, mduration , price, mstatus, id); 
        
        }
        
        public void deleteMovies(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
       System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();
       while(conf.getSingleValue("SELECT m_id FROM tbl_movies WHERE m_id = ?",  id)  == 0){
            System.out.println("SELECTED ID DOESNT EXIST");
            System.out.println("PLEASE TRY AGAIN:");
            id = sc.nextInt();
    }
     String qry = "DELETE FROM tbl_movies WHERE m_id = ?";
    conf.deleteRecord(qry,String.valueOf(id));
        
}
       
        
        
        
        
}
