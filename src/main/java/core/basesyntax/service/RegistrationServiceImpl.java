package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Invalid User");
        }
        String userLogin = user.getLogin();
        String userPass = user.getPassword();
        int userAge = user.getAge();

        if (userLogin == null || userLogin.isEmpty() || !checkLogin(userLogin)
                || userPass == null
                || userPass.length() < MIN_PASSWORD_LENGTH
                || userAge < MIN_AGE) {
            throw new RuntimeException("Invalid Data");
        }
        storageDao.add(user);
        return user;
    }

    private boolean checkLogin(String login) {
        return storageDao.get(login) == null;
    }
}
