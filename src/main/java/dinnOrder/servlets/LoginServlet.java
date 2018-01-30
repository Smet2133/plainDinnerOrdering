package dinnOrder.servlets;

import dinnOrder.Utilities;
import dinnOrder.dao.GenericDao;
import dinnOrder.entities.UserEntity;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(CalcServlet.class);


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(isAuthorized(request)){
            //redirect
            response.sendRedirect("UserView.do");
            return;
        }

        HashMap<String, String> hmap = new HashMap<>();
        String notification = "";
        String action = "Login";
        String registrationLink = "";
        String loginLink = "";

        if(request.getSession().getAttribute("registration") != null && request.getSession().getAttribute("registration").equals("true")) {

            loginLink = "Login";
            action = "Registration";

            if (request.getParameter("login") != null && request.getParameter("password") != null) {
                if (registrationApproved(request.getParameter("login"), request.getParameter("password"))) {
                    request.getSession().setAttribute("login", request.getParameter("login"));
                    request.getSession().removeAttribute("registration");
                    response.sendRedirect("UserView.do");
                } else {
                    notification = "Login is used";
                }
            }



        } else {

            registrationLink = "Registration";
            if (request.getParameter("login") == null || request.getParameter("password") == null) {
                //first time
                //show plain index.html
                //printResponse(response, "");
            } else {
                //not first time
                if (loginApproved(request.getParameter("login"), request.getParameter("password"))) {
                    request.getSession().setAttribute("login", request.getParameter("login"));
                    //redirect
                    response.sendRedirect("UserView.do");
                } else {
                    //add info to session to notify
                    //show index.html
                    //printResponse(response, "Login or password is incorrect");
                    notification = "Login or password is incorrect";
                }
            }

        }

        hmap.put("\\$\\{notification}", notification);
        hmap.put("\\$\\{action}", action);
        hmap.put("\\$\\{registrationLink}", registrationLink);
        hmap.put("\\$\\{loginLink}", loginLink);

        Utilities.printResponse(response,"html/index.html", hmap);

    }

    private boolean registrationApproved(String login, String password) {
        GenericDao<UserEntity> genericDaoUserEntity = new GenericDao<>(UserEntity.class);
        UserEntity userEntity = new UserEntity(login, password, "user");
        return genericDaoUserEntity.createWithId(userEntity);
    }

    private boolean loginApproved(String login, String password) {

        GenericDao<UserEntity> genericDaoUserEntity = new GenericDao<>(UserEntity.class);
        ArrayList<UserEntity> userEntities = null;


        try {
            userEntities = genericDaoUserEntity.getByParameters(new Field[]{UserEntity.class.getDeclaredField("login"),
                    UserEntity.class.getDeclaredField("password")}, new String[]{login, password});
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        if(userEntities != null && userEntities.size() == 1)
            return true;
        else
            return false;


/*
//        JdbcTemplateMy1 jdbcTemplate1= JDBCconfig.getJdbcTemplate();
        JdbcTemplateMy1 jdbcTemplateMy1= new JdbcTemplateMy1();
        JdbcTemplateMy2 jdbcTemplateMy2 = new JdbcTemplateMy2();
        String sql = "SELECT COUNT(*) FROM USERS WHERE login = ? AND password = ?";

        //if(jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{login, password}) == 1)
*//*        if(jdbcTemplateMy1.queryForInt(sql, new String[]{login, password}) == 1)
            return true;
        else
            return false;*//*
*//*        if((int)(long)jdbcTemplateMy2.queryForObject(sql, new String[]{login, password}) == 1)
            return true;
        else
            return false;*//*

        ResultSetHandler<Integer> resultSetHandler = (resultSet) -> {
            Integer integer = null;
            resultSet.next();
            integer = resultSet.getInt(1);
            return integer;
        };
        int i = jdbcTemplateMy2.selectWithMethod(sql, new String[]{login, password}, resultSetHandler);
        if(i == 1)
            return true;
        else
            return false;*/
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
        if(request.getParameter("registration") != null && request.getParameter("registration").equals("true"))
            request.getSession().setAttribute("registration", "true");
        if(request.getParameter("registration") != null && request.getParameter("registration").equals("false"))
            request.getSession().setAttribute("registration", "false");

        doPost(request,response);
    }
}
