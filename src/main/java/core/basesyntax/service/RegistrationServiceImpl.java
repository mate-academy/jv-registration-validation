package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

import java.util.List;

public class RegistrationServiceImpl implements RegistrationService {
    private final static int MIN_LENGTH = 6;
    private static final Integer MIN_AGE = 18;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null!!!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User's login can't be null!!!");
        }
        if (isSameLogin(Storage.people, user.getLogin())) {
            throw new RegistrationException("User with same login registered!!!");
        }
        if (user.getLogin().isEmpty()) {
            throw new RegistrationException("User's login must contain at least 6 characters!!!");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("User's login can't be least 6 characters!!!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User's password can't be null!!!");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("User's password can't be least 6 characters!!!!");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User's age can't be null!!!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's age can't be least 18!!!");
        }
        Storage.people.add(user);
        return user;
    }

    private boolean isSameLogin(List<User> users, String login) {
        for (User user: users) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }
}
