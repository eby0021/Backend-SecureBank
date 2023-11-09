package select.util;


import javax.servlet.*;
import java.io.IOException;

/**
 * Project: OP-Bank
 * Module ID:
 * Comments:
 * JDK version used: JDK 17
 * Namespace: std
 * Author： YeTian
 * Create Date： 9/10/2023
 * Modified By： None
 * Modified Date: None
 * Why & What is modified: None
 * Version: 0.1.0
 */
public class LogCostFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis() ;
        chain.doFilter(request,response);
        System.out.println("Execution time Time is："+ (System.currentTimeMillis()-start));
    }

    @Override
    public void destroy() {

    }
}
