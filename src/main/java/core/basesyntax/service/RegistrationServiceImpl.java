package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;
    private static final int PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User is existed");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login shouldn't be null");
        }
        if (user.getLogin() == "") {
            throw new RuntimeException("Login shouldn't be empty");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age shouldn't be empty");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password shouldn't be empty");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age shouldn't be less than " + MIN_AGE);
        }
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("Age shouldn't be more than " + MAX_AGE);
        }
        if (user.getPassword().length() < PASSWORD_LENGTH) {
            throw new RuntimeException("Password shouldn't be less than " + PASSWORD_LENGTH);
        }
        return storageDao.add(user);
    }
}
