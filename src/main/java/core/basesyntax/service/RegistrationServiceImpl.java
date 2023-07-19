package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.LoginAlreadyExistsException;
import core.basesyntax.exceptions.NotEnoughAgeException;
import core.basesyntax.exceptions.NotEnoughSizeException;
import core.basesyntax.exceptions.UserIsNullException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserIsNullException("Can't register user, because user is null");
        }
        if (user.getAge() == null) {
            throw new NotEnoughAgeException("The age of the user can't be null");
        }
        if (user.getPassword() == null) {
            throw new NotEnoughSizeException("The password can't be null");
        }
        if (user.getLogin() == null) {
            throw new NotEnoughSizeException("The login can't be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new NotEnoughSizeException("The length of the login must be at least "
                    + MIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new NotEnoughSizeException("The length of the password must be at least "
                    + MIN_LENGTH);
        }
        if (user.getAge() < MIN_AGE) {
            throw new NotEnoughAgeException("The age of the user must be at least " + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new LoginAlreadyExistsException("User already exists with login "
                    + user.getLogin());
        }
        storageDao.add(user);
        return user;
    }
}
