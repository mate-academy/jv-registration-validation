package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LEGAL_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException("The information are not valid, can't register user.");
        }
        if (user.getAge() < LEGAL_AGE) {
            throw new RuntimeException("You need to be more than 18 years of age.");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password need to be at least 6 symbols long.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with that login already exists.");
        }
        return storageDao.add(user);
    }
}
