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
        if (user == null) {
            throw new RuntimeException("This user doesn't exist");
        }
        if (user.getAge() == null || user.getLogin() == null
                || user.getPassword() == null) {
            throw new RuntimeException("You must not leave any fields empty");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Your age must be greater than " + MIN_AGE);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password must have more than "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        return storageDao.add(user);
    }
}
