package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserDataException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserDataException("Password can't be null");
        }
        for (User person:Storage.people) {
            if (person.getLogin().equals(user.getLogin())) {
                throw new InvalidUserDataException("You cannot add two users "
                        + "with the same login to the storage");
            }
        }
        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new InvalidUserDataException("Login length is " + user.getLogin().length()
                    + ", the login cannot contain less than 6 characters");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new InvalidUserDataException("Password length is " + user.getPassword().length()
                    + ", the password cannot contain less than 6 characters");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new InvalidUserDataException("User's age is " + user.getAge()
                    + ", age cannot be less than 18");
        }
        return storageDao.add(user);
    }
}
