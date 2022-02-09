package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storage;

    public RegistrationServiceImpl() {
        storage = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        validateUser(user);
        validateUserId(user);
        if (storage.get(user.getLogin()) == null
                && user.getAge() >= MIN_AGE
                && user.getPassword().length() >= MIN_PASSWORD_LENGTH
        ) {
            storage.add(user);
            return storage.get(user.getLogin());
        } else {
            throw new RuntimeException("Entered data does not correspond requirements to register");
        }
    }

    private void validateUser(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null
        ) {
            throw new RuntimeException("Some user data or user may be null");
        }
    }

    private void validateUserId(User user) {
        if (user.getId() != null) {
            throw new RuntimeException("Cannot register new user with an assigned id");
        }
    }
}
