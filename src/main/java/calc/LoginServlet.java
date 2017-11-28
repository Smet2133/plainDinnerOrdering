package calc;

import calc.db.JDBCconfig;
import calc.db.JdbcTemplateMy1;
import calc.db.JdbcTemplateMy2;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(CalcServlet.class);


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        if(isAuthorized(request)){
            //redirect
            response.sendRedirect("Calc.do");
        } else {
            if(request.getParameter("login") == null || request.getParameter("password") == null){
                //first time
                //show plain index.html
                printResponse(response, "");
            } else {
                //not first time
                if (loginApproved(request.getParameter("login"), request.getParameter("password"))) {
                    request.getSession().setAttribute("login", request.getParameter("login"));
                    //redirect
                    response.sendRedirect("Calc.do");
                } else {
                    //add info to session to notify
                    //show index.html
                    printResponse(response, "Login or password is incorrect");
                }
            }

        }
    }

    private boolean loginApproved(String login, String password) {
//        JdbcTemplateMy1 jdbcTemplate1= JDBCconfig.getJdbcTemplate();
        JdbcTemplateMy1 jdbcTemplateMy1= new JdbcTemplateMy1();
        JdbcTemplateMy2 jdbcTemplateMy2 = new JdbcTemplateMy2();
        String sql = "SELECT COUNT(*) FROM USERS WHERE email = ? AND password = ?";

        //if(jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{login, password}) == 1)
/*        if(jdbcTemplateMy1.queryForInt(sql, new String[]{login, password}) == 1)
            return true;
        else
            return false;*/
        if((int)(long)jdbcTemplateMy2.queryForObject(sql, new String[]{login, password}) == 1)
            return true;
        else
            return false;
    }

    private void printResponse(HttpServletResponse response, String notification) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String fileString;
        fileString = Utilities.inputStreamToString(Utilities.inputStreamResources("html/index.html"));
        fileString = fileString.replaceAll("\\$\\{notification}", notification);
        out.println(fileString);
        out.close();
    }

    private boolean isAuthorized(HttpServletRequest request) {
        return request.getSession().getAttribute("login") != null;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
