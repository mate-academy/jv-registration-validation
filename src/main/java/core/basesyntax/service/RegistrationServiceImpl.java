package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null) {
            throw new NotValidUserException("User's age can't be null");
        }
        if (user.getLogin() == null) {
            throw new NotValidUserException("User's login can't be null");
        }
        if (user.getPassword() == null) {
            throw new NotValidUserException("User's password can't be null");
        }
        if (user.getAge() < 18) {
            throw new NotValidUserException("User must be 18 years old or older");
        }
        if (user.getPassword().length() < 6) {
            throw new NotValidUserException("Password's length must be longer than 6");
        }
        for (User user1: Storage.people) {
            if (user1.getLogin() == user.getLogin()) {
                throw new NotValidUserException("Such a user already exists");
            }
        }
        return storageDao.add(user);
    }
}
