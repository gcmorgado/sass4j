package servlet.filter;

import java.io.File;
import java.io.IOException;
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
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException { 
        
        SassFileHandler sfh = new SassFileHandler();
        
        if(request instanceof HttpServletRequest && response instanceof HttpServletResponse) { 
            HttpServletRequest httpReq = (HttpServletRequest) request;
            HttpServletResponse httpResp = (HttpServletResponse) response; 
            String fileName = httpReq.getRequestURI().substring(httpReq.getRequestURI().lastIndexOf("/"), httpReq.getRequestURI().length());
            if (fileName.toLowerCase().endsWith("css")) { 
                //httpResp.setContentType("text/css"); 
                
            } 
            //httpResp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\""); 
        } 
        
        
        
        
        chain.doFilter(request, response);  
    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
