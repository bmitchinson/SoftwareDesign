/**
 * The CheckWriterDemo class is a driver that's main method creates a new Check
 * object, and then triggers it's promptUser method. It's purpose is to present
 * Check's functionality to user, which is detailed further in it's own
 * respective documentation, linked below.
 * <p>
 * It is an implementation of the assignment 14-21_CheckWriter_Medium
 *
 * @author Ben Mitchinson
 * @see Check
 */
public class CheckWriterDemo {
    /**
     * Method used to present instructions and begin the demo
     *
     * @param args - unused
     */
    public static void main(String args[]) {
        System.out.println("******************************************\n" +
                "*            CHECK WRITER DEMO           *\n" +
                "******************************************");
        System.out.println("Enter -1 at anytime to quit");
        while (true) {
            Check demo = new Check();
            demo.promptUser();
        }
    }
}
