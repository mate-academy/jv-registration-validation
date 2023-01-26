package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMUM_USERS_AGE = 18;
    public static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new RegistrationException("All of the user`s fields must be filled");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login field must be filled");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password field must be filled");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age field must be filled");
        }
        if (user.getLogin().isEmpty()) {
            throw new RegistrationException("Login field must be filled");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("The user with such login: \""
                    + user.getLogin()
                    + "\" already exists");
        }
        if (user.getAge() < MINIMUM_USERS_AGE) {
            throw new RegistrationException("The user with: "
                    + user.getAge()
                    + " years old - not allowed");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RegistrationException("The password must be not less than: "
                    + MINIMUM_PASSWORD_LENGTH
                    + " chars");
        }
        storageDao.add(user);
        return user;
    }
}
