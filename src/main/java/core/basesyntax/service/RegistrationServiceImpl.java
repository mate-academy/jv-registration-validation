package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        for (User user1: Storage.people) {
            if (user1.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("Sorry, login is used");
            }
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Sorry, password to short minimal length is "
                    + MIN_PASSWORD_LENGTH);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Sorry, \n"
                    + "your age must be at least required to register"
                    + MIN_AGE + " years old");
        }
        return storageDao.add(user);
    }
}
