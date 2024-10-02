
package it2c.somera.mr;

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
               
                
            }
            
            System.out.println("Continue? (yes/No)");
                response = sc.next();
         
            
            
            
        }while(response.equals("yes"));
        System.out.println("Thankyou");
    }
    
}
