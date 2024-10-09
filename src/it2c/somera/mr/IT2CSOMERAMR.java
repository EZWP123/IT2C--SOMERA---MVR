
package it2c.somera.mr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;


public class IT2CSOMERAMR {

     public void addRent(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print(" Movie name:: ");
        String movie = sc.next();
        System.out.print(" First Name: ");
        String fname = sc.next();
        System.out.print(" Last Name: ");
        String lname = sc.next();
        System.out.print(" Email: ");
        String email = sc.next();
        System.out.print(" Contact no.: ");
        String contact = sc.next();
      

        String sql = "INSERT INTO Renting (r_movie, r_fname, r_lname, r_email, r_contact) VALUES ( ?, ?, ?, ?, ?)";


        conf.addRecord(sql, movie, fname, lname, email, contact);


    }

     
    
        private void viewRent() {
         config conf = new config();   
        String cqry = "SELECT * FROM Renting";
        String[] hrds = {"ID", "Name", "Lastname", "Email", "Contact", "Movie"};
        String[] clmns = {"r_id", "r_fname", "r_lname", "r_email", "r_contact", "r_movie"};

        conf.viewRecords (cqry, hrds, clmns);
        
        }
        
        private void Updaterent(){
        Scanner sc = new Scanner(System.in);
            System.out.println("Enter ID to update");
        int id = sc.nextInt();
        
            System.out.println("Enter new first name");
            String nfname = sc.next();
            
             System.out.println("Enter new last name");
            String nlname = sc.next();
            
             System.out.println("Enter new email");
            String nemail = sc.next();
            
             System.out.println("Enter new contact");
            String ncontact = sc.next();
            
             System.out.println("Enter new movie");
            String nmovie = sc.next();
            
            String qry = "UPDATE Renting SET r_fname = ?, r_lname = ?, r_email = ?, r_contact = ?, r_movie = ? WHERE r_id = ?";
            config conf = new config();
            conf.updateRecord(qry, nfname, nlname,nemail,ncontact,nmovie,id);
        
        }
      
      
      private void deleteRent(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
       System.out.print("Enter ID to delete: ");
    String r_id = sc.next();
    
     String deleteSql = "DELETE FROM Renting WHERE r_id = ?";
    conf.deleteRecord(deleteSql, r_id);
    
  
}
   

        
    
  
    public static void main(String[] args) {
    
        
         Scanner sc = new Scanner (System.in);
        String response;
        
        do{
            
            System.out.println("1. Add");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Exit");
            
            System.out.println("Enter action");
           
           int  action = sc.nextInt();
            
            switch(action){
                
                case 1:
                IT2CSOMERAMR demo = new IT2CSOMERAMR();
               demo.addRent();
               break;
                
                case 2: 
                 IT2CSOMERAMR demo2 = new IT2CSOMERAMR();
                demo2.viewRent();   
                break;
                
                case 3:  IT2CSOMERAMR demo3 = new IT2CSOMERAMR();             
                demo3.Updaterent();
                demo3.viewRent();
                break;
                
                case 4:               
                    IT2CSOMERAMR demo4 = new IT2CSOMERAMR();
                     demo4.viewRent();
                demo4.deleteRent();
                 demo4.viewRent();
                break;
            }
            
            System.out.println("Continue? (yes/No)");
                response = sc.next();
         
            
            
            
        }while(response.equals("yes"));
        System.out.println("Thankyou");
    }

}