package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final Integer MIN_PASSWORD_LENGTH = 6;
    public static final Integer ADULT_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        if (user.getAge() == null || user.getAge() <= 0) {
            throw new RuntimeException("Age not correct");
        }
        if (user.getAge() < ADULT_AGE) {
            throw new RuntimeException("Age < " + ADULT_AGE + " years");
        }
        if (user.getLogin() == null
                || user.getLogin().isEmpty()
                || !Character.isLetter(user.getLogin().charAt(0))) {
            throw new RuntimeException("Login not correct");
        }
        if (user.getPassword() == null
                || user.getPassword().isEmpty()
                || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password not correct");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already exist in storage");
        }
        storageDao.add(user);
        return user;
    }

    public StorageDao getStorageDao() {
        return storageDao;
    }
}
