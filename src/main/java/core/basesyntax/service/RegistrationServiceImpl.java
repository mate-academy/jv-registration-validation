package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidRegisterDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidRegisterDataException("User cannot be null");
        }
        if (user.getLogin() == null || user.getAge() == null || user.getPassword() == null) {
            throw new InvalidRegisterDataException("Not all data is entered");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidRegisterDataException("User with your login already exist");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidRegisterDataException("Your login must have 6 or more characters.");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidRegisterDataException("Your password must have 6 or more characters.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidRegisterDataException("Age cannot be less than " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
