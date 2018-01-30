package dinnOrder;

import dinnOrder.dao.GenericDao;
import dinnOrder.entities.OrderEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class Utilities {


    public static InputStream inputStreamResources(String name){
        return Utilities.class.getClassLoader().getResourceAsStream(name);
    }
    public static String stringResources(String name){
        return Utilities.class.getClassLoader().getResource(name).getPath();
    }

    public static Properties getProperties() {
        final String PROPERTIES_FILE_NAME = "prop.properties";
        //InputStream inputStream = Utilities.class.getClass().getResourceAsStream(PROPERTIES_FILE_NAME);
        InputStream inputStream = Utilities.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String inputStreamToString(InputStream is){
        StringBuilder contentBuilder = new StringBuilder();
        try( BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }


    public static boolean alreadyOrdered(String username){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dt = new java.util.Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        String date = "date >= '" + dateFormat.format(new java.util.Date()) + " 00:00:00.000'" +
                "  AND date < '" + dateFormat.format(dt)  + " 00:00:00.000'";

        String sql = "SELECT NULL FROM orders o WHERE  " + date + " AND user_id = ? AND o.sum < 0";
        GenericDao<OrderEntity> genericDaoOrderEntity = new GenericDao<>(OrderEntity.class);
        boolean ordered = genericDaoOrderEntity.getBoolByQuery(sql, new String[]{username});
        return ordered;
    }

    public static Date tomorrowDate(Date date){
        Date dt = date;
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        return dt;
    }


    public static void printResponse(HttpServletResponse response, String htmlPath, HashMap<String, String> hmap) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String fileString;
        fileString = Utilities.inputStreamToString(Utilities.inputStreamResources(htmlPath));
        for (String s : hmap.keySet()) {
            if (hmap.get(s) == null)
                fileString = fileString.replaceAll(s, "");
            fileString = fileString.replaceAll(s, hmap.get(s));
        }
        out.println(fileString);
        out.close();
    }

    public static String fileToString(String filePath){
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
