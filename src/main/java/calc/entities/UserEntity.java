package calc.entities;

public class UserEntity {
    private String login;
    private String password;
    private String role;

    public UserEntity() {
    }

    public UserEntity(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}