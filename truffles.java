import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class truffles {
    public static void main(String[] args) {
        if(args.length > 0) {
            File file = new File(args[0]);
            // if viable file, continue
            if(file.exists()) {
                try (Scanner sc = new Scanner(file)) {
                    // for holding every line
                    List<String> lineStrings = new ArrayList<>();
                    // read from input file
                    while(sc.hasNextLine()) {
                        String input = sc.nextLine().strip();
                        lineStrings.add(input);
                    }

                    //change this: not guaranteed to be square
                    int[][] field = new int[lineStrings.size()][lineStrings.get(0).split(" ").length]; /// kinda messy, may replace for readability

                    // get each number, convert it to an int, and store it in a 2D int array
                    for(int i = 0; i < lineStrings.size(); i++) {
                        String line = lineStrings.get(i);
                        String[] numStrings = line.split(" "); // CRITICAL: SPLIT ON \t EVENTUALLY
                        for(int j = 0; j < numStrings.length; j++) {
                            field[i][j] = Integer.parseInt(numStrings[j]);
                        }
                    }

                    // print for testing
                    for(int i = 0; i < field.length; i++) {
                        for(int j = 0; j < field[i].length; j++) {
                            System.out.print(field[i][j] + " ");
                        }
                        System.out.println();
                    }
                    
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("ERROR: invalid file given");
            }
        }
        else { // if no input given
            System.out.println("ERROR: No file given");
        }
    }

    /* Takes the given field ints as an input, returns the optimal traversal of the field */
    public static int[] optimalField(int[][] field) {
        List<int[]> fieldAL = new ArrayList<>();
        // turn field into Arraylist of int arrays
        fieldAL.addAll(Arrays.asList(field));
        int rowLength = fieldAL.get(0).length;
        
        // bestPaths will hold the best path to take from each cell in the field
        int[][] bestPaths = new int[fieldAL.size()][rowLength];
        for(int i = 0; i < bestPaths[0].length; i++) {
            bestPaths[bestPaths.length - 1][i] = i; // assign each cell at the very bottom row to i since it can't go down to anything
        }
        // until fieldAL is only one row, remove rows after adding the best traversal to each row
        while(fieldAL.size() > 1) {
            int currRow = fieldAL.size() - 2;
            // maybe add currRow as a param to findBestDown?
            for(int i = 0; i < rowLength; i++) {
                int[] bestDown = findBestDown(fieldAL, i);
                bestPaths[currRow][i] = bestDown[1]; // assign the best path to the current cell
                fieldAL.get(currRow)[i] = bestDown[0];
            }
            fieldAL.remove(fieldAL.size() - 1);
        }
        
        return null;
    }

    /* Helper method for optimalField
    Returns best cell to go down to from current location. 
    Returns int array. First entry is the value  of the cell after choosing next best spot to go to.
    The second entry is the index of the bottom cell chosen for max value.*/
    // NOTE: may need to b echanged for logistics in optimalField
    private static int[] findBestDown (List<int[]> currField, int index) {
        // declare variables
        int currRow = currField.size() - 2;
        int currCell = currField.get(currRow)[index];
        int biggestVal = -1;
        int biggestValIndex = -1;
        int startLoop = -1;
        int endLoop = 2;
        // if current cell is on the very left
        if(index == 0) {
            startLoop = 0;
        }
        // if current cell is on the very right
        else if(index == currField.get(currRow).length - 1) {
            endLoop = 1;
        }
        // go thru down left, down middle, and down right cells to find biggest
        for(int i = startLoop; i < endLoop; i++) {
            if(currField.get(currRow + 1)[index + i] > biggestVal) {
                biggestVal = currField.get(currRow + 1)[index + i];
                biggestValIndex = index + i;
            }
        }
        return new int[]{currCell + biggestVal, biggestValIndex};
    }
}
