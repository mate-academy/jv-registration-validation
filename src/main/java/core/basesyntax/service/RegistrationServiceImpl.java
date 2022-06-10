package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE = 18;
    private static final int VALID_PASSWORD_LENGTH = 6;
    private final StorageDao storageUser = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null || user.getLogin().isBlank()
                || user.getAge() == null
                || user.getPassword() == null) {
            throw new RuntimeException("Empty input");
        }
        if (storageUser.get(user.getLogin()) != null) {
            getThrow("Login used");
        }
        if (user.getAge() < VALID_AGE) {
            getThrow("Invalid age");
        }
        if (user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            getThrow("Password length must be over 6 charted");
        }
        storageUser.add(user);
        return user;
    }

    private void getThrow(String message) {
        throw new RuntimeException("Can`t registration. " + message);
    }
}
