package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.AgeValidationException;
import core.basesyntax.exception.LoginValidateException;
import core.basesyntax.exception.PasswordValidate;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkExistUserInDataBase(user.getLogin());
        ageValidation(user.getAge());
        passwordValidation(user.getPassword());
        return storageDao.add(user);
    }

    private void loginValidation(String login) {
        if (login == null || login.length() <=4) {
            throw new LoginValidateException("your login must be more than 4 characters");
        } else if (!Character.isLetter(login.charAt(0))) {
            throw new LoginValidateException("your login should start from the letter");
        }
    }

    private void checkExistUserInDataBase(String login) {
        loginValidation(login);
        if (storageDao.get(login) != null) {
            throw new LoginValidateException("consumer with that name is already " +
                    "exist please try another one login or input password for that one");
        }
    }

    private void passwordValidation(String password) {
        if (password == null || password.length() < 6) {
            throw new PasswordValidate("password must be longer than six characters");
        }
    }

    private void ageValidation(int age) {
        if (age < 18) {
            throw new AgeValidationException(
                    "User age must be at least " + 18);
        }
    }
}
