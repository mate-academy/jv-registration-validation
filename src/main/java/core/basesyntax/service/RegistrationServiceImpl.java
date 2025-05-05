package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getPassword() == null || user.getLogin() == null
                || user.getAge() == null || user == null) {
            throw new RuntimeException("Value cant be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User " + user.getLogin() + " already exist");
        }
        if (user.getLogin().equals("")) {
            throw new RuntimeException("User login can`t be empty");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age must be more than " + MIN_AGE + " years");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length should be greater then "
                    + MIN_PASSWORD_LENGTH);
        }
        return storageDao.add(user);
    }
}
