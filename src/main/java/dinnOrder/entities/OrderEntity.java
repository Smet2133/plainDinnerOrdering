package dinnOrder.entities;

import dinnOrder.annotations.Column;
import dinnOrder.annotations.Entity;
import dinnOrder.annotations.Id;
import dinnOrder.annotations.Table;

import java.util.Date;



@Entity
@Table(name = "ORDERS")
public class OrderEntity {
    @Id
    @Column(name = "id")
    private int id;
    private String name;
    private Date date;
    private String set;
    private String soup;
    private String salad;
    private String hot;
    private String fixings;
    private String drink;
    private int sum;


    public void setSoup(String s) {
        if(s == null) soup = "";
        else if (s.equals("+")) soup = "+";
        else soup = "";
    }


    // "u.name, s.name as 'set_name', o.soup, o.salad, o.hot, o.hosper, o.fixings, o.bread, o.drink, o.sum ";


}
