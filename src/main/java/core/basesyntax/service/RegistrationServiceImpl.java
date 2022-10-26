package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ADULT_AGE = 18;
    private static final int PASSWORD_LENGTH_MINIMUM = 6;
    private static final int OLDER_AGE = 120;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be a null.");
        }
        checkUserLogin(user);
        checkUserAge(user);
        checkUserPassword(user);
        return storageDao.add(user);
    }

    public void checkUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Your login isn't correct. Login can't be a null.");
        }
        if (user.getLogin().equals("")) {
            throw new InvalidDataException("Your login isn't correct. Login can't be a empty.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("This user already exists");
        }
    }

    public void checkUserAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Your age isn't correct. Age can't be a null");
        }
        if (user.getAge() < ADULT_AGE) {
            throw new InvalidDataException("Your age isn't correct. You must be over 18");
        }
        if (user.getAge() > OLDER_AGE) {
            throw new InvalidDataException("The oldest person in the world is 116 years old,"
                    + " how old are you?");
        }
    }

    public void checkUserPassword(User user) {
        if (user.getPassword().length() < PASSWORD_LENGTH_MINIMUM) {
            throw new InvalidDataException("Your password isn't correct. "
                    + "Please enter password 6 symbols minimum.");
        }
    }
}
