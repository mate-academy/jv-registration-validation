package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new InvalidDataException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("User login cannot be null");
        }
        if (Storage.people.contains(storageDao.get(user.getLogin()))) {
            throw new InvalidDataException("User already exists "
                    + user.getLogin()
                    + " with such login");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidDataException("Login must be at least "
                    + MIN_LENGTH
                    + " current long "
                    + user.getLogin().length());
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("User password cannot be null");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidDataException("Password must be at least"
                    + MIN_LENGTH
                    + " current length"
                    + user.getPassword().length());
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("User age cannot be null");
        }
        if (user.getAge() < 0) {
            throw new InvalidDataException("Age cannot be negative");
        }
        if (user.getAge() < 18) {
            throw new InvalidDataException("User must be at least "
                    + MIN_AGE
                    + " years old.");
        }
        return storageDao.add(user);
    }
}
