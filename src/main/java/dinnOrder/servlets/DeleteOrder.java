package dinnOrder.servlets;

import dinnOrder.Utilities;
import dinnOrder.dao.GenericDao;
import dinnOrder.entities.OrderEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeleteOrder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("login") == null){
            response.sendRedirect("Authorization.do");
            return;
        }

        GenericDao<OrderEntity> genericDaoOrderEntity = new GenericDao<>(OrderEntity.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date tomorrowDate = Utilities.tomorrowDate(date);
        //String sql = "SELECT o.id, o.date, o.set, o.soup, o.salad, o.hot, o.fixings, o.drink, o.sum FROM ORDERS o WHERE ";
        String sql = "SELECT * FROM ORDERS WHERE ";
        sql += " date >= '" + dateFormat.format(date) + " 00:00:00.000'" +
                "  AND date < '" + dateFormat.format(tomorrowDate)  + " 00:00:00.000' ";
        sql += " AND sum < 0 AND user_id = ?";


        try{
            OrderEntity orderEntity = genericDaoOrderEntity.getByParameters(sql,
                new String[]{(String)request.getSession().getAttribute("login")}).get(0);
            genericDaoOrderEntity.deleteById(orderEntity);
        } catch(IndexOutOfBoundsException e) {
            response.sendRedirect("Authorization.do");
            return;
        }




        response.sendRedirect("UserView.do");
    }
}
