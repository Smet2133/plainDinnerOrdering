package calc.dao;

import calc.entities.UserEntity;
import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GenericDaoTest {
    @Test
    public void getByParameters() throws Exception {
        GenericDao<UserEntity> genericDaoUser = new GenericDao<>(UserEntity.class);
        Field field = UserEntity.class.getDeclaredField("role");
        ArrayList<UserEntity> userEntityArrayList = genericDaoUser.getByParameters(new Field[]{field},
                new String[]{"user"});

        System.out.println("In getByParameters");
        System.out.println(userEntityArrayList);
    }

    @org.junit.Test
    public void getById() throws Exception {
        GenericDao<UserEntity> genericDaoUser = new GenericDao<>(UserEntity.class);
        UserEntity userEntity = genericDaoUser.getById("user@mail.ru");

        System.out.println("In getById");
        System.out.println(userEntity);

        assertEquals("Login not equal",  "user@mail.ru", userEntity.getLogin());
        assertEquals("Pass not equal",  "pass", userEntity.getPassword());
        assertEquals("Role not equal",  "user", userEntity.getRole());
    }

}