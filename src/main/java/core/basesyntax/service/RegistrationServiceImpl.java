package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_OF_PASSWORD_AND_LOGIN = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("The user with the same login already exists");
        }

        if (storageDao.get(user.getLogin()) == null) {
            if (user.getLogin() == null) {
                throw new InvalidDataException("The login cannot be null");
            }
            if (user.getPassword() == null) {
                throw new InvalidDataException("The password cannot be null");
            }
            if (user.getAge() == 0) {
                throw new InvalidDataException("The age cannot be zero");
            }
            if (user.getLogin().length() < MIN_LENGTH_OF_PASSWORD_AND_LOGIN) {
                throw new InvalidDataException("The length of login is less than 6 characters");
            }
            if (user.getPassword().length() < MIN_LENGTH_OF_PASSWORD_AND_LOGIN) {
                throw new InvalidDataException("The length of password is less than 6 characters");
            }
            if (user.getAge() < 18) {
                throw new InvalidDataException("The age is less than 18 years");
            } else {
                storageDao.add(user);
            }
        }
        return user;
    }
}
