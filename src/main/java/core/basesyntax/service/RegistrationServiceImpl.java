package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserAgeException;
import core.basesyntax.exception.UserExistsException;
import core.basesyntax.exception.UserLoginLengthException;
import core.basesyntax.exception.UserPasswordLengthException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            return null;
        }
        if (Storage.people.contains(user)) {
            throw new UserExistsException(
                    "User with login " + user.getLogin() + " already exists"
            );
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new UserLoginLengthException(
                    "User login length mustn't be lower than 6 symbols"
            );
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new UserPasswordLengthException(
                    "User password length mustn't be lower than 6 symbols"
            );
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new UserAgeException(
                    "User age mustn't be lower than 18 years"
            );
        }
        storageDao.add(user);
        return user;
    }
}
