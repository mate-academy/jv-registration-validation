package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserInvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new UserInvalidDataException("User`s age is less than 18");
        }
        if (user.getLogin() == null) {
            throw new UserInvalidDataException("User`s login is empty");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new UserInvalidDataException("User`s login is less than 6 symbols");
        }
        if (user.getPassword() == null) {
            throw new UserInvalidDataException("User`s password is empty");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserInvalidDataException("User`s password is less than 6 symbols");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new UserInvalidDataException("User already exists");
        }

        return storageDao.add(user);
    }
}
