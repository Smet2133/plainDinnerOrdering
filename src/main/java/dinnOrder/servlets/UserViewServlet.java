package dinnOrder.servlets;

import dinnOrder.Utilities;
import dinnOrder.dao.GenericDao;
import dinnOrder.entities.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
            return;
        }

        HashMap<String, String> hmap = new HashMap<>();
        GenericDao<UserEntity> genericDaoUserEntity = new GenericDao<>(UserEntity.class);
        GenericDao<SetEntity> genericDaoSetEntity = new GenericDao<>(SetEntity.class);
        GenericDao<OrderEntity> genericDaoOrderEntity = new GenericDao<>(OrderEntity.class);

        UserEntity userEntity = genericDaoUserEntity.getById((String)request.getSession().getAttribute("login"));
        String username = userEntity.getLogin();

        if(Utilities.alreadyOrdered(username)){
            response.sendRedirect("AlreadyOrdered.do");
            return;
        }

        String setOptions = "";
        String saladOptions = "";
        String hotOptions = "";
        String fixingsOptions = "";
        String drinkOptions = "";
        String disabled = "";

        //get balance
        Object obj;
        int balance;
        obj = genericDaoOrderEntity.getObjectByQuery("SELECT sum(sum) s FROM orders WHERE user_id = ?",
                new String[]{username});
        if(obj != null)
            balance = ((Long)obj).intValue();
        else
            balance = 0;

        //get set options
        ArrayList<SetEntity> setEntities = genericDaoSetEntity.getAll();
        for(SetEntity setEntity: setEntities){
            setOptions +=   "<option value=\"" + setEntity.getName() + "\" >" + setEntity.getName() + " </option> \n";
        }

        ArrayList<SaladEntity> saladEntities = new GenericDao<>(SaladEntity.class).getAll();
        for(SaladEntity saladEntity: saladEntities){
            saladOptions +=   "<option value=\"" + saladEntity.getName() + "\" >" + saladEntity.getName() + " </option> \n";
        }

        ArrayList<HotEntity> hotEntities = new GenericDao<>(HotEntity.class).getAll();
        for(HotEntity hotEntity: hotEntities){
            hotOptions +=   "<option value=\"" + hotEntity.getName() + "\" >" + hotEntity.getName() + " </option> \n";
        }

        ArrayList<FixingsEntity> fixingsEntities = new GenericDao<>(FixingsEntity.class).getAll();
        for(FixingsEntity fixingsEntity: fixingsEntities){
            fixingsOptions +=   "<option value=\"" + fixingsEntity.getName() + "\" >" + fixingsEntity.getName() + " </option> \n";
        }

        ArrayList<DrinkEntity> drinkEntities = new GenericDao<>(DrinkEntity.class).getAll();
        for(DrinkEntity drinkEntity: drinkEntities){
            drinkOptions +=   "<option value=\"" + drinkEntity.getName() + "\" >" + drinkEntity.getName() + " </option> \n";
        }

        hmap.put("\\$\\{username}", username);
        hmap.put("\\$\\{setOptions}", setOptions);
        hmap.put("\\$\\{saladOptions}", saladOptions);
        hmap.put("\\$\\{hotOptions}", hotOptions);
        hmap.put("\\$\\{fixingsOptions}", fixingsOptions);
        hmap.put("\\$\\{drinkOptions}", drinkOptions);


        hmap.put("\\$\\{balance}", String.valueOf(balance));
        hmap.put("\\$\\{disabled}", disabled);


        Utilities.printResponse(response, "html/userView.html", hmap);

    }




}