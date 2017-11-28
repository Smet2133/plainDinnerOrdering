package calc.db;

public class JDBCconfig {


    public static JdbcTemplateMy1 getJdbcTemplate( ) {

        String s = JDBCconfig.class.getClassLoader().getResource("myDB.mv.db").toString();
        System.out.println(s);
        s = s.replaceAll(".mv.db", "");
        s = s.replaceAll("%20", " ");
        s = "jdbc:h2:" + s;
        System.out.println(s);

        //s = "jdbc:h2:file:C:/Users/Lenovo/Google%20Drive/netcracker/calculator/out/artifacts/calculator_Web_exploded/WEB-INF/classes/myDB";
        //s = "jdbc:h2:file:C:\\Users\\Lenovo\\Google Drive\\netcracker\\calculator\\src\\main\\resources\\myDB";
        //s = "jdbc:h2:file:C:\\Users\\Lenovo\\Google Drive\\netcracker\\calculator\\out\\artifacts\\calculator_Web_exploded\\WEB-INF\\classes\\myDB";


/*        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.h2.Driver");
        //driverManagerDataSource.setUrl("jdbc:h2:./myDB");
        driverManagerDataSource.setUrl(s);
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("root");*/

        //JdbcTemplate jdbcTemplate = new JdbcTemplate(driverManagerDataSource);
        JdbcTemplateMy1 jdbcTemplate = new JdbcTemplateMy1("org.h2.Driver", s, "root", "root");
        return jdbcTemplate;
    }
}
