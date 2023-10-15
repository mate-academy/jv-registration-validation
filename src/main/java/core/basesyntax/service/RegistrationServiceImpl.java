package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_ID_VALUE = 1;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User is empty");
        }
        if (user.getId() == null) {
            throw new RegistrationException("User Id is empty");
        }
        if (user.getId() < MIN_ID_VALUE) {
            throw new RegistrationException("User Id should started from 1");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User login is empty");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User password is empty");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age is empty");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password should contain more than 6 symbols");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already exists");
        }
        return storageDao.add(user);
    }
}
