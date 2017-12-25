package dinnOrder.entities;

import dinnOrder.annotations.Column;
import dinnOrder.annotations.Entity;
import dinnOrder.annotations.Id;
import dinnOrder.annotations.Table;


@Entity
@Table(name = "SETS")
public class SetEntity {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public SetEntity() {
    }


}