package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.DuplicateUserException;
import core.basesyntax.exception.InvalidAgeException;
import core.basesyntax.exception.InvalidLoginException;
import core.basesyntax.exception.InvalidPasswordException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ADULT_AGE = 18;
    private static final int PASSWORD_LENGTH_MINIMUM = 6;
    private static final int OLDER_AGE = 120;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User can't be a null.");
        }
        if (user.getLogin() == null) {
            throw new InvalidLoginException("Your login isn't correct. Login can't be a null.");
        }
        if (user.getLogin().equals("")) {
            throw new InvalidLoginException("Your login isn't correct. Login can't be a empty.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new DuplicateUserException("This user already exists");
        }
        if (user.getAge() == null) {
            throw new InvalidAgeException("Your age isn't correct. Age can't be a null");
        }
        if (user.getAge() < ADULT_AGE) {
            throw new InvalidAgeException("Your age isn't correct. You must be over 18");
        }
        if (user.getAge() > OLDER_AGE) {
            throw new InvalidAgeException("The oldest person in the world is 116 years old,"
                    + " how old are you?");
        }
        if (user.getPassword().length() < PASSWORD_LENGTH_MINIMUM) {
            throw new InvalidPasswordException("Your password isn't correct. "
                    + "Please enter password 8 symbols minimum.");
        }
        return storageDao.add(user);
    }
}
