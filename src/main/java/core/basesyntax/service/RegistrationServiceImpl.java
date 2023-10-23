package core.basesyntax.service;

import core.basesyntax.cases.of.exceptions.ExceptionDuringRegistration;
import core.basesyntax.cases.of.exceptions.NullExceptionDuringRegistration;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_YEAR = 18;
    private static final int MIN_LENGTH = 6;
    private static final String USER_NULL = "The user can't be Null";
    private static final String USER_EXISTS = "User exists";
    private static final String AGE_UNDER_18 = "Age - over 18 years old";
    private static final String INCORRECT_LOGIN_OR_PASSWORD = "Login and password must contain "
            + "at least 6 characters";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullExceptionDuringRegistration(USER_NULL);
        }
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new ExceptionDuringRegistration(USER_EXISTS);
        }

        if (user.getAge() < MIN_YEAR) {
            throw new ExceptionDuringRegistration(AGE_UNDER_18);
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LENGTH
                || user.getPassword() == null || user.getPassword().length() < MIN_LENGTH) {
            throw new ExceptionDuringRegistration(INCORRECT_LOGIN_OR_PASSWORD);
        }
        return storageDao.add(user);
    }
}
