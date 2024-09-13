package core.basesyntax;

import core.basesyntax.model.User;

public class UserSupplier {
    public User of(String login, String password, Integer age) {
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setAge(age);
        return newUser;
    }
}
