package core.basesyntax.model;

public class User {
    private Long id;
    private String login;
    private String password;
    private Integer age;

    public User(String login, String password, Integer age) {
        this.login = login;
        this.password = password;
        this.age = age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAge() {
        return age;
    }
}
