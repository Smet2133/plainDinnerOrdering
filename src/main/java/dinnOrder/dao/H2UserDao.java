package dinnOrder.dao;

import dinnOrder.db.JdbcTemplateMy2;
import dinnOrder.db.ResultSetHandler;
import dinnOrder.entities.UserEntity;

import java.util.ArrayList;

public class H2UserDao implements UserDao {

        JdbcTemplateMy2 jdbcTemplateMy2 = new JdbcTemplateMy2();
        String sql;

        ResultSetHandler<UserEntity> rsHandlerGetUserEntity = (resultSet) -> {
            UserEntity userEntity = null;
            if (resultSet.next()) {
                userEntity = new UserEntity(resultSet.getString("LOGIN"),
                        resultSet.getString("PASSWORD"), resultSet.getString("ROLE"));

            }
            return userEntity;
        };


    @Override
    public ArrayList<UserEntity> getByRole(int roleId) {
        return null;
    }

    @Override
    public UserEntity getByLogin(String login) {
        sql = "SELECT LOGIN, PASSWORD, ROLE FROM USERS WHERE LOGIN = ?";
        UserEntity userEntity = jdbcTemplateMy2.selectWithMethod(sql, new String[]{login}, rsHandlerGetUserEntity);
        return userEntity;
    }

    @Override
    public UserEntity getByLoginPassword(String login, String password) {
        sql = "SELECT LOGIN, PASSWORD, ROLE FROM USERS WHERE LOGIN = ? AND PASSWORD = ?";
        UserEntity userEntity = jdbcTemplateMy2.selectWithMethod(sql, new String[]{login, password}, rsHandlerGetUserEntity);
        return userEntity;
    }

    @Override
    public void deleteByLogin(String login) {
        sql = "DELETE FROM USERS WHERE LOGIN = ?";
        jdbcTemplateMy2.queryWithoutResultset(sql, new String[]{login});
    }

    @Override
    public UserEntity create(String login, String password, String role) {
        sql = "INSERT INTO USERS (LOGIN, PASSWORD, ROLE) VALUES(?, ?, ?)";
        jdbcTemplateMy2.queryWithoutResultset(sql, new String[]{login, password, role});
        return getByLogin(login);
    }

    @Override
    public UserEntity update(String login, String password, String role) {
        sql = "UPDATE USERS SET PASSWORD = ?, ROLE = ? WHERE LOGIN = ?";
        jdbcTemplateMy2.queryWithoutResultset(sql, new String[]{password, role, login});
        return getByLogin(login);
    }
}