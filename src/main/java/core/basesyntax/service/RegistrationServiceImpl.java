package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_DATA_LENGTH = 6;
    private static final int MIN_AGE_ALLOWED = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null) {
            throw new NullDataException("Your age can not be null");
        }
        if (user.getLogin() == null) {
            throw new NullDataException("Your login can not be null");
        }
        if (user.getPassword() == null) {
            throw new NullDataException("Your password can not be null");
        }
        if (user.getAge() < MIN_AGE_ALLOWED) {
            throw new InvalidAgeException("Your are" + user.getAge()
                    + " years old, but you need to be at least " + MIN_AGE_ALLOWED);
        }
        if (user.getPassword().length() < MIN_DATA_LENGTH) {
            throw new InvalidDataLengthException("Password length must be at least 6");
        }
        if (user.getLogin().length() < MIN_DATA_LENGTH) {
            throw new InvalidDataLengthException("Login length must be at least 6");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserExistException("This login is already taken");
        }
        return storageDao.add(user);
    }
}
