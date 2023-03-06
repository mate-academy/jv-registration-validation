package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationIllegalArgumentException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ACCEPTABLE_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationIllegalArgumentException("User object is null");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationIllegalArgumentException("Login password is empty");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationIllegalArgumentException(
                    "Inputted password is shorter than is permitted. Need at least "
                            + MIN_PASSWORD_LENGTH + " symbols");
        }
        if (user.getAge() == null || user.getAge() < ACCEPTABLE_AGE) {
            throw new RegistrationIllegalArgumentException(
                    "Registration permitted only for User with age "
                            + ACCEPTABLE_AGE + " and more ");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationIllegalArgumentException(
                    "User with this login already registered");
        }
        storageDao.add(user);
        return user;
    }
}
