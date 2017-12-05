package calc.dao;

import calc.annotations.Column;
import calc.annotations.Id;
import calc.annotations.Table;
import calc.db.JdbcTemplateMy2;
import calc.db.ResultSetHandler;
import calc.entities.UserEntity;

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
    ResultSetHandler<T> rsHandler;

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

        rsHandler = (resultSet) -> {
            T entity = null;
            if (resultSet.next()) {
                try {
                    entity = clazz.newInstance();

                    for(Field field: entity.getClass().getDeclaredFields()){
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



    }

    public T getById(String login) {

        sql = "SELECT * FROM " + table +" WHERE LOGIN = ?";
        T t= jdbcTemplateMy2.selectWithMethod(sql, new String[]{login}, rsHandler);
        return t;
    }
}