package dinnOrder.servlets;

import dinnOrder.Utilities;
import dinnOrder.dao.GenericDao;
import dinnOrder.entities.OrderEntity;
import dinnOrder.entities.SetEntity;
import dinnOrder.entities.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AlreadyOrderedServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        if(request.getSession().getAttribute("login") == null){
            response.sendRedirect("Authorization.do");
            return;
        }

        HashMap<String, String> hmap = new HashMap<>();

        GenericDao<UserEntity> genericDaoUserEntity = new GenericDao<>(UserEntity.class);
        GenericDao<SetEntity> genericDaoSetEntity = new GenericDao<>(SetEntity.class);
        GenericDao<OrderEntity> genericDaoOrderEntity = new GenericDao<>(OrderEntity.class);

        UserEntity userEntity = genericDaoUserEntity.getById((String)request.getSession().getAttribute("login"));
        String username = userEntity.getLogin();



        OrderEntity orderEntity = null;
        String todayYouMade = "";
        String currentOrder = "";

        if(!Utilities.alreadyOrdered(username)){
            orderEntity = new OrderEntity();

            orderEntity.setUser_id(username);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            orderEntity.setDate(dateFormat.format(new java.util.Date()));

            try {
                orderEntity.setSet(request.getParameter("set"));
                String parseSoup = request.getParameter("set");
                orderEntity.setSoup(parseSoup.indexOf("Суп") > -1);
                orderEntity.setSalad(request.getParameter("salad"));
                orderEntity.setHot(request.getParameter("hot"));
                orderEntity.setFixings(request.getParameter("fixings"));
                orderEntity.setDrink(request.getParameter("drink"));


                ArrayList<SetEntity> setEntity = null;
                try {
                    setEntity = genericDaoSetEntity.getByParameters(new Field[]{SetEntity.class.getDeclaredField("name")},
                            new String[]{request.getParameter("set")});
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                orderEntity.setSum(-setEntity.get(0).getPrice());

            } catch (NullPointerException e){
                response.sendRedirect("UserView.do");
                return;
            }

            genericDaoOrderEntity.createWithoutId(orderEntity);

            todayYouMade = "You have made your order";
        } else {
            todayYouMade = "Today you have already make your order";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Date tomorrowDate = Utilities.tomorrowDate(date);
            //String sql = "SELECT o.id, o.date, o.set, o.soup, o.salad, o.hot, o.fixings, o.drink, o.sum FROM ORDERS o WHERE ";
            String sql = "SELECT * FROM ORDERS WHERE ";
            sql += " date >= '" + dateFormat.format(date) + " 00:00:00.000'" +
                    "  AND date < '" + dateFormat.format(tomorrowDate)  + " 00:00:00.000' ";
            sql += " AND sum < 0 AND user_id = ?";

            try {
                orderEntity = genericDaoOrderEntity.getByParameters(sql, new String[]{username}).get(0);
            } catch(IndexOutOfBoundsException e) {
                response.sendRedirect("Authorization.do");
                return;
            }


        }


        currentOrder = "<span> Набор: " + orderEntity.getSet() + "</span> <br>\n";
        currentOrder += "<span> Суп: " + orderEntity.getSoup() + "</span> <br>\n";
        currentOrder += "<span> Салат: " + orderEntity.getSalad() + "</span> <br>\n";
        currentOrder += "<span> Горячее: " + orderEntity.getHot() + "</span> <br>\n";
        currentOrder += "<span> Гарнир: " + orderEntity.getFixings() + "</span> <br>\n";
        currentOrder += "<span> Напиток: " + orderEntity.getDrink() + "</span> <br>\n";
        currentOrder += "<span> Сумма: " + orderEntity.getSum() + "</span> <br>\n";




        hmap.put("\\$\\{currentOrder}", currentOrder);
        hmap.put("\\$\\{username}", username);
        hmap.put("\\$\\{todayYouMade}", todayYouMade);


        Utilities.printResponse(response, "html/alreadyOrdered.html", hmap);

    }



}