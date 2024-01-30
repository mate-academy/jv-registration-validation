package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASS_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.equals(new User())) {
            throw new RegistrationException("Fill in the data fields");
        }

        if (user.getLogin() == null) {
            throw new RegistrationException(("Login missing"));
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be at least "
                    + MIN_LOGIN_LENGTH + " characters");
        }

        if (user.getLogin().matches(".*\\s.*")) {
            throw new RegistrationException("Password must contain no spacing");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password must contain no spacing");
        }

        if (user.getPassword().matches(".*\\s.*")) {
            throw new RegistrationException("Password must contain no spacing");
        }

        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_PASS_LENGTH + " characters");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("You must be at least "
                    + MIN_AGE + " years old to register");
        }

        for (User storageUser : Storage.people) {
            if (storageUser.getLogin().equals(user.getLogin())) {
                throw new RegistrationException("User with current login already exists");
            }
        }

        return storageDao.add(user);
    }
}
