package core.basesyntax.service;

import core.basesyntax.model.User;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User can't be null, please write not null User ");
        }
        if (user.getPassword() == null) {
            throw new NullPointerException("Password can't be null, please fix your password");
        }
        if (user.getLogin() == null) {
            throw new NullPointerException("Login can't be null, please fix your login");
        }
        if (loginIsNotUsed(user) && ageIsValid(user) && passWordIsValid(user)) {
            Storage.people.add(user);
            return user;
        }
        throw new RuntimeException("We can't register user, please try letter");
    }

    public boolean ageIsValid(User user) {
        return user.getAge() >= MIN_AGE;
    }

    public boolean loginIsNotUsed(User user) {
        return storageDao.get(user.getLogin()) == null;
    }

    public boolean passWordIsValid(User user) {
        return user.getPassword().length() >= MIN_LENGTH;
    }
}
