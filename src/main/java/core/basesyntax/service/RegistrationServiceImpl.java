package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserUncheckedException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException();
        }
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new UserUncheckedException("Login or password cannot be null");
        }
        for (User person : Storage.people) {
            if (person.getLogin().equals(user.getLogin())) {
                throw new UserUncheckedException("Login already taken");
            }
        }
        if (user.getLogin().length() < 6 || user.getPassword().length() < 6) {
            throw new UserUncheckedException("Login or password characters less than 6");
        }
        if (user.getAge() < 18) {
            throw new UserUncheckedException("User age is less than 18");
        }
        storageDao.add(user);
        return user;
    }
}
