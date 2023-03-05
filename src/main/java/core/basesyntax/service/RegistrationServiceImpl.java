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
        if (user == null) {
            throw new RuntimeException("User shouldn't be null!");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login shouldn't be null!");
        }
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("Login shouldn't be empty!");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age shouldn't be null!");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password shouldn't be null!");
        }
        if (user.getPassword().length() < PASSWORD_LENGTH) {
            throw new RuntimeException("Password shouldn't be less than "
                    + PASSWORD_LENGTH + " characters!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age shouldn't be less than " + MIN_AGE + " years!");
        }
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("Age shouldn't be more than " + MAX_AGE + " years!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User: " + user.getLogin() + " already exists!");
        }
        return storageDao.add(user);
    }
}
