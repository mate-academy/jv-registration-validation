package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int REQUIRED_AGE = 18;
    private static final int MINIMUM_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();
    private final User user = new User();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidInputException("No data provided");
        }
        loginValidation();
        passwordValidation();
        ageValidation();
        return storageDao.add(user);
    }

    private void loginValidation() {
        if (user.getLogin() == null) {
            throw new InvalidInputException("Please, fill all the required fields");
        }
        if (user.getLogin().length() < MINIMUM_LENGTH) {
            throw new InvalidInputException("Your login should be at least 6 characters long");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputException("User already exists");
        }
    }

    private void passwordValidation() {
        if (user.getPassword() == null) {
            throw new InvalidInputException("Please, fill all the required fields");
        }
        if (user.getPassword().length() < MINIMUM_LENGTH) {
            throw new InvalidInputException("Your password should be at least 6 characters long");
        }
    }

    private void ageValidation() {
        if (user.getAge() == null) {
            throw new InvalidInputException("Please, fill every required field");
        }
        if (user.getAge() < REQUIRED_AGE) {
            throw new InvalidInputException("You should be at least 18 years old to register");
        }
    }
}
