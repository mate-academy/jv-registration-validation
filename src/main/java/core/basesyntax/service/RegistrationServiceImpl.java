package core.basesyntax.service;

import core.basesyntax.CustomException;
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
            throw new CustomException("User can't not be null");
        }
        if (user.getLogin() == null) {
            throw new CustomException("Login field cannot be empty enter data");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new CustomException("Such user exists");
        }
        if (!Character.isLetter(user.getLogin().charAt(0))) {
            throw new CustomException("Login must start with a letter");
        }
        if (user.getPassword() == null) {
            throw new CustomException("User password can't not be null");
        }
        if (user.getPassword().equals("")) {
            throw new CustomException("Enter password");
        }
        if (user.getPassword().length() < MINIMAL_LENGTH_PASSWORD) {
            throw new CustomException("Your password is too short");
        }
        if (user.getPassword().length() > MAX_LENGTH_PASSWORD) {
            throw new CustomException("You entered too long password");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new CustomException("You are too small)");
        }
        if (user.getAge() > MAX_AGE) {
            throw new CustomException("You are too old, the heart may not withstand the content))");
        }
        return user;
    }
}
