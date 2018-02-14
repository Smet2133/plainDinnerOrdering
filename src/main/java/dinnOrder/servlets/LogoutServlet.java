package dinnOrder.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        request.getSession().removeAttribute("login");
        HashMap<String, String> userSessionsMap = null;
        userSessionsMap = (HashMap<String, String>)getServletContext().getAttribute("userSessionsMap");
        userSessionsMap.remove(request.getSession().getId());
        getServletContext().setAttribute("userSessionsMap", userSessionsMap);
        */
        request.getSession().invalidate();
        response.sendRedirect("Authorization.do");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
