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
        if (user == null)
            throw new RuntimeException("Invalid data. User can not be null");
        if (user.getAge() < MIN_AGE)
            throw new RuntimeException("Invalid data. User's age can not be less than 18");
        if (user.getLogin() == null)
            throw new RuntimeException("Invalid data. Login can not be null");
        if (storageDao.get(user.getLogin()) != null)
            throw new RuntimeException("It seems the user with the same login already exists");
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH)
            throw new RuntimeException("Sorry, your password is too short");
        if (user.getPassword() == null)
            throw new RuntimeException("Sorry, password can not be null");
        return storageDao.add(user);
    }
}
