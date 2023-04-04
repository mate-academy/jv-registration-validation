package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_ID = 1;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("Invalid user data");
        }
        if (user.getId() == null) {
            throw new RegistrationException("User id can not be empty");
        }
        if (user.getId() < MIN_USER_ID) {
            throw new RegistrationException("User id should be bigger or equal " + MIN_USER_ID);
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Field password can not be empty");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Field age can not be empty");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Field login can not be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("There is user with such login already");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("User age must be " + MIN_USER_AGE + " or older");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password should have "
                    + MIN_PASSWORD_LENGTH + "or more characters");
        }
        return storageDao.add(user);
    }
}
