package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User must not be null");
        }
        if (user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException("Login, Password or Age must not be null!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age might be 18+ years");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("Password length might be 6+ chars");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RuntimeException("Login might be 6+ chars");
        }
        if (user.getLogin().equals(user.getPassword())) {
            throw new RuntimeException("Login must not be equal Password!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user already exists!");
        }
        return storageDao.add(user);
    }
}
