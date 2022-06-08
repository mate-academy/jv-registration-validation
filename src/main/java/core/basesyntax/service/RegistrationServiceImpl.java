package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MINIMUM_LENGTH = 6;
    private static final int MINIMUM_VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getPassword() == null
                || user.getAge() == null
                || user.getLogin() == null) {
            throw new RuntimeException("Your input is null");
        } else if (user.getPassword().length() < PASSWORD_MINIMUM_LENGTH) {
            throw new RuntimeException("Your password is less than 6 symbols");
        } else if (user.getAge() < MINIMUM_VALID_AGE) {
            throw new RuntimeException("Your age is lower than 18 years.");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException(("Your login is already used"));
        } else {
            storageDao.add(user);
        }
        return user;
    }
}
