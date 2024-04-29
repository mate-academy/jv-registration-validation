package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_PASS_LENGTH = 6;
    private static final int ACCEPTABLE_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();
    //
    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User already exists");
        }
        if (user.getLogin() == null || user.getLogin().length() < LOGIN_PASS_LENGTH) {
            throw new InvalidDataException("Login must be at least "
                    + LOGIN_PASS_LENGTH + " characters length");
        }
        if (user.getPassword() == null || user.getPassword().length() < LOGIN_PASS_LENGTH) {
            throw new InvalidDataException("Password must be at least "
                    + LOGIN_PASS_LENGTH + " characters length");
        }
        if (user.getAge() < ACCEPTABLE_AGE) {
            throw new InvalidDataException("Age must be at least " + ACCEPTABLE_AGE);
        }

        return storageDao.add(user);
    }
}
