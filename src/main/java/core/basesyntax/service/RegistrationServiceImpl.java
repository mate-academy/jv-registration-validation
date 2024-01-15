package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_LENGTH_FOR_LOGIN = 6;
    private static final int DEFAULT_LENGTH_FOR_PASSWORD = 6;
    private static final int AGE_THRESHOLD = 18;
    private static final int MINIMUM_ID = 0;
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
        if (user.getLogin().length() < DEFAULT_LENGTH_FOR_LOGIN) {
            throw new InvalidDataException("Your login should be at least "
                    + DEFAULT_LENGTH_FOR_LOGIN + " character");
        }
        if (user.getPassword().length() < DEFAULT_LENGTH_FOR_PASSWORD) {
            throw new InvalidDataException("Your password should be at least "
                    + DEFAULT_LENGTH_FOR_PASSWORD + " character");
        }
        if (user.getAge() < AGE_THRESHOLD) {
            throw new InvalidDataException("Your age should be at least "
                    + AGE_THRESHOLD + " for registration");
        }
        if (user.getId() <= MINIMUM_ID) {
            throw new InvalidDataException("You have entered an incorrect id. "
                    + "Id can not be less than " + MINIMUM_ID);
        }
        return storageDao.add(user);
    }
}
