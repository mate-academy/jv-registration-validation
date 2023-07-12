package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.NotEnoughAgeException;
import core.basesyntax.exceptions.NotEnoughSizeException;
import core.basesyntax.exceptions.UserIsNullException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserIsNullException("Can't register user, because user is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            return null;
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new NotEnoughSizeException("The length of the login must be at least 6");
        }
        if (user.getPassword() == null ||user.getPassword().length() < 6) {
            throw new NotEnoughSizeException("The length of the password must be at least 6");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new NotEnoughAgeException("The age of the user must be at least 18");
        }
        storageDao.add(user);
        return user;
    }
}
