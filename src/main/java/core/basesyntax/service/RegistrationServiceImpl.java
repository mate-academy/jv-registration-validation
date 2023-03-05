package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.validators.RegistrationValidatorException;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationValidatorException {
        if (user == null) {
            throw new RegistrationValidatorException("User shouldn't be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < 5) {
            throw new RegistrationValidatorException(
                    "Login is null or length is less than 5 symbols"
            );
        }
        if (user.getLogin().startsWith(" ")) {
            throw new RegistrationValidatorException(
                    "Login shouldn't start with space or special symbols"
            );
        }
        if (user.getPassword() == null
                || user.getPassword().length() < MIN_PASSWORD_CHARACTERS) {
            throw new RegistrationValidatorException(
                    "Your password is null or less than 5 characters"
            );
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationValidatorException("Your age is null or less than 18 years old");
        }
        if (storageDao.get(user.getLogin()) == user) {
            throw new RegistrationValidatorException("User existing in the storage");
        }
        return storageDao.add(user);
    }
}
