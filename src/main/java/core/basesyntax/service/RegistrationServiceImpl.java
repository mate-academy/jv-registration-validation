package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN = 6;
    private static final int MIN_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }

        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        String login = user.getLogin();

        for (User existingUser : Storage.people) {
            if (existingUser.getLogin().equals(login)) {
                throw new RegistrationException("Such user already exists");
            }
        }

        if (user.getLogin().length() < MIN_LOGIN) {
            throw new RegistrationException("Login can't be shorter than 6");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD) {
            throw new RegistrationException("Password can't be shorter than 6");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is 18");
        }
        return storageDao.add(user);
    }
}
