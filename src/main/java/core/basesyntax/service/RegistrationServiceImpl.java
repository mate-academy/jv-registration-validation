package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNull(user);
        User registeredUser = storageDao.get(user.getLogin());
        if (registeredUser == null && user.getAge() >= 18 && user.getPassword().length() >= 6) {
            storageDao.add(user);
            return user;
        }
        throw new RuntimeException("Can't registered User");
    }

    private void checkForNull(User user) {
        if (user.getLogin() == null || user.getAge() == null || user.getPassword() == null) {
            throw new RuntimeException("Can't registered User");
        }
    }
}
