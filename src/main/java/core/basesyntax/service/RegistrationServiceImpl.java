package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User should not be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login should not be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login should be at least 6 characters long");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password should not be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password should be at least 6 characters long");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age should not be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User should be at least 18 years old");
        }

        for (User existing : Storage.people) {
            if (existing.getLogin().equals(user.getLogin())) {
                throw new RegistrationException("Login is already in use");
            }
        }

        Storage.people.add(user);
        return user;
    }
}
