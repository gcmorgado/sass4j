package servlet.filter;

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
        
        File file = new File(httpReq.getServletContext().getRealPath(path));
        
        if(file.exists()) {
            chain.doFilter(request, response);
        } else { 
            InputStream is = new FileInputStream(file);
            OutputStream os = httpResp.getOutputStream();
            httpResp.sendError(404, "File doesn't exists.");
        }
        
    }

    @Override
    public void destroy() {
        
    }
    
}
