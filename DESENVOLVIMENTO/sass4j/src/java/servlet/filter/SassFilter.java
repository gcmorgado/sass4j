package servlet.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
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
    
    private final HashMap<String, String> cssMap = new HashMap();
    private final HashMap<String, String> sassMap = new HashMap();
    private final RubyInstanceConfig config;
    private final ScriptEngineManager manager;
    private final ScriptEngine engine;
   
    public SassFilter() {
        config = new RubyInstanceConfig();   
        config.setCompatVersion(CompatVersion.RUBY2_0);
        manager = new ScriptEngineManager();
        engine = manager.getEngineByName("jruby");                   
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {   
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, FileNotFoundException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        String sassPath = httpReq.getRequestURI().replace("/sass4j", "");
        List<File> listOfFiles = new ArrayList<>();
        File cssFile = new File(httpReq.getServletContext().getRealPath(sassPath));
        File sassFile = new File(httpReq.getServletContext().getRealPath(sassPath).replace(".css", ".scss"));
        listOfFiles.add(sassFile);
        for (File f : listOfFiles) {
        httpResp.setHeader("Content-Type", "text/css");
        Scanner scanSass = new Scanner(sassFile, "UTF-8");
        StringBuilder sass = new StringBuilder(); 
        while (scanSass.hasNextLine()) {
            sass.append(scanSass.nextLine());
        }
        if (sassFile.exists()) {
            if (cssFile.exists()) {
                chain.doFilter(request, response); 
            } else {
                OutputStream os = httpResp.getOutputStream();
                if(!cssMap.isEmpty() && !sassMap.isEmpty()) {
                    if(sassMap.get(cssFile.toString()).equals(sass.toString())) {
                        os.write(cssMap.get(cssFile.toString()).getBytes());
                        System.out.println("cacheou");
                        os.flush();
                        os.close();
                    }
                } else {  
                    String rubyFile = SassFilter.class.getResource("../../sass4j.rb").getFile();
                    String sassScript = SassFilter.class.getResource("../../sass.rb").getFile();
                    InputStreamReader isr = new InputStreamReader(new FileInputStream(rubyFile), "UTF8");
                    engine.put("sassScript", sassScript);
                    try {
                        engine.eval(isr);
                    } catch (ScriptException ex) {
                        throw new RuntimeException(ex);
                    }
                    Invocable inv = (Invocable) engine;
                    try {
                        Object ret = inv.invokeFunction("compile", sass.toString());
                        byte[] bytes = ret.toString().getBytes();
                        os.write(bytes);
                        os.flush();
                        os.close();
                        cssMap.put(cssFile.toString(), ret.toString());
                        sassMap.put(cssFile.toString(), sass.toString());
                    } catch (ScriptException | NoSuchMethodException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        
        
    }

    @Override
    public void destroy() {
    }

}
