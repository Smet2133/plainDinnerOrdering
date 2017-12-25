package dinnOrder.ajax;

import dinnOrder.dao.GenericDao;
import dinnOrder.entities.SetEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class SumAjax extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<SetEntity> setEntity = null;
        GenericDao<SetEntity> genericDaoSetEntity = new GenericDao<>(SetEntity.class);
        try {
            setEntity = genericDaoSetEntity.getByParameters(new Field[]{SetEntity.class.getDeclaredField("name")},
                    new String[]{request.getParameter("set")});
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print(String.valueOf(setEntity.get(0).getPrice()));


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
