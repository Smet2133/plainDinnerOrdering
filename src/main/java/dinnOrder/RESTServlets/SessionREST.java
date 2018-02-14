package dinnOrder.RESTServlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class SessionREST extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String fileString;
        fileString = "{\"sessionID\":[";






        String login = request.getParameter("login");
        if (login != null) {
            HashMap<String, String> userSessionsMap = null;
            userSessionsMap = (HashMap<String, String>) getServletContext().getAttribute("userSessionsMap");
            for (String sessionIDMap : userSessionsMap.keySet()) {
                if (userSessionsMap.get(sessionIDMap).equals(login)) {
                    fileString += "\"" + sessionIDMap + "\"," ;
                }
            }
            if (!userSessionsMap.isEmpty())
                fileString = fileString.substring(0, fileString.length() - 1);
        }
        else  fileString += "\"no user\"" ;
        fileString += "]}";

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(fileString);
        out.close();
    }
}
