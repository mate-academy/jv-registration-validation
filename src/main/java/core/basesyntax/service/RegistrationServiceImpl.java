package core.basesyntax.service;

import core.basesyntax.cases.of.exceptions.ExceptionDuringRegistration;
import core.basesyntax.cases.of.exceptions.NullExceptionDuringRegistration;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_YEAR = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullExceptionDuringRegistration("The user can't be Null");
        }
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new ExceptionDuringRegistration("User exists");
        }

        if (user.getAge() < MIN_YEAR) {
            throw new ExceptionDuringRegistration("Age - over 18 years old");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LENGTH
                || user.getPassword() == null || user.getPassword().length() < MIN_LENGTH) {
            throw new ExceptionDuringRegistration("Login and password must contain "
                    + "at least 6 characters");
        }
        return storageDao.add(user);
    }
}
