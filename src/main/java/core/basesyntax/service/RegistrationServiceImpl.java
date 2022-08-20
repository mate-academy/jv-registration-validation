package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int USER_MINIMUM_AGE = 18;
    public static final int PASSWORD_MINIMUM_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null || user.getAge() < 0 || user.getLogin().isEmpty()) {
            throw new RegistrationServiceException("User fields can't be null/empty/negative");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("User with login "
                    + user.getLogin() + " exists");
        }
        if (user.getAge() < USER_MINIMUM_AGE || user.getAge() > 110) {
            throw new RegistrationServiceException("User age must be 18-110 years old, "
                    + "but was " + user.getAge());
        }
        if (user.getPassword().length() < PASSWORD_MINIMUM_LENGTH) {
            throw new RegistrationServiceException("Password length should be more than 6, but was "
                    + user.getPassword());
        }
        return storageDao.add(user);
    }
}
