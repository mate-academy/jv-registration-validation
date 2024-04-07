package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int USER_MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("Can't pass Null");
        }

        if (user.getId() == null) {
            throw new ValidationException("ID is required");
        }

        if (user.getLogin() == null) {
            throw new ValidationException("Login is required");
        }

        if (user.getPassword() == null) {
            throw new ValidationException("Password is required");
        }

        if (user.getAge() == null) {
            throw new ValidationException("Age is required");
        }

        if (Storage.people.contains(user)) {
            throw new ValidationException("This user already exists");
        }

        if (user.getLogin().length() < LOGIN_MIN_LENGTH) {
            throw new ValidationException("Login must be at least " + LOGIN_MIN_LENGTH
                    + " characters long");
        }

        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new ValidationException("Password must be at least " + PASSWORD_MIN_LENGTH
                    + " characters long");
        }

        if (user.getAge() < USER_MIN_AGE) {
            throw new ValidationException("User must be at least " + USER_MIN_AGE + " years old");
        }

        storageDao.add(user);
        return user;
    }
}
