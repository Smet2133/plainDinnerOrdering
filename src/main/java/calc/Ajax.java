package calc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Ajax extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        double arg1Value, arg2Value;
        String arg1 = request.getParameter("arg1");
        String arg2 = request.getParameter("arg2");
        try {
            arg1Value = (arg1 == null) ? 0 : Double.parseDouble(arg1);
            arg2Value = (arg2 == null) ? 0 : Double.parseDouble(arg2);
        } catch (NumberFormatException e) {
            out.print("Enter correct number");
            return;
        }

        double result = 0;
        String operationValue = (request.getParameter("operation") == null) ? "+" : request.getParameter("operation");
        switch (operationValue) {
            case "+": result = arg1Value + arg2Value;
                break;
            case "-": result = arg1Value - arg2Value;
                break;
            case "/": result = arg1Value / arg2Value;
                break;
            case "*": result = arg1Value * arg2Value;
                break;
        }

        out.print(String.valueOf(result));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //doPost(request,response);
    }
}
