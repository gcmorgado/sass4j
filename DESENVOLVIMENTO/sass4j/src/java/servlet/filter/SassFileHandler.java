package servlet.filter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 *
 * @author gmorgado
 */

public class SassFileHandler {
    
    public void fileHandler(InputStream is, OutputStream os) throws IOException {
        
        OutputStreamWriter osw = new OutputStreamWriter(os);  
        BufferedWriter bw = new BufferedWriter(osw);  
  
        Scanner input = new Scanner(is); 
        
        while (input.hasNextLine()) {  
            input.findInLine("$");
            String line = input.nextLine();
              
            String newline = line.substring(1, 6);
            bw.write(newline + ",");  
  
              
            newline = line.substring(6, 15);  
            bw.write(newline + ",");  
  
              
            newline = line.substring(15, 20);  
            bw.write(newline + ",");  
              
            bw.newLine();  
  
        }  
  
        bw.close();  
        input.close();  
        
        
    }

    
    
    
    
    
}
