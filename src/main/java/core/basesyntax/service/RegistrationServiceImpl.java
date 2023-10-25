package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User`s data is empty");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can`t be null!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can`t be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("There is already such a user in the storage!");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login length must be more than 6! ("
                    + user.getLogin().length() + " now)");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password length must be more than 6! ("
                    + user.getPassword().length() + " now)");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User can`t registration because "
                    + "age field is empty");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age must be 18+! (" + user.getAge() + " now)");
        }
        return storageDao.add(user);
    }
}
