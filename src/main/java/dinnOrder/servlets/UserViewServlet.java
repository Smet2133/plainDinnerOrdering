package dinnOrder.servlets;

import dinnOrder.Utilities;
import dinnOrder.dao.GenericDao;
import dinnOrder.entities.OrderEntity;
import dinnOrder.entities.SetEntity;
import dinnOrder.entities.UserEntity;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserViewServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private static Logger logger = Logger.getLogger(UserViewServlet.class);

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        if(request.getSession().getAttribute("login") == null){
            response.sendRedirect("Authorization.do");
        }


        HashMap<String, String> hmap = new HashMap<>();

        GenericDao<UserEntity> genericDaoUserEntity = new GenericDao<>(UserEntity.class);
        GenericDao<SetEntity> genericDaoSetEntity = new GenericDao<>(SetEntity.class);
        GenericDao<OrderEntity> genericDaoOrderEntity = new GenericDao<>(OrderEntity.class);

        UserEntity userEntity = genericDaoUserEntity.getById((String)request.getSession().getAttribute("login"));


        String username = userEntity.getLogin();
        String setOptions = "";
        String plusSelected = "";
        String minusSelected = "";
        String multSelected = "";
        String divSelected = "";
        Object obj;
        int balance;

        obj = genericDaoOrderEntity.getObjectByQuery("SELECT sum(sum) s FROM orders WHERE user_id = ?",
                new String[]{username});
        if(obj != null)
            balance = ((Long)obj).intValue();
        else
            balance = 0;




        ArrayList<SetEntity> setEntities = genericDaoSetEntity.getAll();
        for(SetEntity setEntity: setEntities){
            setOptions +=   "<option value=\"" + setEntity.getName() + "\" >" + setEntity.getName() + " </option> \n";
        }

        hmap.put("\\$\\{username}", username);
        hmap.put("\\$\\{setOptions}", setOptions);
        hmap.put("\\$\\{balance}", String.valueOf(balance));

        Utilities.printResponse(response, "html/userView.html", hmap);

    }

}