package dinnOrder;

import dinnOrder.dao.GenericDao;
import dinnOrder.dao.H2UserDao;
import dinnOrder.entities.UserEntity;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {



        UserEntity userEntity = new UserEntity();
        GenericDao<UserEntity> genericDao = new GenericDao<>(UserEntity.class);

        int i = 1;
        while (i < 1000) {
            userEntity.setLogin("user" + i + "@mail.ru");
            userEntity.setPassword("pass" + i);
            userEntity.setRole("user");
            genericDao.createWithId(userEntity);
            i++;
        }



/*
        GenericDao<UserEntity> genericDaoUser = new GenericDao(UserEntity.class);
        UserEntity userEntity = genericDaoUser.getById("user@mail.ru");
        System.out.println(userEntity.getLogin() + userEntity.getPassword());
        System.out.println(genericDaoUser.getById("user@mail.ru"));
        Entity annotation = UserEntity.class.getDeclaredAnnotation(Entity.class);

        System.out.println(new H2UserDao().getByLogin("user@mail.ru"));
        System.out.println(new H2UserDao().getByLoginPassword("user@mail.ru", "pass"));
        System.out.println();*/
/*
        final Logger logger = Logger.getLogger(Main.class);
        logger.info("In get");*/

/*        String s = Main.class.getClassLoader().getResource("myDB.mv.db").toString();
        System.out.println(s);
        s = s.replaceAll(".mv.db", "");
        s = "jdbc:h2:" + s;
        System.out.println(s);
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.h2.Driver");
        //driverManagerDataSource.setUrl("jdbc:h2:./myDB");
        //driverManagerDataSource.setUrl(s);
        //driverManagerDataSource.setUrl("file:D:/gDisk/netcracker/calculator/target/classes/myDB");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("root");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(driverManagerDataSource);


        String sql = "SELECT COUNT(*) FROM USERS WHERE email = ? AND password = ?";
        jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"user", "pass"});
        System.out.println(jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"user@mail.ru", "pass"}));*/

/*        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.h2.Driver");
        driverManagerDataSource.setUrl("jdbc:h2:./myDB");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("root");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(driverManagerDataSource);

        String sql = "SELECT * FROM USERS";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            System.out.println((row.get("email")));
            System.out.println((row.get("password")));
        }



        try {
            driverManagerDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            Class.forName("org.h2.Driver");
            DriverManager.getConnection("jdbc:h2:./myDB", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        //String fileString = Utilities.fileToString(Utilities.stringResources("index.html"));
        //System.out.println(Main.class.getClassLoader().getResource("html/index.html").getPath());
/*
        Properties properties = new Utilities().getProperties();
        System.out.println(properties.getProperty("examp"));*/

       /* Properties defaultProps = new Properties();
        FileInputStream in = new FileInputStream("prop.properties");
        defaultProps.load(in);
        in.close();

        System.out.println("hi");
        System.out.println(System.getProperty("user.dir"));*/




      /*  System.out.println("hi");
        File f ;
        f = new File(".");
        System.out.println(f.getAbsolutePath());
        f = new File("/.");
        System.out.println(f.getAbsolutePath());
        f = new File("C:\\.");
        System.out.println(f.getAbsolutePath());*/


/*        String  fileString;

        fileString = Utilities.fileToString("index.html");
        fileString = fileString.replaceAll("\\$\\{greetings}", "Hi, bro");
        fileString = fileString.replaceAll("\"", "\\\"");
        PrintWriter localOut = new PrintWriter(new FileWriter("localOut.txt"));
        localOut.println("hi");
        localOut.println(fileString);
        localOut.close();*/
//        System.out.println(fileString);
    }
}
