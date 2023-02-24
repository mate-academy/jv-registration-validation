package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidRegistrationDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_18 = 18;
    private static final int MIN_PASSWORD_LEN = 6;
    private static final int AGE_140 = 140;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidRegistrationDataException("Register argument is null");
        }
        if (user.getLogin() == null || user.getLogin().equals("")) {
            throw new InvalidRegistrationDataException("Invalid login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidRegistrationDataException("User with this login"
                    + "is already registered");
        }
        if (user.getAge() == null || user.getAge() < AGE_18 || user.getAge() > AGE_140) {
            throw new InvalidRegistrationDataException("Age must be over 18");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LEN) {
            throw new InvalidRegistrationDataException("Password is too short");
        }
        storageDao.add(user);
        return user;
    }
}
