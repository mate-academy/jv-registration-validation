package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("User login can't be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login { "
                    + user.getLogin()
                    + " } already exist!");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User age can't be null!");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("User is less than "
                    + MIN_USER_AGE
                    + " years old!");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User password can't be null!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User password is less than "
                    + MIN_PASSWORD_LENGTH
                    + " characters!");
        }
        storageDao.add(user);
        return user;
    }
}
