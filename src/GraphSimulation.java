/* File name : GraphSimulation.java */
import java.io.*;
import java.util.Scanner;

/**
 * This program reads adjacency matrices from a specified input file and writes
 * all possible non-looping paths between all possible pairs of vertices. If a
 * path does not exist, the program acknowledges it. All output is written to
 * the specified output file.
 *
 * @author Rodrigo Rogel-Perez
 * @version 1.0
 * @since 2020-11-17
 */
public class GraphSimulation {

   public static void main(String[] args) {

      int lineNum; // Row number of input file
      int colVal; // Column value
      int numVertices; // Number of vertices
      int matrixCount; // Matrix counter
      String record; // Row data
      String text; // Output text;
      Node vertex;
      List vertices;

      //  Check for command line arguments.
      // This chunk of code was copied from the sample provided by the professor
      if (args.length != 2) {
         System.out.println("Usage: java GraphSimulation [input file pathname]"
            + " [output file pathname]");
         System.exit(-1);
      }

      // Open file and parse the data line by line until there it is null
      try (BufferedReader input = new BufferedReader(new FileReader(args[0]))) {

         lineNum = 1;
         colVal = 0;
         numVertices = 0;
         matrixCount = 1;
         text = "";
         vertices = new List();
         vertex = null;

         while ((record = input.readLine()) != null) {
            // Validate the specified number of nodes in the input file
            if (!record.isEmpty() && !record.substring(0, 1).equals("/")) {
               Scanner scan = new Scanner(record);

               // Check for invalid input: input is not an integer
               try {
                  numVertices = Integer.parseInt(scan.next());
               } catch (NumberFormatException e) {
                  text = "Specified number of vertices in line " + lineNum
                     + " is not an integer.\n"
                     + record + "\n";
                  writeOutput(text, args[1]); // Write to output file
                  System.exit(-1);
               }
               // Check for invalid input: input less than 0
               if (numVertices < 1) {
                  text = "Specified number of vertices in line " + lineNum
                     + " can not be less than 1.\n"
                     + record + "\n";
                  writeOutput(text, args[1]);
                  System.exit(-1);
               }
               text += "*****************************************\n"
                  + "   MATRIX " + (matrixCount) + ": STRUCTURE"
                  + "\n*****************************************\n";
               // Read elements of the adjacent matrix row by row
               for (int i = 0; i < numVertices; i++) {
                  lineNum++;
                  record = input.readLine();
                  System.out.println(record);
                  text += record + "\n";
                  scan = new Scanner(record);
                  vertex = new Node(i + 1);

                  // Read every element in the current row
                  for (int j = 0; j < numVertices; j++) {
                     // Check for invalid input: element is not an integer
                     try {
                        colVal = Integer.parseInt(scan.next());
                     } catch (NumberFormatException e) {
                        text = "Element in line " + lineNum
                           + " column " + (j + 1) + " is not an integer.\n"
                           + record + "\n";
                        writeOutput(text, args[1]);
                        System.exit(-1);
                     } catch (Exception e) {
                        // Missing element in matrix nxn
                        text = "Missing element(s) in line " + lineNum
                           + " column " + (j + 1) + ". Matrix dimensions are "
                           + (numVertices + "x" + numVertices) + "\n"
                           + record + "\n";
                        writeOutput(text, args[1]);
                        System.exit(-1);
                     }
                     // Check for invalid input: element is neither 0 nor 1
                     if (colVal != 1 && colVal != 0) {
                        text = "Value in line " + lineNum
                           + " column " + (j + 1) + " is outside the range [0,1].\n"
                           + record + "\n";
                        writeOutput(text, args[1]);
                        System.exit(-1);
                     }
                     // node is adjacent and added to list of adjacent nodes
                     if (colVal == 1) {
                        vertex.addAdjacentNode(new Node(j + 1));
                     }
                  }
                  vertices.addLast(vertex);
               }
               vertices.findAllPaths();
               text += "*****************************************\n"
                  + "   MATRIX " + (matrixCount++) + ": PATHS"
                  + "\n*****************************************\n";
               text += vertices.allPathsStr();
               vertices.removeAll();
            }
            lineNum++;
         }
      } catch (FileNotFoundException e) {
         text = "File Not Found " + e;
         writeOutput(text, args[1]);
         System.exit(-1);
      } catch (IOException e) {
         text = "An I/O Error Ocurred " + e;
         writeOutput(text, args[1]);
         System.exit(-1);
      }
      text += "\nEnd of File... program will now terminate\n";
      writeOutput(text, args[1]);
   }

   /**
    * Write a string to the specified file.
    *
    * @param text The text to write.
    * @param fileName File name on which text will be written.
    */
   private static void writeOutput(String text, String fileName) {

      // Write to new file using try-with-resource statement to automatically close stream at end of session
      try (PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
         output.print(text);
      } // Throw exception if other I/O related error is encoutered
      catch (IOException e) {
         System.out.println("An I/O Error Occurred " + e);
         System.exit(-1);
      }
   }
}
