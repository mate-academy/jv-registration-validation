package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("There is some problems with your identification on our end,"
                    + " please fill in the form one more time");
        }
        if (user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException("You should fill in all the required fields");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password should be no less than "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("In order to register your age should be more than "
                    + MIN_AGE);
        }
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("Please enter the valid age");
        }
        if (Storage.people.contains(storageDao.get(user.getLogin()))) {
            throw new RuntimeException("The user with this login is already exists");
        }

        storageDao.add(user);
        return user;
    }
}
