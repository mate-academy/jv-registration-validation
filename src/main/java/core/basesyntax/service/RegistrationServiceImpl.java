package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.AgeValidationExeption;
import core.basesyntax.exeptions.LoginValidationException;
import core.basesyntax.exeptions.NullUserValidationExeption;
import core.basesyntax.exeptions.PasswordValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullUserValidationExeption("User can't be null");
        }

        ageValidation(user);
        loginValidation(user);
        passwordValidation(user);

        return storageDao.add(user);
    }

    private void ageValidation(User user) {
        if (user.getAge() == null) {
            throw new AgeValidationExeption("Age can't be null");
        }
        if (user.getAge() < 18) {
            throw new AgeValidationExeption("User's age must be greater or equal to 18");
        }
    }

    private void loginValidation(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new LoginValidationException("User's login can't be null or empty");
        }
        if (user.getLogin().length() < 6) {
            throw new LoginValidationException("User's login length must be at least 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new LoginValidationException("User already exists");
        }
    }

    private void passwordValidation(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new PasswordValidationException("User's password can't be null or empty");
        }
        if (user.getPassword().length() < 6) {
            throw new PasswordValidationException(
                    "User's password length must be at least 6 characters"
            );
        }
    }
}
