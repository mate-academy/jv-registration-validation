package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getPassword() == null
                || user.getLogin() == null
                || user.getAge() == null) {
            throw new InvalidUserDataException("Field can't be null");
        } else if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException(
                    "Age should be equal or greater than " + MIN_AGE
                            + " but age is " + user.getAge());
        } else if (user.getPassword().length() < MIN_LENGTH
                || user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidUserDataException(
                    "Password/Login length should be greater than " + MIN_LENGTH);
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException(
                    "Login already exist " + user.getLogin());
        }
        storageDao.add(user);
        return user;
    }
}
