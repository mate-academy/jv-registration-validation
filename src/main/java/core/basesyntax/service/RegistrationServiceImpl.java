package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_SIZE = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null
                || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("Necessary user`s data do not exist!");
        }
        if (user.getAge() < MIN_AGE || (user.getPassword().length() < MIN_PASSWORD_SIZE)) {
            throw new ValidationException("User`s data does not meet the requirements!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("User with such login already exists.");
        }
        return storageDao.add(user);
    }
}
