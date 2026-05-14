import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
}
