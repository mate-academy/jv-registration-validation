package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.UserAlreadyExistException;
import core.basesyntax.exceptions.UsersAgeNotValidException;
import core.basesyntax.exceptions.UsersPasswordNotValidException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_ACCEPT_AGE = 18;
    private static final int MIN_ACCEPT_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new NullPointerException("You must fill all the fields!");
        }
        if (storageDao.get(user.getLogin()) == null) {
            if (user.getAge() >= MIN_ACCEPT_AGE) {
                if (user.getPassword().length() >= MIN_ACCEPT_PASSWORD_LENGTH) {
                    storageDao.add(user);
                    return user;
                }
                throw new UsersPasswordNotValidException("Password must be at least 6 symbols");
            }
            throw new UsersAgeNotValidException("You should be older!");
        }
        throw new UserAlreadyExistException("This login already exists!");
    }
}
