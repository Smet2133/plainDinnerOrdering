package calc.db;

import calc.Main;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JDBCconfig {


    public static JdbcTemplate getJdbcTemplate( ) {

        String s = JdbcTemplate.class.getClassLoader().getResource("myDB.mv.db").toString();
        System.out.println(s);
        s = s.replaceAll(".mv.db", "");
        s = "jdbc:h2:" + s;

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.h2.Driver");
        //driverManagerDataSource.setUrl("jdbc:h2:./myDB");
        driverManagerDataSource.setUrl(s);
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("root");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(driverManagerDataSource);
        return jdbcTemplate;
    }
}
