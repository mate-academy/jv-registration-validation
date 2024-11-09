package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;

    public static final int MIN_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User is null please fix that!");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age is null!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User password is null!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User login is null!");
        }
        if (user.getLogin().isEmpty() || user.getPassword().isEmpty()) {
            throw new RegistrationException("User login or password is empty!"
                    + user.getLogin());
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("User login must be at least " + MIN_LENGTH
                    + " characters long! - "
                    + user.getLogin());
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("The user must not be less than "
                    + MIN_AGE + " years old! - "
                    + user.getAge());
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("User password is need to be at least "
                    + MIN_LENGTH + " letters!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login is already register! - "
                    + user.getLogin());
        }
        return storageDao.add(user);
    }
}
