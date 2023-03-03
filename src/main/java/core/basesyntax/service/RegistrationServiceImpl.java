package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidInputException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int AGE_GUINNESS_RECORD = 122;
    private static final int MIN_ID = 0;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 3;
    private static final int MAX_LOGIN_LENGTH = 20;
    private static final int MAX_PASSWORD_LENGTH = 30;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidInputException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidInputException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputException("A user with this login exists");
        }
        if (user.getPassword() == null) {
            throw new InvalidInputException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidInputException("Password less than "
                    + MIN_PASSWORD_LENGTH + " symbols");
        }
        if (!user.getLogin().matches("[a-zA-Z0-9]+")) {
            throw new InvalidInputException("Login can't contains special characters: @#$%");
        }
        if (user.getLogin().length() > MAX_LOGIN_LENGTH) {
            throw new InvalidInputException("Login size should be less than " + MAX_LOGIN_LENGTH);
        }
        if (user.getPassword().length() > MAX_PASSWORD_LENGTH) {
            throw new InvalidInputException("Password size should be less than "
                    + MAX_PASSWORD_LENGTH);
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidInputException("Login should be larger than " + MIN_LOGIN_LENGTH);
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidInputException("Age less than " + MIN_AGE);
        }
        if (user.getAge() >= AGE_GUINNESS_RECORD) {
            throw new InvalidInputException("Age more than " + AGE_GUINNESS_RECORD);
        }
        if (user.getId() < MIN_ID) {
            throw new InvalidInputException("User id can't be less than " + MIN_ID);
        }
        if (user.getPassword().equals(user.getLogin())) {
            throw new InvalidInputException("The password should be different from the login");
        }
        return storageDao.add(user);
    }
}
