package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.DataValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new DataValidationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new DataValidationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new DataValidationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new DataValidationException("Age can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new DataValidationException("length of the login cannot be "
                    + user.getLogin().length() + " it must be at least " + MIN_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new DataValidationException("length of the password cannot be "
                    + user.getPassword().length() + " it must be at least " + MIN_PASSWORD_LENGTH);
        }
        if (user.getAge() < MIN_AGE) {
            throw new DataValidationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        if (Storage.people.contains(user)) {
            throw new DataValidationException("the user with this login "
                    + user.getLogin() + " already exists");
        }
        return storageDao.add(user);
    }
}
