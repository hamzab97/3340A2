import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static DisjointSet disjointSet;
    /*
        + means 1
        space means 0
         */

    public static void main(String[] args) {
        disjointSet = new DisjointSet();
        calculateDimensions("./src/girl.img");
    }

    /*
     * Method creates a matrix representation of the input image
     */
    private static void readImage(String image, int[][] matrix) {
        int i = 0;
        int j = 0;
        try {
            File file = new File(image);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                j = 0;
                for (char ch : data.toCharArray()) {
                    if (ch == '+') {
                        matrix[i][j] = 1;
                    } else {
                        matrix[i][j] = 0;
                    }
                    j++;
                }
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /*
     * Method creates union sets when reading the image
     */
    private static void calculateDimensions(String image) {
        int length = 0;
        int width = 0;

        try {
            File file = new File(image);
            Scanner myReader = new Scanner(file);
            System.out.println("1. Input binary image");
            while (myReader.hasNextLine()) {
                //calculate the dimensions of the input image
                //the length and the width of the lines
                String data = myReader.nextLine();
                System.out.println(data);
                length++;
                width = data.length();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        int total_indices = length * width;
        disjointSet.uandf(total_indices);

        int[][] matrix = new int[length][width];

        readImage(image, matrix);

        int index = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] == 1) {
                    disjointSet.make_set(index);
                }
                index++;
            }
        }

        /*
         * Iterate thru the matrix from 0 to length and 0 to width
         * create union of sets at i and j where ever there is 1 present
         * and connected horizontally and vertically with other 1s
         */
        index = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] == 1 && j + 1 < width && matrix[i][j + 1] == 1) {
                    disjointSet.union_sets(index, index + 1);
                }
                if (matrix[i][j] == 1 && i + 1 < length && matrix[i + 1][j] == 1) {
                    disjointSet.union_sets(index, index + length);
                }
                index++;
            }
        }

        //part b, question 2. Connected component image

        System.out.println("Total number of connected components " + disjointSet.final_sets() + "\n");

        index = 0;
        System.out.println("Connected component image: ");
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] == 1) {
                    System.out.print(disjointSet.find_set(index).data + " ");
                }
                else {
                    System.out.print(" 0");
                }
                index++;
            }
            System.out.println();
        }

        System.out.println("\nConnected components are (Component label=size of component):");
        Stream<Map.Entry<Integer, Integer>> sorted_stream = sortedComponents(total_indices);
        sorted_stream.forEach(s -> System.out.println(s));
        System.out.println();

        Stream<Integer> keys = sortedComponents(total_indices)
                .filter(map -> map.getValue() >= 4)
                .map(Map.Entry::getKey);

        Set<Integer> keysSet = keys.collect(Collectors.toSet());

        index = 0;
        System.out.println("Connected component image with only components greater than 4: ");
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] == 1 && keysSet.contains(disjointSet.find_set(index).data)) {
                    System.out.print(disjointSet.find_set(index).data + " ");
                }
                else {
                    System.out.print(" 0");
                }
                index++;
            }
            System.out.println();
        }
    }

    private static Stream<Map.Entry<Integer, Integer>> sortedComponents(int total_index) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < total_index; i++) {
            if (disjointSet.find_set(i) != null){
                map.put(disjointSet.find_set(i).data, map.getOrDefault(disjointSet.find_set(i).data, 0) + 1);
            }
        }
        return map.entrySet().stream().sorted(
                Collections.reverseOrder(
                        Map.Entry.comparingByValue()));
    }

}
