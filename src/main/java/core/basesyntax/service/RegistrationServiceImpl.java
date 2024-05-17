package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("LOGIN " + user.getLogin() + "is used");
        }
        if ( user.getPassword() != null){
            if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new ValidationException("PASSWORD " + user.getPassword() + "is to short");
             }
        }

        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new ValidationException("Login " + user.getLogin() + "is to short");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new ValidationException("Age " + user.getAge() + " is too young");
        }
        storageDao.add(user);
        return user;
    }
}
