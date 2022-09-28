package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null.");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password can't be less " + MIN_PASSWORD_LENGTH + ".");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User age can't be null.");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("User can't be less " + MIN_USER_AGE + " years old.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("There is a user with this username "
                    + user.getLogin() + " .");
        }
        return storageDao.add(user);
    }
}
