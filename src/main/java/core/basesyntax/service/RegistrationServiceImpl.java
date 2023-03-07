package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationValidatorException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationValidatorException("User shouldn't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationValidatorException(
                    "Login is null!"
            );
        }
        if (user.getPassword() == null
                || user.getPassword().length() < MIN_PASSWORD_CHARACTERS) {
            throw new RegistrationValidatorException(
                    "Your password is null or less than " + MIN_PASSWORD_CHARACTERS + " characters"
            );
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationValidatorException("Your age is null or less than "
                    + MIN_AGE + " years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationValidatorException(
                    "Login already exists in the storage: " + user.getLogin()
            );
        }
        return storageDao.add(user);
    }
}
