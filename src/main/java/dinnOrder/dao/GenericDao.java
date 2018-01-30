package dinnOrder.dao;

import dinnOrder.annotations.Column;
import dinnOrder.annotations.Id;
import dinnOrder.annotations.Table;
import dinnOrder.db.JdbcTemplateMy2;
import dinnOrder.db.ResultSetHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class GenericDao<T> {

    JdbcTemplateMy2 jdbcTemplateMy2 = new JdbcTemplateMy2();
    String sql;
    Class clazz;
    String table;
    String idColumn;
    Field idField;

    HashMap<Field, String> fieldColumns = new HashMap<>();
    ResultSetHandler<T> rsHandlerForOne;
    ResultSetHandler<ArrayList<T>> rsHandlerForMany;
    ResultSetHandler<Object> rsHandlerForObject;
    ResultSetHandler<Boolean> rsHandlerForExistence;


    public GenericDao(Class<T> clazz){

        this.clazz = clazz;
        Annotation annotation = clazz.getAnnotation(Table.class);
        Table table = (Table)annotation;
        this.table = table.name();


        for (Field field : clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(Id.class)){
                idField = field;
                idField.setAccessible(true);
                annotation = field.getAnnotation(Column.class);
                Column column = (Column)annotation;
                idColumn = column.name();
            }
            if(field.isAnnotationPresent(Column.class)){
                annotation = field.getAnnotation(Column.class);
                Column column = (Column)annotation;
                fieldColumns.put(field, column.name());
            }
        }

        rsHandlerForOne = (resultSet) -> {
            T entity = null;
            if (resultSet.next()) {
                try {
                    entity = clazz.newInstance();
                    for(Field field: entity.getClass().getDeclaredFields()){
                        field.setAccessible(true);
                        field.set(entity, resultSet.getString(fieldColumns.get(field)));
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return entity;
        };

        rsHandlerForMany = (resultSet) -> {
            ArrayList<T> list = new ArrayList<>();
            T entity = null;
            while(resultSet.next()) {
                try {
                    entity = clazz.newInstance();
                    for(Field field: entity.getClass().getDeclaredFields()){
                        field.setAccessible(true);
                        if(field.getType().equals(String.class)){
                            field.set(entity, resultSet.getString(fieldColumns.get(field)));
                        } else if((field.getType().equals(Integer.TYPE))){
                            field.set(entity, resultSet.getInt(fieldColumns.get(field)));
                        }

                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                list.add(entity);
            }
            return list;
        };

        rsHandlerForObject = (resultSet) -> {
            Object obj = null;
            if(resultSet.next()) {
                obj = resultSet.getObject(1);
            }
            return obj;
        };

        rsHandlerForExistence = (resultSet) -> {
            return resultSet.next();
        };

    }



    public boolean createWithId(T t){

        String values = null;
        try {

            values = " VALUES (";
            sql = "INSERT INTO " + table + " (";

            for(Field field: fieldColumns.keySet()){
                sql += fieldColumns.get(field) + ",";
                field.setAccessible(true);
                values += "'" + field.get(t) + "',";
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        sql = sql.substring(0, sql.length()-1);
        values = values.substring(0, values.length()-1);

        sql += ")";
        sql += values + ")";

        return jdbcTemplateMy2.queryWithoutResultset(sql, null);
    }

    public boolean createWithoutId(T t){

        String values = null;
        try {

           // values = " VALUES ('" + idField.get(t) + "'";
           // sql = "INSERT INTO " + table + " (" + idColumn + ""; //+ +
            values = " VALUES (";
            sql = "INSERT INTO " + table + " (";

            for(Field field: fieldColumns.keySet()){
                if(fieldColumns.get(field).equals(idColumn))
                    continue;
                //sql += ", " + fieldColumns.get(field);
                //field.setAccessible(true);
                //values += ", '" + field.get(t) + "'";
                sql += fieldColumns.get(field) + ",";
                field.setAccessible(true);
                values += "'" + field.get(t) + "',";
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        sql = sql.substring(0, sql.length()-1);
        values = values.substring(0, values.length()-1);

        sql += ")";
        sql += values + ")";

        return jdbcTemplateMy2.queryWithoutResultset(sql, null);
    }

    public void deleteById(T t){
        try {
            sql = "DELETE FROM " + table + " WHERE " + idColumn + " = ?";
            jdbcTemplateMy2.queryWithoutResultset(sql,
                    new String[]{idField.get(t).toString()});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Object getObjectByQuery(String sql, String[] values){
        return jdbcTemplateMy2.selectWithMethod(sql, values, rsHandlerForObject);
    }

    public boolean getBoolByQuery(String sql, String[] values){
        return jdbcTemplateMy2.selectWithMethod(sql, values, rsHandlerForExistence);
    }

    public ArrayList<T> getAll(){
        sql = "SELECT * FROM " + table ;
        ArrayList<T> list = jdbcTemplateMy2.selectWithMethod(sql, new String[]{}, rsHandlerForMany);
        return list;
    }

    public ArrayList<T> getByParameters(String sql, String[] values){
        ArrayList<T> list = jdbcTemplateMy2.selectWithMethod(sql, values, rsHandlerForMany);
        return list;
    }


    public ArrayList<T> getByParameters(Field[] fields, String[] values){
        sql = "SELECT * FROM " + table + " WHERE ";
        if(fields.length < 1){
            return null;
        }

        sql += fieldColumns.get(fields[0]) + " = ? ";

        if(fields.length > 1){
            for(int i = 1; i < fields.length; i++){
                sql += " AND " + fieldColumns.get(fields[i]) + " = ? ";
            }
        }

        //sql created

        ArrayList<T> list = jdbcTemplateMy2.selectWithMethod(sql, values, rsHandlerForMany);
        return list;
    }

    public T getById(String login) {

        sql = "SELECT * FROM " + table + " WHERE " + idColumn + " = ?";
        T t= jdbcTemplateMy2.selectWithMethod(sql, new String[]{login}, rsHandlerForOne);
        return t;
    }
}
