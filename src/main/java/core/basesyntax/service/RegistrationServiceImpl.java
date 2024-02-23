package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LENGTH_OF_LOGIN_AND_PASSWORD = 6;
    private static final int MINIMUM_USER_AGE_TO_LOGIN = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User should be not null");
        }
        if (storageDao.get(user.getLogin()) == null
                && user.getLogin().length() >= MINIMUM_LENGTH_OF_LOGIN_AND_PASSWORD
                && user.getPassword().length() >= MINIMUM_LENGTH_OF_LOGIN_AND_PASSWORD
                && user.getAge() >= MINIMUM_USER_AGE_TO_LOGIN) {
            storageDao.add(user);
        } else {
            throw new InvalidDataException("Can't add the user");
        }

        return user;
    }
}
