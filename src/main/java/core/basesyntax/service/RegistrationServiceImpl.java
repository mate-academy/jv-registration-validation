package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("Login can't be null! You should enter login!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationServiceException("Password can't be null! "
                    + "You should enter password!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("You entered age: " + user.getAge()
                    + " years old. User's age should be at least than 18 years old!");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RuntimeException("User's login should be at least 6 characters");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("User's password should be at least 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login " + user.getLogin()
                    + " is already exist! You should create another login.");
        }
        return storageDao.add(user);
    }
}
