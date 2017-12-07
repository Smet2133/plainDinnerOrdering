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
    private String login;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ROLE")
    private String role;


    public UserEntity() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserEntity(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }


    @Override
    public String toString() {
        return login + " " + password + " " + role;
    }
}