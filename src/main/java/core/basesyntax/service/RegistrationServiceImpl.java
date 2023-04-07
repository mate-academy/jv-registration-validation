package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LEHGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new NullPointerException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new NullPointerException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new NullPointerException("Age can't be null");
        }
        if (storageDao.get(user.getLogin()) == null) {
            if (user.getAge() < MIN_AGE) {
                throw new RuntimeException("Not valid age: " + user.getAge() + ". Allowed age is "
                        + "older than " + MIN_AGE);
            }
            if (user.getPassword().toCharArray().length < MIN_LEHGTH) {
                throw new RuntimeException("Password is incorrect. Plese enter at least 6 symbols");
            }
            Storage.people.add(user);
            //storageDao.add(user);
            int size = Storage.people.size();
            return user;
        }
        throw new RuntimeException("Can't add user. There's existing user with a given login.");
    }
}
