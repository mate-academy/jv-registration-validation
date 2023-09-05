package core.basesyntax.service;

import core.basesyntax.customexception.CustomException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_LOGIN = 6;
    private static final int VALID_PASSWORD = 6;
    private static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLoginValidation(user);
        checkPasswordValidation(user);
        checkAgeValidation(user);
        return storageDao.add(user);
    }

    private static void checkLoginValidation(User user) {
        if (user.getLogin() == null || user.getLogin().length() < VALID_LOGIN) {
            throw new CustomException("Error! Login must have at least length " + VALID_LOGIN);
        }
    }

    private void checkPasswordValidation(User user) {
        if (user.getPassword() == null || user.getPassword().length() < VALID_PASSWORD) {
            throw new CustomException("Error! Password must have at least length "
                    + VALID_PASSWORD);
        }

    }

    private static void checkAgeValidation(User user) {
        if (user.getAge() == null || user.getAge() < VALID_AGE) {
            throw new CustomException("Error! You must have at least age " + VALID_AGE);
        }
    }
}
