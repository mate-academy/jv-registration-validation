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
            throw new RuntimeException("User can't be Null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login is null");
        }
        if (user.getLogin().equals(EMPTY_LOGIN)) {
            throw new RuntimeException("Login can't be empty");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password is null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User's password to short, it mast consist of 6 character");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age is null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User to young");
        }
        if (storageDao.get(user.getLogin()) == user) {
            throw new RuntimeException("User with this email already exist");
        }
        if (storageDao.get(user.getLogin()) != null && storageDao.get(user.getLogin()).getLogin().equals(user.getLogin())) {
            throw new RuntimeException("User with this email already exist");
        }
        return storageDao.add(user);
    }
}
