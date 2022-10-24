package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.validator.AgeValidator;
import core.basesyntax.service.validator.AgeValidatorImpl;
import core.basesyntax.service.validator.LoginValidator;
import core.basesyntax.service.validator.LoginValidatorImpl;
import core.basesyntax.service.validator.PasswordValidator;
import core.basesyntax.service.validator.PasswordValidatorImpl;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final AgeValidator ageValidator = new AgeValidatorImpl();
    private final LoginValidator loginValidator = new LoginValidatorImpl();
    private final PasswordValidator passwordValidator = new PasswordValidatorImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Can't register null User!");
        }
        validateUserData(user);
        return storageDao.add(user);
    }

    private void validateUserData(User user) {
        if (!ageValidator.isValid(user.getAge())) {
            throw new RuntimeException("User age is not allowed!");
        }
        if (!loginValidator.isValid(user.getLogin(), storageDao)) {
            throw new RuntimeException("Login isn't valid or user with same login already exist!");
        }
        if (!passwordValidator.isValid(user.getPassword())) {
            throw new RuntimeException("Password should contain 6+ characters!");
        }
    }
}
