package dinnOrder.RESTServlets;

import dinnOrder.dao.GenericDao;
import dinnOrder.entities.SetEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuREST extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String fileString;
        fileString = "{\"set\":[";

        GenericDao<SetEntity> genericDaoSetEntity = new GenericDao<>(SetEntity.class);
        ArrayList<SetEntity> setEntities = genericDaoSetEntity.getAll();
        for(SetEntity setEntity: setEntities){
            fileString +=  "\"" + setEntity.getName() + "\"," ;
        }
        if(!setEntities.isEmpty())
            fileString = fileString.substring(0, fileString.length() - 1);


        fileString += "]}";

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(fileString);
        out.close();
    }
}
