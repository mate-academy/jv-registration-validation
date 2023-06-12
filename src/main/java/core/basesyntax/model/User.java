package core.basesyntax.model;

import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private Integer age;

    //We use this constructor to be capable to initialize
    //a new User with login, password and age
    //, so we are sure the User has appropriate data and
    // is ready to be added to the Storage of Users
    public User(String login, String password, Integer age) {
        this.login = login;
        this.password = password;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(login, user.login)
                && Objects.equals(password, user.password)
                && Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, age);
    }
}
