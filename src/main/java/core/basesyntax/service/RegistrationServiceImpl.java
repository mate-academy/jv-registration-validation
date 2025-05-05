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
        if (user == null || user.getAge() == null
                || user.getPassword() == null || user.getLogin() == null) {
            throw new RuntimeException("Data entered incorrectly");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Your age must be not less than "
                    + MIN_AGE + " years");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password must be at least "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login is already taken");
        }
        return storageDao.add(user);
    }
}
