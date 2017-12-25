package dinnOrder.servlets;

import dinnOrder.Utilities;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class CalcServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private static Logger logger = Logger.getLogger(CalcServlet.class);

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        //final  Logger logger = Logger.getLogger(CalcServlet.class);
        logger.info("In get");

        //logger.info("In get");

        HashMap<String, String> hmap = new HashMap<>();


        String notification = "";
        String plusSelected = "";
        String minusSelected = "";
        String multSelected = "";
        String divSelected = "";

        if(request.getSession().getAttribute("login") == null){
            response.sendRedirect("Authorization.do");
        }

        double arg1Value;
        double arg2Value;

        try {
            arg1Value = (request.getParameter("arg1") == null) ? 0 : Double.parseDouble(request.getParameter("arg1"));
            arg2Value = (request.getParameter("arg2") == null) ? 0 : Double.parseDouble(request.getParameter("arg2"));
        } catch (NumberFormatException e) {
            notification = "Enter correct number";
            arg1Value = 0;
            arg2Value = 0;
        }


        String operationValue = (request.getParameter("operation") == null) ? "+" : request.getParameter("operation");
        double result = 0;
        switch (operationValue) {
            case "+": result = arg1Value + arg2Value;
                    plusSelected = "selected";
            break;
            case "-": result = arg1Value - arg2Value;
                minusSelected = "selected";
            break;
            case "/": result = arg1Value / arg2Value;
                divSelected = "selected";
            break;
            case "*": result = arg1Value * arg2Value;
                multSelected = "selected";
        }

        hmap.put("\\$\\{notification}", notification);
        hmap.put("\\$\\{plusSelected}", plusSelected);
        hmap.put("\\$\\{minusSelected}", minusSelected);
        hmap.put("\\$\\{divSelected}", divSelected);
        hmap.put("\\$\\{multSelected}", multSelected);
        hmap.put("\\$\\{arg1Value}", "value=\"" + String.valueOf(arg1Value)+ "\"");
        hmap.put("\\$\\{arg2Value}", "value=\"" + String.valueOf(arg2Value)+ "\"");
        hmap.put("\\$\\{result}", String.valueOf(result));
        hmap.put("\\$\\{userName}", (String)request.getSession().getAttribute("login"));

        Utilities.printResponse(response, "html/calc.html", hmap);

    }

}