package core.basesyntax.model;

import core.basesyntax.exeption.InvalidEmailException;

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

    public void setLogin(String login) throws InvalidEmailException {
        if (login == null) {
            this.login = login;
            return;
        }
        int length = login.length();
        if (!login.contains("@")) {
            throw new InvalidEmailException("E-mail without '@' is uncorrected!");
        }
        if (!login.substring(login.indexOf('@') + 1, length).equals("gmail.com")) {
            throw new InvalidEmailException("Invalid e-mail mail!");
        }
        if (!Character.isLetter(login.charAt(0))) {
            throw new InvalidEmailException("Your e-mail should start from the letter! Now it's : " + login.charAt(0));
        }
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
