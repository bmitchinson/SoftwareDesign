import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class SortDemo {

    //Change this to how many elements you would like to see sorted
    private static final int ELEMENTS = 100;

    public static void main(String args[]) {

        System.out.println("******************************************\n" +
                           "*               QUICK SORT               *\n" +
                           "******************************************\n\n");

        Scanner input = new Scanner(System.in);

        Integer[] randomArray = new Integer[ELEMENTS];
        for (int i = 0; i < ELEMENTS; i++) {
            randomArray[i] = i;
        }
        System.out.println("Array 0-" + ELEMENTS + " contents: ");
        printIntegerArray(randomArray);

        List<Integer> temp = Arrays.asList(randomArray);
        Collections.shuffle(temp);
        randomArray = (Integer[]) temp.toArray();
        System.out.println("After shuffle: ");
        printIntegerArray(randomArray);

        System.out.println("\nPress enter to begin QuickSort. " +
                "All steps and base cases will be shown as they are called " +
                "recursively below:");
        System.out.print("\nEnter:");
        input.nextLine();

        System.out.println("Running quicksort...");
        QuickSort result = new QuickSort(randomArray);
        System.out.println("\nResult of quicksort:\n");
        printIntegerArray(result.getSortedArray());

    }

    public static void printIntegerArray(Integer[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i != array.length-1){
                System.out.print(",");
            }
            if (i % 20 == 0 && i!=0) {
                System.out.println();
            }
        }
        System.out.println("]\n");
    }
}
