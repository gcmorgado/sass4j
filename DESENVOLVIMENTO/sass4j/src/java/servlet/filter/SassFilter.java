package servlet.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.jruby.embed.ScriptingContainer;
import org.jruby.javasupport.JavaEmbedUtils.EvalUnit;

/**
 *
 * @author gmorgado
 */

@WebFilter(urlPatterns = "*.css", dispatcherTypes = DispatcherType.REQUEST)
public class SassFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
        String rubyFile = this.getClass().getResource("../../gem/teste.rb").getFile();
        ScriptingContainer container = new ScriptingContainer();
        try {
            EvalUnit unit = container.parse(new FileReader(rubyFile), rubyFile);
        } catch (FileNotFoundException ex) {
            // Tratar exceção para falha ao achar o arquivo ruby
            Logger.getLogger(SassFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, FileNotFoundException {
        
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        String sassPath = httpReq.getRequestURI().replace("/sass4j", "");

        File cssFile = new File(httpReq.getServletContext().getRealPath(sassPath));
        File sassFile = new File(httpReq.getServletContext().getRealPath(sassPath).replace(".css", ".sass"));
        
        if (sassFile.exists()) {
            InputStream is = new FileInputStream(sassFile);
            OutputStream os = httpResp.getOutputStream();

            if (cssFile.exists()) {
                chain.doFilter(request, response);
                //} else if(sassFile.exists()) {
                // TODO:
                // Verificar se há o arquivo em cache
                //      Se há o arquivo, verificar se a versão em cache é igual a última versão
                //      Se não é igual, compilar novamente, devolver o arquivo final e cachear novamente o arquivo.
            } else {
//                SassFileHandler sfh = new SassFileHandler();
//                sfh.fileHandler(is, os);
                Scanner scan = new Scanner(is);
                StringBuilder sass = new StringBuilder();
                while (scan.hasNextLine()) {
                    sass.append(scan.nextLine());
                }
            }
        }
    }

    @Override
    public void destroy() {

    }

}
