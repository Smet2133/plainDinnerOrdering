package calc.entities;

import java.util.Date;

public class OrderEntity {
    private int id;
    private String name;
    private Date date;
    private String set;
    private String soup;
    private String salad;
    private String hot;
    private String hosper;
    private String fixings;
    private String bread;
    private String drink;
    private int sum;


    public void setSoup(String s) {
        if(s == null) soup = "";
        else if (s.equals("+")) soup = "+";
        else soup = "";
    }


    // "u.name, s.name as 'set_name', o.soup, o.salad, o.hot, o.hosper, o.fixings, o.bread, o.drink, o.sum ";


}
