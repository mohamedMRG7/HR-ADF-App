package auth;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.LocalizationClass;
import view.MainClass;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Implement this method
    }
    private final String LOG_IN_PAGE_PATHE = "/logIn";
    public static final String LOG_IN_SESSION_KEY = "isLogedIn";
    public static final String USER_INFO_SESSION_KEY="userInfo";


    /*Confirm that the user has been loged in during this session else redirect to log in screen*/
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //get the requst page name
        String reqPageName = request.getPathInfo();
      
        Boolean isLogedIn = (Boolean) request.getSession(true).getAttribute(LOG_IN_SESSION_KEY);
        
        //if its the first session for the user make isLogIn=false insted of null
        if (isLogedIn == null)
            isLogedIn = false;
        
        if (isLogedIn) {
            //if isLogedIn true continue to requst page
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            //if isLogedIn false check the requste page if it the login page continue if its not redirect to logIn page
            if (!reqPageName.equals(LOG_IN_PAGE_PATHE))
                response.sendRedirect(request.getContextPath() + "/faces" + LOG_IN_PAGE_PATHE);
            else
                filterChain.doFilter(servletRequest, servletResponse);

        }

    }

    @Override
    public void destroy() {
        // TODO Implement this method
    }
}
