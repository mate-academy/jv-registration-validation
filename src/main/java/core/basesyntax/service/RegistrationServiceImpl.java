package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_FOR_LOGIN_AND_PASSWORD = 6;
    private static final int MIN_AGE = 18;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Current user is null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can not be null!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can not be null!");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can not be null!");
        }
        if (user.getLogin().length() < MIN_LENGTH_FOR_LOGIN_AND_PASSWORD) {
            throw new RegistrationException("Not valid login: " + user.getLogin()
                        + ". Min allowed length is " + MIN_LENGTH_FOR_LOGIN_AND_PASSWORD);
        }
        if (user.getPassword().length() < MIN_LENGTH_FOR_LOGIN_AND_PASSWORD) {
            throw new RegistrationException("Not valid password: " + user.getPassword()
                        + ". Min allowed length is " + MIN_LENGTH_FOR_LOGIN_AND_PASSWORD);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                        + ". Min allowed age is " + MIN_AGE);
        }
        for (User iterUser : Storage.people) {
            if (iterUser.getLogin().equals(user.getLogin())) {
                throw new RegistrationException("User with login "
                                + user.getLogin() + " already exist!");
            }
        }
        Storage.people.add(user);
        return user;
    }
}

