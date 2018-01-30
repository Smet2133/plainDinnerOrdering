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
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "DATE")
    private String date;
    @Column(name = "SET")
    private String set;
    @Column(name = "SOUP")
    private String soup;
    @Column(name = "salad")
    private String salad;
    @Column(name = "hot")
    private String hot;
    @Column(name = "fixings")
    private String fixings;
    @Column(name = "drink")
    private String drink;
    @Column(name = "SUM")
    private int sum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public void setDate(String  date) {
        this.date = date;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getSalad() {
        return salad;
    }

    public void setSalad(String salad) {
        this.salad = salad;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getFixings() {
        return fixings;
    }

    public void setFixings(String fixings) {
        this.fixings = fixings;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setSoup(String s) {
        if(s == null) soup = "";
        else if (s.equals("+")) soup = "+";
        else soup = "";
    }

    public void setSoup(boolean b) {
        if(b) soup = "+";
        else soup ="";
    }

    public String getSoup() {
        return soup;
    }

    // "u.name, s.name as 'set_name', o.soup, o.salad, o.hot, o.hosper, o.fixings, o.bread, o.drink, o.sum ";


}
