package calc.entities;

import calc.annotations.Column;
import calc.annotations.Entity;
import calc.annotations.Id;
import calc.annotations.Table;


@Entity
@Table(name = "USERS")
public class UserEntity {
    @Id
    @Column(name = "LOGIN")
    public String login;
    @Column(name = "PASSWORD")
    public String password;
    @Column(name = "ROLE")
    public String role;


    public UserEntity() {
    }




    public UserEntity(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}