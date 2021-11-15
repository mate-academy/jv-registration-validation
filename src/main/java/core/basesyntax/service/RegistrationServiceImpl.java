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
            throw new RuntimeException("User might not be null");
        } else if (user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException("Login, Password or Age might not be null!");
        } else if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age might be 18+ years");
        } else if (user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("Password length might be 6+ chars");
        } else if (user.getLogin().length() < MIN_LENGTH) {
            throw new RuntimeException("Login might be 6+ chars");
        } else if (user.getLogin().equals(user.getPassword())) {
            throw new RuntimeException("Login might not be equal Password!");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user already exists!");
        } else {
            return storageDao.add(user);
        }
    }
}
