package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String EMPTY_LOGIN = "";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User can't be Null");
        }
        if (user.getLogin() == null) {
            throw new NullPointerException("Login is null");
        }
        if (user.getLogin().equals(EMPTY_LOGIN)) {
            throw new NullPointerException("Login can't be empty");
        }
        if (user.getPassword() == null) {
            throw new NullPointerException("Password is null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User's password to short, it mast consist of 6 character");
        }
        if (user.getAge() == null) {
            throw new NullPointerException("Age is null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User to young");
        }
        if (storageDao.get(user.getLogin()) == user) {
            throw new RuntimeException("User with this email already exist");
        }
        return storageDao.add(user);
    }
}
