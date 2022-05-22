package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final int UNBELIEVABLE_AGE = 110;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getLogin() == "") {
            throw new RuntimeException("user is null or login is null/empty");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE || user.getAge() > UNBELIEVABLE_AGE) {
            throw new RuntimeException("or age null or user is too young or age is unbelievable");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("user's password is too short");
        }
        if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        } else {
            throw new RuntimeException("Such a user already exists with this login");
        }
    }
}
