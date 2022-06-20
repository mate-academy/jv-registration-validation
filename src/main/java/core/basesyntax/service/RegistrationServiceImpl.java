package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_LOGIN_LENGTH = 20;
    private static final int ALLOWED_AGE = 18;
    private final StorageDaoImpl storage = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getAge() == null
                || user.getPassword() == null) {
            throw new RuntimeException("You need to fill in all obligatory fields.");
        }
        if (storage.get(user.getLogin()) != null) {
            throw new RuntimeException("There is such user with the same login already.");
        }
        if (storage.checkForAvailablePassword(user)) {
            throw new RuntimeException("Such a password is already existed.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password length should be more than 6 characters.");
        }
        if (user.getLogin().length() > MAX_LOGIN_LENGTH) {
            throw new RuntimeException("Your login length should be less than 20 characters.");
        }
        if (user.getAge() < ALLOWED_AGE) {
            throw new RuntimeException("Registration is allowed only for users older than 18.");
        } else if (user.getAge() < 0 || user.getAge() > 100) {
            throw new RuntimeException("Invalid user's age. ");
        }
        return storage.add(user);
    }
}
