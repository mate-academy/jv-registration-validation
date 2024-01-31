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
    public User register(User user) {
        checkUserData(user);
        if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        }
        throw new RegistrationException("User is already registered!");
    }

    private boolean checkUserData(User user) {
        if (user == null) {
            throw new RegistrationException("User can`t be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("The login can`t be less than "
                    + MIN_LOGIN_LENGTH + " characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("The password can`t be less than "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("The minimum age to register is  " + MIN_AGE);
        }
        return true;
    }

}
