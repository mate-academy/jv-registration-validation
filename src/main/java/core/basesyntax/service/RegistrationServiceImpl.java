package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDao storageDao = new StorageDaoImpl();
    private final int zeroAge = 0;
    private final int requiredAge = 18;
    private final int requiredPasswordLength = 6;

    @Override
    public User register(User user) throws InvalidDataException {
        if (user == null) {
            throw new InvalidDataException("Can't register null user. Please, enter your data");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with such login is already exist");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (user.getPassword().length() < requiredPasswordLength) {
            throw new InvalidDataException("Your password should consist at least 6 characters");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null");
        }
        if (user.getAge() < zeroAge) {
            throw new InvalidDataException("Not valid age!");
        }
        if (user.getAge() < requiredAge) {
            throw new InvalidDataException("Your age is less then require");
        }
        return storageDao.add(user);
    }
}
