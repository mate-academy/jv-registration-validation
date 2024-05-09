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
    private final LoginValidator loginValidator = new LoginValidatorIml();
    private final PasswordValidator passwordValidator = new PasswordValidatorIml();
    private final AgeValidator ageValidator = new AgeValidatorIml();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException(RegistrationExceptionMessage.USER_CAN_NOT_BE_NULL);
        }
        loginValidator.isValid(user.getLogin());
        checkExistUser(user.getLogin());
        passwordValidator.isValid(user.getPassword());
        ageValidator.isValid(user.getAge());
        return storageDao.add(user);

    }

    private void checkExistUser(String login) {
        User existsUser = storageDao.get(login);
        if (existsUser != null) {
            throw new RegistrationException(RegistrationExceptionMessage.USER_EXISTS_MESSAGE);
        }
    }

}
