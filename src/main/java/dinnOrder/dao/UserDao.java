package dinnOrder.dao;

import dinnOrder.entities.UserEntity;

import java.util.ArrayList;

public interface UserDao {
    public ArrayList<UserEntity> getByRole(int roleId);
    public UserEntity getByLogin(String login);
    public UserEntity getByLoginPassword(String login, String password);
    public void deleteByLogin(String login);
    public UserEntity create(String login, String password, String role);
    public UserEntity update(String login, String password, String role);


}
