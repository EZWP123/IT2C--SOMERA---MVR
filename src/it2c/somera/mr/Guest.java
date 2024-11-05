
package it2c.somera.mr;

import java.util.Scanner;


public class Guest {
    
    
    
   
  
    public void gtransaction(){
     Scanner sc = new Scanner(System.in);
     String response;
     do{
          System.out.println("GUEST PANEL");
            
            System.out.println("1. ADD GUEST");
            System.out.println("2. VIEW GUEST ");
            System.out.println("3. UPDATE GUEST");      
            System.out.println("4. DELETE GUEST");
             System.out.println("5. EXIT");
            System.out.println("Enter action");           
            int  act = sc.nextInt();
          Guest cs = new Guest();
            switch(act){
                case 1:         cs.addGuest();
                                cs.viewGuest();     
             
               break;
                
                case 2:         cs.viewGuest();
                break;
                
                case 3:         cs.viewGuest();
                                cs.updateGuest();
                                cs.viewGuest();
                break;
                
                case 4:         cs.viewGuest();
                                cs.deleteGuest();
                                cs.viewGuest();
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
    
    public void addGuest(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
       
        System.out.print(" First Name: ");
        String fname = sc.next();
        System.out.print(" Last Name: ");
        String lname = sc.next();
        System.out.print(" Email: ");
        String email = sc.next();
        System.out.print(" Contact no.: ");
        String contact = sc.next();
      
        String sql = "INSERT INTO Renting ( r_fname, r_lname, r_email, r_contact) VALUES (  ?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, email, contact);
    }
     
    
        public void viewGuest(){
         config conf = new config();   
        String cqry = "SELECT * FROM Renting";
        String[] hrds = {"ID", "Name", "Lastname", "Email", "Contact"};
        String[] clmns = {"r_id", "r_fname", "r_lname", "r_email", "r_contact"};
        conf.viewRecords (cqry, hrds, clmns);
        
        }
        
        private void updateGuest(){
            
        Scanner sc = new Scanner(System.in);
        config conf = new config();
            System.out.println("Enter ID to update");     
            int id = sc.nextInt();
       while(conf.getSingleValue("SELECT m_id FROM tbl_movies WHERE m_id = ?",  id)  == 0){
            System.out.println("SELECTED ID DOESNT EXIST");
            System.out.println("PLEASE TRY AGAIN:");
            id = sc.nextInt();
    }
    
            System.out.println("Enter new first name");
            String nfname = sc.next();
            
             System.out.println("Enter new last name");
            String nlname = sc.next();
            
             System.out.println("Enter new email");
            String nemail = sc.next();
            
             System.out.println("Enter new contact");
            String ncontact = sc.next();
            String qry = "UPDATE Renting SET r_fname = ?, r_lname = ?, r_email = ?, r_contact = ? WHERE r_id = ?";        
            conf.updateRecord(qry, nfname, nlname,nemail,ncontact,id);
        
        }
      
      
      public void deleteGuest(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
       System.out.print("Enter ID to delete: ");
    int id = sc.nextInt();
    while(conf.getSingleValue("SELECT r_id FROM Renting WHERE r_id = ?",  id)  == 0){
            System.out.println("SELECTED ID DOESNT EXIST");
            System.out.println("PLEASE TRY AGAIN:");
            id = sc.nextInt();
    }
     String qry = "DELETE FROM Renting WHERE r_id = ?";
    conf.deleteRecord(qry,String.valueOf(id));
    
      }
}
   
