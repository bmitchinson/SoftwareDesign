import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A class to manage the recursion involved in a dual pivot quick sort. Stores
 * and array object both before and after sorting for comparison, along with
 * methods to print them to the user for confirmation.
 *
 * @see #QuickSort
 * @see #sort(Integer[])
 */
public class QuickSort {

    private Integer[] initalArray;
    private Integer[] sortedArray;
    private Random rand = new Random();

    /**
     * Stores the inital array, and begins the recursion by calling
     * {@link #sort(Integer[])} on a new copy of the given array
     *
     * @param array initial array to be sorted.
     */
    QuickSort(Integer[] array) {
        initalArray = array;
        sortedArray = sort(Arrays.copyOf(initalArray, initalArray.length));
    }

    /**
     * This recursive method is used to sort partitions using randomly selected
     * pivots. It's reaches a base case once there are only zero, one, or two
     * elements in a partition, at which point it returns the sorted result.
     * results and then combined in proper order, resulting in a completely
     * sorted array.
     *
     * @param array partition to sort
     * @return sorted partition
     * @see QuickSort
     * @see SortDemo
     */
    private Integer[] sort(Integer[] array) {
        System.out.println("***********************************");
        System.out.println("Given:" + arrayAsString(array));

        // Base case if there's two ele, one, or
        switch (array.length) {
            case (2):
                if (array[0] < array[1]) {
                    System.out.println("Base Reached:" + arrayAsString(array));
                    return array;
                } else {
                    Integer[] swap = {array[1], array[0]};
                    System.out.println("Base Reached:" + arrayAsString(swap));
                    return swap;
                }

            case (1):
                System.out.println("Base Reached:" + arrayAsString(array));
                return array;

            case (0):
                System.out.println("Base Reached:" + arrayAsString(new Integer[0]));
                return new Integer[0];
        }

        // Get two pivot points
        int a = rand.nextInt(array.length);
        int b = rand.nextInt(array.length);
        while (b == a) {
            // Make sure a and b are different
            b = rand.nextInt(array.length);
        }
        // Make sure a is greater than b
        if (array[b] < array[a]){
            Integer tmp = b;
            b = a;
            a = tmp;
        }

        // sort into 3 partitions
        List<Integer> lessA = new ArrayList<>();
        List<Integer> betweenAB = new ArrayList<>();
        List<Integer> moreB = new ArrayList<>();

        for (Integer i : array) {
            if (array[a] < i && i < array[b]) {
                betweenAB.add(i);
            } else if (i >= array[b]) {
                moreB.add(i);
            } else if (i <= array[a]) {
                lessA.add(i);
            }
        }

        // Recursive calls to sort partitions
        Integer[] lessAresult = sort(lessA.toArray(new Integer[0]));
        Integer[] betweenABresult = sort(betweenAB.toArray(new Integer[0]));
        Integer[] moreBresult = sort(moreB.toArray(new Integer[0]));

        // Combine sorted results
        Integer[] result = new Integer[array.length];
        int i = 0;
        for (Integer num : lessAresult) {
            result[i] = num;
            i++;
        }
        for (Integer num : betweenABresult) {
            result[i] = num;
            i++;
        }
        for (Integer num : moreBresult) {
            result[i] = num;
            i++;
        }

        return result;
    }

    /**
     * A getter for the sortedArray
     * @return sortedArray
     */
    public Integer[] getSortedArray() {
        return sortedArray;
    }

    /**
     * return a given array as a string for printing for user confirmation
     * @param array array requesting string representation
     * @return string representation of given array
     */
    private String arrayAsString(Integer[] array) {
        String result = "";
        result += "[";
        for (int i = 0; i < array.length; i++) {
            result += (array[i]);
            if (i != array.length - 1) {
                result += ",";
            }
            if (i % 20 == 0 && i != 0) {
                result += "\n";
            }
        }
        result += ("]");
        return result;
    }


}
