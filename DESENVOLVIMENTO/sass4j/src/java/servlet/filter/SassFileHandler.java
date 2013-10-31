package servlet.filter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
        Scanner input = new Scanner(is);
        HashMap<String, String> lista = new HashMap<String, String>();  
        
        while(input.hasNextLine()) {
            
            String line = input.nextLine();
                
            if(line.matches("\\^\\$.*;")){
                String patternFound[] = line.split(":");
            }

            

//            if(patternFound != null) {
//                String array[] = patternFound.split(":");
//                lista.put(array[0], array[1]);   
//                System.out.println(patternFound);
//            }

            System.out.println(lista);

            
            
        }
        
        bw.close();  
        input.close();  

    }
    
    public String convertVariables(String input) {
        
        String output = "";
        
        
        
        return output;
    }
    
}