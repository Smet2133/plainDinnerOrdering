package dinnOrder.RESTServlets;

import dinnOrder.Utilities;
import dinnOrder.dao.GenericDao;
import dinnOrder.entities.OrderEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OrderREST extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String fileString;
        fileString = "{\"order\":";

        GenericDao<OrderEntity> genericDaoOrderEntity = new GenericDao<>(OrderEntity.class);

        String login = request.getParameter("login");
        if (login != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Date tomorrowDate = Utilities.tomorrowDate(date);
            //String sql = "SELECT o.id, o.date, o.set, o.soup, o.salad, o.hot, o.fixings, o.drink, o.sum FROM ORDERS o WHERE ";
            String sql = "SELECT * FROM ORDERS WHERE ";
            sql += " date >= '" + dateFormat.format(date) + " 00:00:00.000'" +
                    "  AND date < '" + dateFormat.format(tomorrowDate)  + " 00:00:00.000' ";
            sql += " AND sum < 0 AND user_id = ?";
            ArrayList<OrderEntity> arrayList = genericDaoOrderEntity.getByParameters(sql, new String[]{login});
            if(!arrayList.isEmpty()){
                OrderEntity orderEntity = null;
                orderEntity = genericDaoOrderEntity.getByParameters(sql, new String[]{login}).get(0);

                fileString += "{\"set\": \"" + orderEntity.getSet() + "\", ";
                fileString += "\"salad\": \"" + orderEntity.getSalad() + "\", ";
                fileString += "\"hot\": \"" + orderEntity.getHot() + "\", ";
                fileString += "\"soup\": \"" + orderEntity.getSoup() + "\", ";
                fileString += "\"fixings\": \"" + orderEntity.getFixings() + "\", ";
                fileString += "\"drink\": \"" + orderEntity.getDrink() + "\", ";
                fileString += "\"sum\": \"" + orderEntity.getSum() + "\"}";
            }
            else{
                fileString += "\"no order or no user\"" ;
            }
        }
        else {
            fileString += "\"no user\"" ;
        }
        fileString += "}";
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(fileString);
        out.close();

    }

}
