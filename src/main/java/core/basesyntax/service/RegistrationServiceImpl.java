package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegisterDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storage = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegisterDataException("login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegisterDataException("password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegisterDataException("age can't be null");
        }
        if (storage.get(user.getLogin()) != null) {
            throw new RegisterDataException("user with same login: " + user.getLogin()
                    + " already exist");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegisterDataException("user age: " + user.getAge() + " less than 18");
        }
        if ((user.getLogin()).length() < MIN_LOGIN_LENGTH) {
            throw new RegisterDataException("user login have: " + (user.getLogin()).length()
                    + " chars that less than min required amount(6)");
        }
        if ((user.getPassword()).length() < MIN_PASSWORD_LENGTH) {
            throw new RegisterDataException("user password have: " + (user.getPassword()).length()
                    + " chars that less than min required amount(6)");
        }
        return storage.add(user);
    }
}
