package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_DATA_LENGTH = 6;
    private static final int MIN_VALID_AGE = 18;

    @Override
    public User register(User user) throws RegistrationException {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login field is empty");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password field is empty");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age field is empty");
        }
        if (user.getLogin().length() < MIN_DATA_LENGTH) {
            throw new RegistrationException("Login should be at least 6 characters");
        }
        if (user.getPassword().length() < MIN_DATA_LENGTH) {
            throw new RegistrationException("Password should be at least 6 characters");
        }
        if (user.getAge() < MIN_VALID_AGE) {
            throw new RegistrationException("Age should be at least 18 years old");
        }
        for (User current : Storage.people) {
            if (current.getLogin().equals(user.getLogin())) {
                throw new RegistrationException("User with such login already exist");
            }
        }
        Storage.people.add(user);
        return user;
    }
}
