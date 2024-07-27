package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null || user.getAge() < MINIMUM_AGE) {
            throw new RuntimeException("Wrong age");
        }
        if (user.getLogin() == null || user.getLogin().length() < MINIMUM_LENGTH) {
            throw new RuntimeException("Wrong login");
        }
        if (user.getPassword() == null || user.getPassword().length() < MINIMUM_LENGTH) {
            throw new RuntimeException("Wrong password");
        }
        if (isStorageContainLogin(user.getLogin())) {
            throw new RuntimeException("Such user exist in DB");
        }
        return storageDao.add(user);
    }

    private boolean isStorageContainLogin(String login) {
        return storageDao.get(login) != null;
    }
}
