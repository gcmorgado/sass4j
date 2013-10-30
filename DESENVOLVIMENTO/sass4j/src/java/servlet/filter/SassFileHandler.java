package servlet.filter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author gmorgado
 */

public class SassFileHandler {
    
    public void fileHandler(InputStream is, OutputStream os) throws IOException {
        
        OutputStreamWriter osw = new OutputStreamWriter(os);  
        BufferedWriter bw = new BufferedWriter(osw);  
        HashMap<String, String> lista = new HashMap<String, String>();  
        Scanner input = new Scanner(is); 
        
        while(input.hasNextLine()) {
            
            String patternFound = input.findInLine("\\$[^;]*");
            
            if(patternFound != null) {
                String array[] = patternFound.split(":");
                //lista.put(array[0], array[1]);   
                System.out.println(patternFound);
            }
            
            System.out.println(lista);

            String line = input.nextLine();
            bw.newLine();  
            
        }
        
        bw.close();  
        input.close();  

    }
    
    public String convertVariables(String input) {
        
        String output = "";
        
        
        
        return output;
    }
    
}