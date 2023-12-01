package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer MIN_AGE = 18;
    private static final int MIN_CHARACTERS_IN_LOGIN = 6;
    private static final int MIN_CHARACTERS_IN_PASSWORD = 6;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (Storage.people.contains(user)) {
            throw new RegistrationException("User already exists");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age is null");
        }
        if (user.getLogin().length() < MIN_CHARACTERS_IN_LOGIN) {
            throw new RegistrationException("Login is too short, "
                    + "min length is " + MIN_CHARACTERS_IN_LOGIN
                    + " characters");
        }
        if (user.getPassword().length() < MIN_CHARACTERS_IN_PASSWORD) {
            throw new RegistrationException("Password is too short, "
                    + "min length is " + MIN_CHARACTERS_IN_PASSWORD
                    + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is "
                    + MIN_AGE);
        }
        Storage.people.add(user);
        return user;
    }
}
