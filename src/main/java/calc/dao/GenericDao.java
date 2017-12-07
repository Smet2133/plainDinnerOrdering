package calc.dao;

import calc.annotations.Column;
import calc.annotations.Id;
import calc.annotations.Table;
import calc.db.JdbcTemplateMy2;
import calc.db.ResultSetHandler;

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


    public GenericDao(Class<T> clazz){

        this.clazz = clazz;
        Annotation annotation = clazz.getAnnotation(Table.class);
        Table table = (Table)annotation;
        this.table = table.name();


        for (Field field : clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(Id.class)){
                idField = field;
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
                        field.set(entity, resultSet.getString(fieldColumns.get(field)));
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

    }

    public void delete(T t){
        try {
            sql = "DELETE FROM " + table + " WHERE " + idColumn + " = ?";
            jdbcTemplateMy2.queryWithoutResultset(sql,
                    new String[]{idField.get(t).toString()});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
