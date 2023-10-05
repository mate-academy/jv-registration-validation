package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with such login already exists in the Storage");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("User login must be at least 6 characters long");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User password must be at least 6 characters long");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid user age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }

        return storageDao.add(user);
    }
}
