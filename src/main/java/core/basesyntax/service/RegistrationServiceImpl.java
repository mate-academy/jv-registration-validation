package core.basesyntax.service;

import core.basesyntax.RegistrationServiceException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_LENGTH_PASSWORD = 6;
    private static final int MAX_LENGTH_PASSWORD = 16;
    private static final int MINIMAL_AGE = 18;
    private static final int MAX_AGE = 80;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationServiceException("User can't not be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("Login field cannot be empty enter data");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("Such user exists");
        }
        if (!Character.isLetter(user.getLogin().charAt(0))) {
            throw new RegistrationServiceException("Login must start with a letter");
        }
        if (user.getPassword() == null) {
            throw new RegistrationServiceException("User password can't not be null");
        }
        if (user.getPassword().isEmpty()) {
            throw new RegistrationServiceException("Enter password");
        }
        if (user.getPassword().length() < MINIMAL_LENGTH_PASSWORD) {
            throw new RegistrationServiceException("Your password is too short");
        }
        if (user.getPassword().length() > MAX_LENGTH_PASSWORD) {
            throw new RegistrationServiceException("You entered too long password");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RegistrationServiceException("You are too small)");
        }
        if (user.getAge() > MAX_AGE) {
            throw new
                    RegistrationServiceException("You are too old, " +
                    "the heart may not withstand the content))");
        }
        return user;
    }
}
