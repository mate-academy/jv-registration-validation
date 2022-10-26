package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        final int minValueOfPassword = 6;
        final int minAge = 18;

        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("No valid age");
        }
        if (user.getPassword() == null || user.getPassword().length() < minValueOfPassword) {
            throw new RuntimeException("No valid password");
        }
        if (user.getAge() < minAge) {
            throw new RuntimeException("Age must be greater than 18");
        }
        if (storageDao.get(user.getLogin()).getLogin() == null) {
            storageDao.add(user);
        }
        return user;
    }
}
