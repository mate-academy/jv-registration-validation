package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException(
                    "Invalid login or such login already contains in storage");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Invalid age");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Invalid password");
        }
        return user;
    }
}
