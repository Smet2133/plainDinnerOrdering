package dinnOrder.db;

import dinnOrder.Utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class JdbcTemplateMy1 {


    private String DriverClassName;
    private String Url;
    private String Username;
    private String Password;


    public JdbcTemplateMy1() {
        Properties properties = new Utilities().getProperties();

        String s = JdbcTemplateMy1.class.getClassLoader().getResource("myDB.mv.db").toString();
        s = s.replaceAll(".mv.db", "");
        s = s.replaceAll("%20", " ");
        s = "jdbc:h2:" + s;

        DriverClassName = properties.getProperty("driver");
        Url = s;
        Username =  properties.getProperty("username");
        Password =  properties.getProperty("password");
        try {
            Class.forName(DriverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public JdbcTemplateMy1(String driverClassName, String url, String username, String password) {
        DriverClassName = driverClassName;
        Url = url;
        Username = username;
        Password = password;
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> query(String sql, String[] strings){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<ArrayList<Object>> arrayListOuter = new ArrayList<>();
        ArrayList<Object> arrayListInner;
        try {
            connection = DriverManager.getConnection(Url, Username, Password);
            statement = connection.prepareStatement(sql);
            for(int i = 0; i < strings.length; i++){
                statement.setString(i + 1, strings[i]);
            }
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                arrayListInner = new ArrayList<>();
                for (int i = 1; i < resultSet.getMetaData().getColumnCount() + 1; i++) {
                    arrayListInner.add(resultSet.getObject(i));
                }
                arrayListOuter.add(arrayListInner);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(connection!=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        return arrayListOuter;
    }

    public int queryForInt(String sql, String[] strings){
        ArrayList<ArrayList<Object>> arrayList = query(sql, strings);
        return (int)(long)(arrayList.get(0)).get(0);
    }

    /*

    public  <T>baseQuery(String sql, Class<T> requiredType, String[] strings){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        T result = null;
        try {
            connection = DriverManager.getConnection(Url, Username, Password);
            statement = connection.prepareStatement(sql);
            result = queryForIntBody(connection, statement);
        }  catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(connection!=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        return result;

    }

    private <T> T queryForIntBody(Connection connection, PreparedStatement statement) throws SQLException {
        ResultSet resultSet;
        for(int i = 0; i < strings.length; i++){
            statement.setString(i + 1, strings[i - 1]);
        }
        resultSet = statement.executeQuery();
        resultSet.next();
        return (T)(new Integer(resultSet.getInt(1));
    }

    public Integer queryForInt(String sql, String[] strings){
        return  baseQuery(sql, class.Integer, String[] strings)

   *//*     Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet;
        int result = 0;
        try {
            connection = DriverManager.getConnection(Url, Username, Password);
            statement = connection.prepareStatement(sql);
            for(int i = 0; i < strings.length; i++){
                statement.setString(i + 1, strings[i - 1]);
            }
            resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1);
        }  catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(connection!=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        return result;*//*
    }
*/
}
