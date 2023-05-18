package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_LOGIN_AND_PASSWORD = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("You must fill in all fields");
        }
        if (user.getId() == null) {
            throw new RegistrationException("Id must be a number!");
        }
        if (user.getPassword() == null
                || user.getPassword().length() <= MIN_LENGTH_LOGIN_AND_PASSWORD) {
            throw new RegistrationException("Password must be longer than 6 including");
        }
        if (user.getLogin() == null || user.getLogin().length() <= MIN_LENGTH_LOGIN_AND_PASSWORD) {
            throw new RegistrationException("Login must be longer than 6 including");
        }
        if (user.getAge() == null || user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("Come back when you're 18");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login is already in use, try something new");
        }
        return storageDao.add(user);
    }
}
