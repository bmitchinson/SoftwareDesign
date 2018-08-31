import java.util.Scanner;

public class ChangeComputation {

    // Import Register
    public static void main(String args[]){
        System.out.println("\nWelcome to SWD Register:" +
                "\n*****************************\n");
        // Display welcome message
        // Initialize new Register
        Register mainReg = new Register();

        Scanner input = new Scanner(System.in);

        int choice = 0;
        while (choice != 9){
            mainReg.printTotals();
            // Display menu options
            printMenu();
            // TODO: Do I need to validate nextInt? Try catch?
            choice = input.nextInt();
            switch (choice){
                (1){

                }
            }
            // Choice switch statement for register operations.
        }
    }

    private static void printMenu(){
        System.out.println("\nOptions:\n*****************************");
        System.out.println("1.) Charge Customer for an Item");
        System.out.println("2.) Manually add a transaction");
        System.out.print("9.) Exit\n\n");
        System.out.print("Choice:");
    }

}
