package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("The user is null");
        }
        if (user.getId() == null) {
            throw new InvalidDataException("The user's ID is null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("The user's login is null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("The user's password is null");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("The user's age is null");
        }
        if (Storage.people.contains(user)) {
            throw new InvalidDataException("This user is already registered");
        }
        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new InvalidDataException("The login is shorter than 6 characters");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new InvalidDataException("The password is shorter than 6 characters");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new InvalidDataException("The age is less than 18");
        }
        Storage.people.add(user);
        return user;
    }
}
