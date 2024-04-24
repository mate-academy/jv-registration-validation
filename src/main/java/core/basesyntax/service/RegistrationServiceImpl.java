package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Error, user is null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Error, user`s login is null`");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Error, user`s password is null`");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Error, user`s age is null`");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Error, user exists with such login "
                    + user.getLogin());
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Error, 18 is min age, your age is "
                    + user.getAge());
        }
        if (user.getLogin().length() < MIN_CHARACTERS) {
            throw new RegistrationException("Error, 6 is min length, login length is "
                    + user.getLogin().length());
        }
        if (user.getPassword().length() < MIN_CHARACTERS) {
            throw new RegistrationException("Error, 6 is min length, password length is "
                    + user.getPassword().length());
        }
        return storageDao.add(user);
    }
}
