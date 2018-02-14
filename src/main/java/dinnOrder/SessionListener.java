package dinnOrder;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HashMap<String, String> userSessionsMap = null;
        userSessionsMap = (HashMap<String, String>)httpSessionEvent.getSession().getServletContext().getAttribute("userSessionsMap");
        if(httpSessionEvent.getSession().getAttribute("login") != null)
            for(String s: userSessionsMap.keySet()){
                if(userSessionsMap.get(s) == httpSessionEvent.getSession().getAttribute("login")){
                    userSessionsMap.remove(s);
                }
            }
        httpSessionEvent.getSession().getServletContext().setAttribute("userSessionsMap", userSessionsMap);
    }
}
