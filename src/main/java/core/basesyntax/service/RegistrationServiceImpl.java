package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_REGISTRATION_AGE = 18;
    public static final int MAX_REGISTRATION_AGE = 100;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_LOGIN_LENGTH = 20;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RuntimeException {
        if (user == null) {
            throw new RuntimeException("User cannot be null");
        }
        if (user.getAge() < MIN_REGISTRATION_AGE || user.getAge() > MAX_REGISTRATION_AGE
                || user.getAge() == null) {
            throw new RuntimeException("User`s age cannot be less than "
                    + MIN_REGISTRATION_AGE + " and more than " + MAX_REGISTRATION_AGE);
        }
        if (user.getLogin().length() == 0 || user.getLogin().length() > MAX_LOGIN_LENGTH
                || user.getLogin() == null) {
            throw new RuntimeException("User`s login cannot be empty");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH
                || user.getPassword().length() > MAX_PASSWORD_LENGTH
                || user.getPassword() == null) {
            throw new RuntimeException("Password length cannot be less than " + MIN_PASSWORD_LENGTH
                    + "or more than " + MAX_PASSWORD_LENGTH + " and password cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User by login: " + user.getLogin() + " already exists");
        }
        return storageDao.add(user);
    }
}
