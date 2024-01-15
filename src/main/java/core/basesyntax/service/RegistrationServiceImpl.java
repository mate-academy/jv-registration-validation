package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD = 6;
    private static final int MINIMAL_AGE_FOR_REGISTRATION = 18;
    private static final int ZERO = 0;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can not be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("This login is already registered, try another one");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Field with login can not be empty(null), input login");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Field with password can not be empty(null)"
                    + ", input password");
        }
        if (user.getId() == null) {
            throw new InvalidDataException("ID can not be null");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can not be null");
        }
        if (user.getLogin().length() < DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD) {
            throw new InvalidDataException("Your login should be at least 6 character");
        }
        if (user.getPassword().length() < DEFAULT_LENGTH_FOR_LOGIN_AND_PASSWORD) {
            throw new InvalidDataException("Your password should be at least 6 character");
        }
        if (user.getAge() < MINIMAL_AGE_FOR_REGISTRATION) {
            throw new InvalidDataException("Your age should be at least 18 for registration");
        }
        if (user.getId() <= ZERO) {
            throw new InvalidDataException("You have entered an incorrect id. "
                    + "Id can not be less than 0");
        }
        return storageDao.add(user);
    }
}
