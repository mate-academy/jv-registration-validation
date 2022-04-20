package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static int INITIAL_AGE = 18;
    private static int PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();


    @Override
    public User register(User user) {
        if (user == null || user.getAge() == null
                || user.getLogin() == null
                || user.getPassword() == null) {
            throw new RuntimeException("User is null");
        }
        if (user.getAge() >= INITIAL_AGE && storageDao.get(user.getLogin()) == null
                && user.getPassword().length() >= PASSWORD_LENGTH) {
            storageDao.add(user);
            return user;
        }
        throw new RuntimeException("Incorrect data");
    }
}
