public class CheckWriterDemo {
    public static void main(String args[]){
        System.out.println("******************************************\n" +
                           "*            CHECK WRITER DEMO           *\n" +
                           "******************************************");
        System.out.println("Enter -1 at anytime to quit");
        while(true){
            Check demo = new Check();
            demo.promptUser();
        }
    }
}
