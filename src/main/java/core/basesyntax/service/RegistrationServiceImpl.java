package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    public boolean existLogin(String login) {
        for (User person : Storage.people) {
            if (person.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        } else if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login must be longer than 6 characters");
        } else if (existLogin(user.getLogin())) {
            throw new RegistrationException(user.getLogin() + "has already been taken");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        } else if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password must be longer than 6 characters");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Do not leave password field empty!");
        } else if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
