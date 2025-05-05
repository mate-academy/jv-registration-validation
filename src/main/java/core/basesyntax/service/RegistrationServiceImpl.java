package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE_LIMIT = 18;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegisterException {
        if (user.getLogin() == null) {
            throw new RegisterException("Login can't be null");
        }
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new RegisterException("This user is already in the database");
        }
        if (user.getLogin().isEmpty()) {
            throw new RegisterException("Login can't be empty");
        }
        if (user.getPassword() == null) {
            throw new RegisterException("Password can't be null");
        }
        if (user.getPassword().isEmpty()) {
            throw new RegisterException("Password can't be empty");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegisterException("The length of the password must be at least 6 characters");
        }
        if (user.getAge() == null) {
            throw new RegisterException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE_LIMIT) {
            throw new RegisterException("The user is under 18 years old");
        }
        storageDao.add(user);
        return user;
    }
}
