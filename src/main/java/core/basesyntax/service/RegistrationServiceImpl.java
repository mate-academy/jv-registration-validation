package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("The login can't be a NULL!");
        }
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("Can't register a user with empty login.");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("The password can't be a NULL!");
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RuntimeException("The password must be longer that 5 symbols.");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("The age can't be a NULL!");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RuntimeException("Tha age of user must be at least 18.");
        }
        for (User users : Storage.people) {
            if (users.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("You can't register twice))).");
            }
        }
        Storage.people.add(user);
        return user;
    }
}
