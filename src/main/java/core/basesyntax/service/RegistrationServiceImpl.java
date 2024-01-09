package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null || user.getLogin().length() < MIN_LENGTH
                || user.getPassword().length() < MIN_LENGTH || user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException(
                    "Enter correct data. Min Age = " + MIN_AGE
                            + ". The length of the password and login is greater than "
                            + MIN_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException(
                    "User with this login already exists. Enter a different login.");
        }
        storageDao.add(user);
        return user;
    }
}
