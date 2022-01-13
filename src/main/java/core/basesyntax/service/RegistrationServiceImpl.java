package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LEN = 6;
    private static final int MIN_USER_AGE = 18;

    @Override
    public User register(User user) {
        //user.login validation
        if (user.getLogin() == null) {
            throw new RuntimeException("login is null");
        }
        for (User storageUser : Storage.people) {
            if (user.getLogin().equals(storageUser.getLogin())) {
                throw new RuntimeException("user with this login already exists");
            }
        }
        //user.password validation
        if (user.getPassword() == null) {
            throw new RuntimeException("password is null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LEN) {
            throw new RuntimeException("password length less then " + MIN_PASSWORD_LEN);
        }
        //user.age validation
        if (user.getAge() == null) {
            throw new RuntimeException("age is null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("age less then " + MIN_USER_AGE);
        }
        Storage.people.add(user);
        return user;
    }
}
