package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int VALID_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getPassword() == null) {
            throw new RegisterServiceImplException("Password can't be null !");
        }
        if (user.getLogin() == null) {
            throw new RegisterServiceImplException("Login can't be null !");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegisterServiceImplException("Age can't be less than " + MIN_AGE + " !");
        }
        if (user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            throw new RegisterServiceImplException("Password must be at least "
                    + VALID_PASSWORD_LENGTH + " charaters !");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegisterServiceImplException("User already exist !");
        } else {
            return storageDao.add(user);
        }
    }
}
