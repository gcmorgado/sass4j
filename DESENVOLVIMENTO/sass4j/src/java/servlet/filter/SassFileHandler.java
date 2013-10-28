/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet.filter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author gmorgado
 */

public class SassFileHandler {
    
    public File readFile(File file) throws IOException {
        
        InputStream is = new FileInputStream("c:/teste.sass");  
        OutputStream os = new FileOutputStream("c:/saida.css");  
        OutputStreamWriter osw = new OutputStreamWriter(os);  
        BufferedWriter bw = new BufferedWriter(osw);  
  
        Scanner input = new Scanner(is); 
        Pattern p = null;
        p = Pattern.compile("$");
        
        while (input.hasNextLine()) {  
            input.findInLine(p);
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
        
        
        return file;
        
    }

    
    
    
    
    
}
