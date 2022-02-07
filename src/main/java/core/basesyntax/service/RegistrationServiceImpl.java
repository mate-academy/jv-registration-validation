package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_CHAR = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User is Null!");
        }
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("Invalid data!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_CHAR || user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age or password are invalid!");
        }
        if (user.getLogin().trim().equals("") || user.getPassword().trim().equals("")) {
            throw new RuntimeException("Login or password is empty!");
        }
        if (user.getLogin().trim().contains(" ") || user.getPassword().trim().contains(" ")) {
            throw new RuntimeException("Password or login can't have a space!");
        }
        for (User u : Storage.people) {
            if (u.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("User with such login already exists!");
            }
        }
        return storageDao.add(user);
    }
}
