package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 130;
    private static final int MIN_CHARS_COUNT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User is null");
        }
        if (Storage.people.contains(user)) {
            throw new UserAlreadyPresentException();
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserShortAgeException();
        }
        if (user.getAge() > MAX_AGE) {
            throw new UserTooOldException();
        }
        if (user.getPassword().length() < MIN_CHARS_COUNT) {
            throw new UserPasswordTooShortException();
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (user.getId() != null) {
            throw new RuntimeException("Id should be null, but id is ["+user.getId()+"]");
        }
        return storageDao.add(user);
    }
}
