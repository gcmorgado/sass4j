package servlet.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author gmorgado
 */

public class SassFileHandler {
    
    public void fileHandler(InputStream is, OutputStream os) throws IOException {
        
        PrintWriter output = new PrintWriter(os);
        HashMap<String, String> listOfPatterns = new HashMap<>();
        
        try (Scanner input = new Scanner(is)) {
        
            while(input.hasNextLine()) {
                
                String line = input.nextLine();
                
                if(line.matches("^\\$[^:]*:[^;]*;")) {
                    String patternFound[] = line.split(":");
                    listOfPatterns.put(patternFound[0],patternFound[1].substring(0, patternFound[1].length()-1));
                } else {
                    if(line.contains("$")) {
                        String variableFound[] = line.split("\\$");
                        String newLine = variableFound[0].substring(0, variableFound[0].length()-1)+listOfPatterns.get("$"+variableFound[1].substring(0,variableFound[1].length()-1));
                        System.out.println(newLine);
                    }
                    
                } 
                
                
                
                
                
            }
        }

    }
    
}