package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.exception.RegistrationExceptionMessage;
import core.basesyntax.model.User;
import core.basesyntax.service.interfaces.AgeValidator;
import core.basesyntax.service.interfaces.LoginValidator;
import core.basesyntax.service.interfaces.PasswordValidator;
import core.basesyntax.service.interfaces.RegistrationService;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final LoginValidator loginValidator = new LoginValidatorImpl();
    private final PasswordValidator passwordValidator = new PasswordValidatorImpl();
    private final AgeValidator ageValidator = new AgeValidatorImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user.getLogin());
        checkExistUser(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationException(RegistrationExceptionMessage.USER_CAN_NOT_BE_NULL);
        }
    }

    private void validateLogin(String login) {
        if (!loginValidator.isValid(login)) {
            throw new RegistrationException(RegistrationExceptionMessage.WRONG_LOGIN_MESSAGE);
        }
    }

    private void checkExistUser(String login) {
        User existsUser = storageDao.get(login);
        if (existsUser != null) {
            throw new RegistrationException(RegistrationExceptionMessage.USER_EXISTS_MESSAGE);
        }
    }

    private void validatePassword(String password) {
        if (!passwordValidator.isValid(password)) {
            throw new RegistrationException(RegistrationExceptionMessage.WRONG_PASSWORD_MESSAGE);
        }
    }

    private void validateAge(Integer age) {
        if (!ageValidator.isValid(age)) {
            throw new RegistrationException(RegistrationExceptionMessage.WRONG_AGE_MESSAGE);
        }
    }

}
