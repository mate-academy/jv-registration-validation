package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 20;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        test(user);
        return storageDao.add(user);
    }

    private void test(User user) {
        if (user == null) {
            throw new UserRegistrationException("User shouldn't be null");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new UserRegistrationException("User's age must be over " + MIN_AGE);
        }
        if (user.getAge() > MAX_AGE) {
            throw new UserRegistrationException("User's age must be less than " + MAX_AGE);
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LENGTH) {
            throw new UserRegistrationException("Login must contain at least "
                    + MIN_LENGTH + " character");
        }
        if (user.getLogin().length() > MAX_LENGTH) {
            throw new UserRegistrationException("Login must contain a maximum of "
                    + MAX_LENGTH + " character");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH) {
            throw new UserRegistrationException("Password must contain at least "
                    + MIN_LENGTH + " character");
        }
        if (user.getPassword().length() > MAX_LENGTH) {
            throw new UserRegistrationException("Password must contain a maximum of "
                    + MAX_LENGTH + " character");
        }
        if (user.getId() == null) {
            throw new UserRegistrationException("Id can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("Login already taken");
        }
    }
}
