package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_LOGIN = 6;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user.getLogin() == null) {
            throw new RegistrationException("Not valid login: " + user.getLogin());
        }
        if (user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new RegistrationException("Not valid login: " + user.getLogin()
                    + ". Min length login is " + MIN_LENGTH_LOGIN);
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RegistrationException("Not valid password: " + user.getPassword()
                    + ". Min length password is " + MIN_LENGTH_PASSWORD);
        }
        if (user.getAge() <= 0) {
            throw new RegistrationException("Error in age: " + user.getAge());
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        if ((user.getId() == null)) {
            throw new RegistrationException("Id cannot be null");
        }
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
        }
        return user;
    }
}
