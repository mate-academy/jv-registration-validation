package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User should be not null");
        }
        if (user.getLogin() == null) {
            throw new NullPointerException("Login should be not null");
        }
        if (user.getPassword() == null) {
            throw new NullPointerException("Password should be not null");
        }
        if (loginIsNotUsed(user) && ageIsValid(user) && passwordIsValid(user)) {
            Storage.people.add(user);
            return user;
        }
        throw new RuntimeException("Can't register user, something going wrong!");
    }

    public boolean ageIsValid(User user) {
        return user.getAge() >= MIN_AGE;
    }

    public boolean loginIsNotUsed(User user) {
        return storageDao.get(user.getLogin()) == null;
    }

    public boolean passwordIsValid(User user) {
        return user.getPassword().length() >= MIN_PASSWORD_LENGTH;
    }
}
