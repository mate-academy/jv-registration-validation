package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user already exists!");
        } else if (user.getLogin() == null || user.getPassword() == null) {
            throw new RuntimeException("Login or Password might not be null!");
        } else if (user.getLogin().equals(user.getPassword())) {
            throw new RuntimeException("Login might not be equal Password!");
        } else if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age might be 18+");
        } else if (user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("Password length might be 6+");
        } else if (user.getLogin().length() < MIN_LENGTH) {
            throw new RuntimeException("Login might be 6+");
        } else {
            return storageDao.add(user);
        }
    }
}
