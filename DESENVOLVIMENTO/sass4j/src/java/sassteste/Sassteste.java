package sassteste;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.jruby.CompatVersion;
import org.jruby.RubyInstanceConfig;
import servlet.filter.SassFilter;

/**
 *
 * @author gmorgado
 */

public class Sassteste {

    public static void main(String[] args) throws Exception {
               
        RubyInstanceConfig config = new RubyInstanceConfig();   
        config.setCompatVersion(CompatVersion.RUBY2_0); 
        
        String rubyFile = Sassteste.class.getResource("../sass4j.rb").getFile();
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("jruby"); 
        InputStreamReader isr = new InputStreamReader(new FileInputStream(rubyFile), "UTF8");
        engine.eval(isr);
        Invocable inv = (Invocable) engine;
        
        //String sass5 = "$c:red; a{color: $c;}";
        String sassScript = SassFilter.class.getResource("../sass.rb").getFile();
        engine.put("sassScript", sassScript);

        String pathArquivo = Sassteste.class.getResource("teste.scss").getFile();
        File arquivo = new File(pathArquivo);
        Scanner scan = new Scanner(arquivo, "UTF-8");
        StringBuilder sass = new StringBuilder();
        while (scan.hasNextLine()) {
            sass.append(scan.nextLine());
        }
        
        //String s = new String(Charset.forName("UTF-8").encode(sass).array());
        //s = s.substring(0, s.length() - 2);
        
        Object ret = inv.invokeFunction("compile", sass.toString());
        byte[] bytes = sass.toString().getBytes();
        
        
    }
    
}
