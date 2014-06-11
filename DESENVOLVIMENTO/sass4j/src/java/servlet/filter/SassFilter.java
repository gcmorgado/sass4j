package servlet.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jruby.CompatVersion;
import org.jruby.RubyInstanceConfig;

/**
 *
 * @author gmorgado
 */

@WebFilter(urlPatterns = "*.css", dispatcherTypes = DispatcherType.REQUEST)
public class SassFilter implements Filter {
    
    private final HashMap<String, String[]> map = new HashMap();
    private final RubyInstanceConfig config;
    private final ScriptEngineManager manager;
    private final Invocable engine;
   
    public SassFilter() {
        config = new RubyInstanceConfig();   
        config.setCompatVersion(CompatVersion.RUBY2_0);
        manager = new ScriptEngineManager();
        ScriptEngine script = manager.getEngineByName("jruby"); 
        engine = (Invocable) script;
        String rubyFile = SassFilter.class.getResource("../../sass4j.rb").getFile();
        String sassScript = SassFilter.class.getResource("../../sass.rb").getFile();
        script.put("sassScript", sassScript);
        InputStreamReader isr;
        try {
            isr = new InputStreamReader(new FileInputStream(rubyFile), "UTF8");
            script.eval(isr);
        } catch (FileNotFoundException | UnsupportedEncodingException | ScriptException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public String compileSass(StringBuilder sass) {
        
        try {
            return engine.invokeFunction("compile", sass.toString()).toString();
        } catch (ScriptException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {   
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        String sassPath = httpReq.getRequestURI().replace("/sass4j", "");
        File cssFile = new File(httpReq.getServletContext().getRealPath(sassPath));
        
        if (cssFile.exists()) {
            chain.doFilter(request, response);
        } else {
            File sassFile = new File(httpReq.getServletContext().getRealPath(sassPath).replace(".css", ".scss"));
            if(sassFile.exists()) {
                OutputStream os = httpResp.getOutputStream();
                byte[] bytesCss;
                if(map.get(cssFile.toString())[0].equals(String.valueOf(sassFile.lastModified()))) {
                    bytesCss = map.get(cssFile.toString())[1].getBytes();   
                } else {
                    Scanner scanSass = new Scanner(sassFile, "UTF-8");
                    StringBuilder sass = new StringBuilder(); 
                    while (scanSass.hasNextLine()) {
                        sass.append(scanSass.nextLine());
                    }
                    String sassCompilado = compileSass(sass);
                    bytesCss = sassCompilado.getBytes();
                    map.put(cssFile.toString(), new String[]{String.valueOf(sassFile.lastModified()), sassCompilado});
                }
                httpResp.setHeader("Content-Type", "text/css");
                os.write(bytesCss);
                os.flush();
                os.close();
            } else {
                httpResp.sendError(404, "Arquivos SCSS e CSS não foram encontrado.");
            }
        }
    }

    @Override
    public void destroy() {
    }
    
}