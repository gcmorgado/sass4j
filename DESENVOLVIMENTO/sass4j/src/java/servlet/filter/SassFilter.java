package servlet.filter;

import com.sun.xml.bind.v2.TODO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

/**
 *
 * @author gmorgado
 */

@WebFilter(urlPatterns = "*.css", dispatcherTypes = DispatcherType.REQUEST)
public class SassFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException { 
        
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response; 
        String path = httpReq.getRequestURI().replace("/sass4j", "");
        
        
        File cssFile = new File(httpReq.getServletContext().getRealPath(path));
        File sassFile = new File(httpReq.getServletContext().getRealPath(path).replace(".css", ".sass"));
        
        InputStream is = new FileInputStream(sassFile);
        OutputStream os = httpResp.getOutputStream();
        
        //if(cssFile.exists()) {
            //chain.doFilter(request, response);
        //} else if(sassFile.exists()) {
            // TODO:
            // Verificar se há o arquivo em cache
            //      Se há o arquivo, verificar se a versão em cache é igual a última versão
            //      Se não é igual, compilar novamente, devolver o arquivo final e cachear novamente o arquivo.
        //} else {
            SassFileHandler sfh = new SassFileHandler();
            sfh.fileHandler(is, os);
        //}
        //httpResp.sendError(404, "File doesn't exists.");
    }

    @Override
    public void destroy() {
        
    }
    
}
