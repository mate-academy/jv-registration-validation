package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int LOWEST_AGE_THRESHOLD = 18;
    static final int HIGHEST_AGE_THRESHOLD = 125;
    static final int SHORTEST_PASSWORD_LENGTH = 6;
    private StorageDao storageDao;

    @Override
    public User register(User user) {
        Integer age = user.getAge();
        String password = user.getPassword();
        if (age == null) {
            throw new RuntimeException("Enter your age");
        } else if (age < LOWEST_AGE_THRESHOLD || age > HIGHEST_AGE_THRESHOLD) {
            throw new RuntimeException("Sorry, we cant register you."
                    + " Our users can't be younger then 18 and older then 125 years old");
        }
        if (password == null) {
            throw new RuntimeException("Enter your password, please!");
        } else if (password.length() < SHORTEST_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password is too short.");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Enter your password");
        }
        storageDao = new StorageDaoImpl();
        if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        }
        throw new RuntimeException("User with such login already exist");
    }
}
