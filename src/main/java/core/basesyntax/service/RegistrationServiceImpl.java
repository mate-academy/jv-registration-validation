package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() >= 18
            && user.getLogin().length() >= MIN_LOGIN_PASSWORD_LENGTH
            && user.getPassword().length() >= MIN_LOGIN_PASSWORD_LENGTH
            && storageDao.get(user.getLogin()) != null) {
            storageDao.add(user);
        }
        return user;
    }
}
