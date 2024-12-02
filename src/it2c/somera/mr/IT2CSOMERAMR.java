 package it2c.somera.mr;
import java.util.Scanner;
public class IT2CSOMERAMR {
    public static void main(String[] args){
         Scanner sc = new Scanner (System.in);
        String response;
        
        do{
            System.out.println("");
            System.out.println("RENTING APP");
            
            System.out.println("1. Guest");
            System.out.println("2. Movies");
            System.out.println("3. Reservation");      
            System.out.println("4. Reports");
            System.out.println("5. Exit");
            
            System.out.println("Enter action");           
            int  action = sc.nextInt();
            
            switch(action){
                
                case 1:
                             Guest cs = new Guest();
                             cs.gtransaction();
               break;
                
                case 2: 
                             Movies mv = new Movies();
                             mv.mtransaction();
                break;
                
                case 3:  
                             Reservation rs = new Reservation();
                             rs.rtransaction();
                break;
                
                case 4:          Reports report = new Reports();
                                 report.reportMenu();
                   
                break;
            }
            
            System.out.println("Sure? (yes/no)");
                response = sc.next();
         
            
            
            
        }while(response.equals("no"));
        System.out.println("Okay po");
    }
}
