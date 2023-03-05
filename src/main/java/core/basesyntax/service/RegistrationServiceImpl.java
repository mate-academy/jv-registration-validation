package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationFailedException {
        if (user.getAge() == null
                || user.getAge() < MIN_AGE) {
            throw new RegistrationFailedException("Your age must be over 18!");
        } else if (user.getPassword() == null
                || user.getPassword().length() <= MIN_PASS_LENGTH) {
            throw new RegistrationFailedException("Your password must be at least 6 characters!");
        } else if (user.getLogin() == null) {
            throw new RegistrationFailedException("You wrote an empty login!");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationFailedException("Your login already exist!");
        }
        return user;
    }
}
