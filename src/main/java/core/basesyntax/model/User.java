package core.basesyntax.model;

import java.util.Objects;

public class User {
    final String login;
    private String password;
    private Integer age;


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAge() {
        return age;
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
public User(String login , String password , Integer age){
        this.login = login;
        this.password = password;
        this.age = age;
}
public User(String login){
        this.login = login;
}
    @Override
    public int hashCode() {
        return Objects.hash(login, password, age);
    }
}
