package core.basesyntax.model;

import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private Integer age;

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
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        User user = (User) object;
        return Objects.equals(login, user.login)
                && Objects.equals(password, user.password)
                && Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, age);
    }
}
