package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_VALID_AGE = 18;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getAge() == null || user.getPassword() == null) {
            throw new RuntimeException("Not all fields are filled");
        }
        if (user.getLogin().equals("")) {
            throw new RuntimeException("an empty line in the Login or Password field");
        }
        if (user.getAge() < 0) {
            throw new RuntimeException("The user entered a negative number or was not born yet");
        }
        if (user.getAge() < MIN_VALID_AGE) {
            throw new RuntimeException("User is too young");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Short password");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login already exists");
        }
        storageDao.add(user);
        return user;
    }
}
