package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RuntimeException();
        }
        if (user.getPassword() == null || user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RuntimeException();
        }
        if (user.getAge() == null || user.getAge() < MINIMAL_AGE) {
            throw new RuntimeException();
        }
        for (User users : Storage.people) {
            if (users.getLogin().equals(user.getLogin())) {
                throw new RuntimeException();
            }
        }
        Storage.people.add(user);
        return user;
    }
}
