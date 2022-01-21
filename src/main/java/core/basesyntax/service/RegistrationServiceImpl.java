package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_PASSWORD_CHARACTERS_AMOUNT = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getAge() == 0
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getLogin().isEmpty()
                || user.getPassword().isEmpty()) {
            throw new NullPointerException("This line cannot be empty");
        }
        User userFromDb = storageDao.get(user.getLogin());
        if (userFromDb != null) {
            throw new RuntimeException(
                    "User with this login " + user.getLogin() + " is already exists");
        }
        if (user.getAge() <= MIN_AGE) {
            throw new RuntimeException("User's age is under 18");
        }
        if (user.getPassword().length() <= MIN_PASSWORD_CHARACTERS_AMOUNT) {
            throw new RuntimeException("Password contains less then 6 characters");
        }
        return storageDao.add(user);
    }
}
