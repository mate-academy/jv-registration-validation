package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDaoImpl people = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNullUser(user);
        checkValidPassword(user);
        checkValidAge(user);
        checkExistLogin(user);
        return people.add(user);
    }

    private void checkNullUser(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
    }

    private void checkValidPassword(User user) {
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password length can't be less than "
                    + MIN_PASSWORD_LENGTH);
        }
    }

    private void checkValidAge(User user) {
        if (user.getAge() < DEFAULT_AGE) {
            throw new RegistrationException("Age can't be less than " + DEFAULT_AGE);
        }
    }

    private void checkExistLogin(User user) {
        if (people.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
    }
}
