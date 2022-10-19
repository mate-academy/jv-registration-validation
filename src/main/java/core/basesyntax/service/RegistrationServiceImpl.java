package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int EIGHTEEN_YEARS_OLD = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User can't be Null");
        }
        if (user.getLogin() == null) {
            throw new NullPointerException("Login is null");
        }
        if (user.getAge() == null) {
            throw new NullPointerException("Age is null");
        }
        if (user.getPassword() == null) {
            throw new NullPointerException("Password is null");
        }
        for (User u : Storage.people) {
            if (u.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("User with this email already exist");
            }
        }
        if (user.getAge() < EIGHTEEN_YEARS_OLD) {
            throw new RuntimeException("User to young");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("User's password to short, it mast consist of 6 character");
        }
        storageDao.add(user);
        return user;
    }
}
