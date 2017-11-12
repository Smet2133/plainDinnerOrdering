package calc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class CalcServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        HashMap<String, String> hmap = new HashMap<>();


        String notification = "";;
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

        printResponse(response, hmap);
/*
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Calculator</title>\n      <link rel=\"stylesheet\" type=\"text/css\" href=\"calcStyle.css\">" +
                "</head>\n" +
                "<body>\n" +
                "    <p>Calculator</p>\n" +
                "    <form method=\"GET\"\n" +
                "          action=\"Calc.do\">\n" +
                "    <div class=\"container\">" +
                "        Argument 1:\n");

        out.println(" <input type=\"text\" name=\"arg1\" value=\"" + arg1Value + "\"> ");
        out.println("  <br>\n" +
                "        Argument 2:");
        out.println(" <input type=\"text\" name=\"arg2\" value=\"" + arg2Value + "\"> <br>");
        out.println("Operation:\n" +
                "        <select name=\"operation\" value=\"" + operationValue + "\">\n" +
                "            <option value=\"+\" " + ((operationValue.equals("+")) ? "selected" : "") + "> + </option>\n" +
                "            <option value=\"-\" " + ((operationValue.equals("-")) ? "selected" : "") + "> - </option>\n" +
                "            <option value=\"*\" " + ((operationValue.equals("*")) ? "selected" : "") + "> * </option>\n" +
                "            <option value=\"/\" " + ((operationValue.equals("/")) ? "selected" : "") + "> / </option>\n" +
                "        </select>\n" +
                "        <br>");
        out.println("Result:\n" + result +
                "        <br><br>\n" +
                "        <input type=\"SUBMIT\">\n" +
                "        </div>" +
                "    </form>\n<br><br>" +
                "\n" +
                "\n" +
                      "Login:"  + request.getSession().getAttribute("login") +
                "<form method=\"POST\"\n" +
                "action=\"Logout.do\">\n" +
                "<input type=\"SUBMIT\" value=\"logout\">\n" +
                "</form>" +
                "</body>\n" +
                "</html>");*/

    }

    private void printResponse(HttpServletResponse response, HashMap<String, String> hmap) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String fileString;
        fileString = Utilities.inputStreamToString(Utilities.inputStreamResources("html/calc.html"));
        for (String s : hmap.keySet()) {
            if (hmap.get(s) == null)
                continue;
            fileString = fileString.replaceAll(s, hmap.get(s));
        }
        out.println(fileString);
        out.close();
    }

}